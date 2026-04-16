package com.universidad.sync.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class AgendaAcademica {

    private Integer idPropietario;
    private List<String> bloquesOcupados;
    private Date ultimaActualizacion;

    public AgendaAcademica(Integer idPropietario) {
        this.idPropietario = idPropietario;
        this.bloquesOcupados = new ArrayList<>();
        this.ultimaActualizacion = new Date();
    }

    public void agregarClase(String bloqueUsado) {
        this.bloquesOcupados.add(bloqueUsado);
        this.ultimaActualizacion = new Date();
    }

public List<String> obtenerTiempoLibre() {
        List<String> todosLosBloques = new ArrayList<>(Arrays.asList(
            "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"
        ));
        todosLosBloques.removeAll(this.bloquesOcupados);

        return todosLosBloques;
    }

    public void liberarBloque(String bloqueTiempo) {
        this.bloquesOcupados.remove(bloqueTiempo);
        this.ultimaActualizacion = new Date();
    }
}