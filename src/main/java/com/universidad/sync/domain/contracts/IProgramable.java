package com.universidad.sync.domain.contracts;

public interface IProgramable {


    boolean verificarDisponibilidad(String fechaHora);
    void bloquearEspacio(String fechaHora);

}
