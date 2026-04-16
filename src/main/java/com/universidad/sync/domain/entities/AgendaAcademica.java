package com.universidad.sync.domain.entities;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AgendaAcademica {

    private Integer idPropietario;
    private List<String> bloquesOcupados;
    private Date ultimaActualizacion;

    public AgendaAcademica(Integer idPropietario) {
        this.idPropietario = idPropietario;
        this.bloquesOcupados = new ArrayList<>();
        this.ultimaActualizacion = new Date();
    }

    public void agregarClase(String bloqueTiempo) {
        this.bloquesOcupados.add(bloqueTiempo);
        this.ultimaActualizacion = new Date();
    }

    public List<String> obtenerTiempoLibre() {
        // Lógica simulada: retornaría los bloques de 8am a 8pm que no estén en la lista
        return new ArrayList<>();
    }

    public void liberarBloque(String bloqueTiempo) {
        this.bloquesOcupados.remove(bloqueTiempo);
        this.ultimaActualizacion = new Date();
    }
}