package Grupo4.Lab2.MongoDB.Entities;

import java.time.Instant;

public class EventoNavegacion {

    private String tipo;       // ej: "busqueda", "click", "filtro"
    private String valor;      // nombre producto ej: "paracetamol"
    private Instant timestamp;
}
