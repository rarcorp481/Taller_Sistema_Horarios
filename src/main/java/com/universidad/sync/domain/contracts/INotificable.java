package com.universidad.sync.domain.contracts;

public interface INotificable {

    void emitirAviso(Integer idUsuario, String detalle);
}
