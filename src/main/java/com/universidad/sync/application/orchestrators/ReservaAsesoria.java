package com.universidad.sync.application.orchestrators;

import com.universidad.sync.domain.contracts.ISesionSincronizada;
import com.universidad.sync.domain.entities.EspacioCampus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReservaAsesoria implements ISesionSincronizada {

    private static Integer generadorId = 1;

    private Integer idReserva;
    private Integer idDocente;
    private List<Integer> idsEstudiantes;
    private EspacioCampus espacioAsignado;
    private Boolean estadoConfirmacion;


    public ReservaAsesoria(Integer idDocente, List<Integer> idsEstudiantes, EspacioCampus espacioAsignado) {
        this.idReserva = generadorId++;
        this.idDocente = idDocente;
        this.idsEstudiantes = idsEstudiantes;
        this.espacioAsignado = espacioAsignado;
        this.estadoConfirmacion = false;
    }

    // Interface Methods (ISesionSincronizable)

    @Override
    public boolean verificarDisponibilidad(String fechaHora) {
        if (espacioAsignado.isEnMantenimiento()) return false;
        int totalPersonas = 1 + idsEstudiantes.size();
        if (!espacioAsignado.verificarAforo(totalPersonas)) return false;
        return true;
    }

    @Override
    public void bloquearEspacio(String fechaHora) {
        System.out.println("Bloqueando la sala " + espacioAsignado.getIdEspacio() + " para la fecha: " + fechaHora);
        this.estadoConfirmacion = true;
    }

    @Override
    public void emitirAviso(Integer idUsuario, String detalle) {
        System.out.println("Enviando notificación al usuario " + idUsuario + ": " + detalle);
    }

    @Override
    public void confirmarMatchDeHorarios() {
        if (this.estadoConfirmacion) {
            emitirAviso(this.idDocente, "Reserva confirmada.");
            for (Integer idEstudiante : idsEstudiantes) {
                emitirAviso(idEstudiante, "Reserva confirmada. Sala: " + espacioAsignado.getIdEspacio());
            }
        }
    }

    // Own Methods

    public String exportarDetalleMarkdown() {
        return """
               # Detalle de Reserva
               - **ID Reserva:** %d
               - **Docente:** %d
               - **Sala:** %d
               - **Participantes:** %d
               """.formatted(
                   this.idReserva,
                   this.idDocente,
                   this.espacioAsignado.getIdEspacio(),
                   this.idsEstudiantes.size()
               );
    }
}