package Grupo4.Lab2.Entities;


import org.locationtech.jts.geom.Point;

public class RepartidorEntity {



    private long repartidor_id;
    private String nombre;
    private String telefono;
    private Boolean disponible;
    private Point ubicacion_actual;

    public RepartidorEntity(long repartidor_id, String nombre, String telefono, Boolean disponible, Point ubicacion_actual) {
        this.repartidor_id = repartidor_id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.disponible = disponible;
        this.ubicacion_actual = ubicacion_actual;
    }

    public RepartidorEntity() {

    }

    public long getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(long repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Point getUbicacion_actual() {
        return ubicacion_actual;
    }
    public void setUbicacion_actual(Point ubicacion_actual) {
        this.ubicacion_actual = ubicacion_actual;
    }
}
