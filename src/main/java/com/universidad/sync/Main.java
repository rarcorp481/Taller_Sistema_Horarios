package com.universidad.sync;

import com.universidad.sync.application.orchestrators.ReservaAsesoria;
import com.universidad.sync.domain.entities.AgendaAcademica;
import com.universidad.sync.domain.entities.EspacioCampus;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EspacioCampus laboratorioLinux = new EspacioCampus(101, 30, List.of("Proyector", "PCs Arch Linux"), false);

        // Profiles (Users)
        AgendaAcademica agendaDocente = new AgendaAcademica(5001);
        AgendaAcademica agendaEstudiante1 = new AgendaAcademica(202601);
        AgendaAcademica agendaEstudiante2 = new AgendaAcademica(202602);

        // Orchestrations

        // Lista con estudiantes, docente y sala
        List<Integer> grupoEstudiantes = List.of(agendaEstudiante1.getIdPropietario(), agendaEstudiante2.getIdPropietario());
        ReservaAsesoria nuevaReserva = new ReservaAsesoria(agendaDocente.getIdPropietario(), grupoEstudiantes, laboratorioLinux);

        // Pipeline
        String fechaSugerida = "2026-04-18 14:00";

        System.out.println("--- INICIANDO SISTEMA DE RESERVAS ---");

        if (nuevaReserva.verificarDisponibilidad(fechaSugerida)) {
            System.out.println("Disponibilidad confirmada. Aforo y mantenimiento OK.");

            //Se bloquea el espacio (se aparta)
            nuevaReserva.bloquearEspacio(fechaSugerida);

            //Se confirma que todos aceptan y se emiten los avisos
            nuevaReserva.confirmarMatchDeHorarios();

            //Se exporta el ticket final
            System.out.println("\n--- TICKET GENERADO ---");
            System.out.println(nuevaReserva.exportarDetalleMarkdown());
        } else {
            System.out.println("No se pudo realizar la reserva. Choque de horarios o sala no apta.");
        }
    }
}