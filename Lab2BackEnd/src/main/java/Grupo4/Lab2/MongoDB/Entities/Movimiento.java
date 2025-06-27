package Grupo4.Lab2.MongoDB.Entities;

import java.util.Date;

public class Movimiento {
    private String ubicacion; // en wkt
    private Date tiempo;

    public Movimiento(String ubicacion, Date tiempo) {
        this.ubicacion = ubicacion;
        this.tiempo = tiempo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }
}
