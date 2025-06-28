package Grupo4.Lab2.MongoDB.Entities;

import java.time.Instant;


public class EventoPedido {
    String estado;
    Instant timestamp;

    public EventoPedido(String estado, Instant timestamp) {
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
