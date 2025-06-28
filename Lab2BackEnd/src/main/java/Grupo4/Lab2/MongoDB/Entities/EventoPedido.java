package Grupo4.Lab2.MongoDB.Entities;

import java.time.LocalDateTime;

public class EventoPedido {
    String estado;
    LocalDateTime timestamp;

    public EventoPedido(String estado, LocalDateTime timestamp) {
        this.estado = estado;
        this.timestamp = timestamp;
    }

    public EventoPedido() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
