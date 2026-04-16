package com.universidad.sync.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EspacioCampus {

    private Integer idEspacio;
    private Integer capacidadMaxima;
    private List<String> recursosTecnicos;
    private boolean enMantenimiento;

    public boolean verificarAforo(int cantidadPersonas) {
        return cantidadPersonas <= this.capacidadMaxima;
    }

    public void habilitarMantenimiento() {
        this.enMantenimiento = true;
    }
}