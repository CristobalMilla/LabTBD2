package Grupo4.Lab2.MongoDB.Entities;

import java.time.Instant;

public class Movimiento {
    private String ubicacion; // en wkt
    private Instant tiempo;

    public Movimiento() {}

    public Movimiento(String ubicacion, Instant tiempo) {
        this.ubicacion = ubicacion;
        this.tiempo = tiempo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Instant getTiempo() {
        return tiempo;
    }

    public void setTiempo(Instant tiempo) {
        this.tiempo = tiempo;
    }
}
