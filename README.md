# Taller de Abstracción: Clases y Objetos

## Ejecución: 

### Dependencias: 
- IntelliJ IDEA
- JDK 21 o superior (puedes cambiarlo en configuración)


### Instrucciones: 
Abre una bash de git en la dirección donde quieras alojar el repositorio y **ejecuta este comando**:

```bash
git clone https://github.com/rarcorp481/Taller_Sistema_Horarios.git
```

Luego abre IntelliJ IDEA y dentro de las opciones de arriba selecciona `File > Open` y **busca la carpeta donde alojaste el proyecto** clonado y selecciónalo. 

Para ejecutarlo posicionate en la clase `Main.java` en la ruta `src/main/java/com/universidad/sync` dentro visualizador de rutas de IntelliJ IDEA y comienza la ejecución con el botón en el menú superior del IDE. 




## Problema a Resolver: Sistema de Sincronización de Horarios Universitarios


### Contexto: 
En el entorno universitario, coordinar un espacio libre entre docentes y grupos de estudiantes para proyectos o asesorías suele ser un proceso manual y propenso a choques de horario.

### Problema: 
Se requiere un sistema centralizado que cruce automáticamente las afendas de múltiples usuarios, encuentre "huecos" libres en común de forma sincronizada y permita reservar espacios físicos dentro del campus sin generar conflictos de concurrencia.



## Diseño del proyecto:

### Árbol de directorios:
El gestor de dependencias usado es Maven. Las clases e interfaces están bien categorizadas y dentro de sus respectivos directorios. 

```text
Taller_Sistema_Horarios/
├── pom.xml
├── README.md
└── src
    └── main
        └── java
            └── com
                └── universidad
                    └── sync
                        ├── application
                        │   └── orchestrators
                        │       └── ReservaAsesoria.java
                        ├── domain
                        │   ├── contracts
                        │   │   ├── INotificable.java
                        │   │   ├── IProgramable.java
                        │   │   └── ISesionSincronizada.java
                        │   └── entities
                        │       ├── AgendaAcademica.java
                        │       └── EspacioCampus.java
                        └── Main.java

12 directories, 9 files
```

### Diseño de Interfaces (ISP): 

Para cumplir con el requerimiento de las interfaces y mantener el **Principio de Segregación de Interfaces (ISP)**, definimos dos contratos base y un contrato hijo que hereda de ambos. El directorio en donde se alojan es [contracts](src/main/java/com/universidad/sync/domain/contracts). 

#### Vista previa: 

```java
// Interfaz Padre 1: Lógica del tiempo

public interface IProgramable {
    boolean verificarDisponibilidad(String fechaHora);
    void bloquearEspacio(String fechaHora);
}

// Intefaz Padre 2: Lógica de Alertas

public interface INotificable {
    void emitirAviso(int idUsuario, String detalle);
}

// Interface Hija: Herencia de IProgramable e INotificable

public interface ISesionSincronizada extends IProgramable, INotificable{
    void confirmarMatchDeHorarios();
}
```



## Definición de las 3 clases principales:

Estas clases están diseñadas respetando el **Principio de Responsabilidad Única (SRP)**.

### Clase 1: [AgendaAcademica](src/main/java/com/universidad/sync/domain/entities/AgendaAcademica.java)
Representa el registro de bloques de tiempo de un estudiante o profesor. No sabe nada de notificaciones ni de salas, solo administra el tiempo.

#### Atributos:

- idPropietario (Integer)
- bloquesOcupados (Lista de String)
- ultimaActualizacion (Date)

#### Métodos y Comportamientos:

- **agregarClase(String bloqueUsado):** Registra un horario fijo inamovible. 
- **obtenerTiempoLibre():** Retorna los bloques que no están en la lista de ocupados. 
- **liberarBloque(String bloqueTiempo):** Elimina un registro si se cancela una clase.

### Clase 2: [EspacioCampus](src/main/java/com/universidad/sync/domain/entities/EspacioCampus.java)
Representa el recurso físico (una sala de estudio o laboratorio) que se va a reservar.

#### Atributos:

- idEspacio (Integer)
- capacidadMaxima (Integer)
- recursosTecnicos (Lista de String)

#### Métodos y Comportamientos:

- **verificarAforo(int cantidadPersonas):** Retorna true si el grupo cabe en la sala.
- **habilitarMantenimiento():** Cambia el estado temporalmente para que no pueda ser reservada.

### Clase 3: [ReservaAsesoria](src/main/java/com/universidad/sync/application/orchestrators/ReservaAsesoria.java) (Implementa ISesionSincronizada)
Esta es la clase orquestadora. Une el tiempo libre encontrado en las agendas con un espacio físico disponible.

#### Atributos:

- idReserva (Integer)
- idDocente (Integer)
- idsEstudiantes (Lista de Integer)
- espacioAsignado (EspacioCampus)
- estadoConfirmacion (Boolean)

#### Métodos y Comportamientos:

1. **Heredado de IProgramable:**
   -  **verificarDisponibilidad(String fechaHora):** Cruza los datos de las agendas involucradas y la disponibilidad del EspacioCampus
   - **bloquearEspacio(String fechaHora):** Fija la reserva en firme.
   

2. **Heredado de INotificable:** 
   - **emitirAviso(Integer idUsuario, String detalle):** Genera el registro de la alerta para los participantes


3. **Heredado de ISesionSincronizada:**
   - confirmarMatchDeHorarios(): Valida que todas las partes aprobaron la hora sugerida.
   
4. **Propio:**
   - **exportarDetalleMarkdown():** Genera un resumen en texto plano estructurado con los detalles de la reunión.




## Relación entre las clases (Diagrama): 

Para visualizar el diagrama de manera óptima, entra a este enlace de google drive (desde cualquier cuenta): [link](https://drive.google.com/file/d/1gSkYoHB7TKeLz2jNc7P9ge0XiLxfNAJL/view?usp=sharing)

> [!IMPORTANT]
> Una vez dentro del enlace, recuerda hacer clic en el botón que dice "Abrir con draw.io" para asegurar que el texto sea legible.

> [!WARNING]
> Si no abres el diagrama con draw.io, el texto será ilegible.
