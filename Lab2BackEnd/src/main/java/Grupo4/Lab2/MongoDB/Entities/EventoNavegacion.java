package Grupo4.Lab2.MongoDB.Entities;

import java.time.Instant;

public class EventoNavegacion {

    private String tipo;       // ej: "busqueda", "click", "filtro"
    private String valor;      // nombre producto ej: "paracetamol"
    private Instant timestamp;

    public EventoNavegacion() {
    }

    public EventoNavegacion(String tipo, String valor, Instant timestamp) {
        this.tipo = tipo;
        this.valor = valor;
        this.timestamp = timestamp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
