package Grupo4.Lab2.Entities;

import jakarta.persistence.Column;
import org.locationtech.jts.geom.Point;

public class PuntoInteresEntity {

    private long interes_id;
    private String nombre;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point ubicacion;

    public PuntoInteresEntity(long interes_id, String nombre, Point ubicacion) {
        this.interes_id = interes_id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }
    public PuntoInteresEntity() {
    }
    public long getInteres_id() {
        return interes_id;
    }
    public void setInteres_id(long interes_id) {
        this.interes_id = interes_id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Point getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Point ubicacion) {
        this.ubicacion = ubicacion;
    }
}
