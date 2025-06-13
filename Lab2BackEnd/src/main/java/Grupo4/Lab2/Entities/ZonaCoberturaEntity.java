package Grupo4.Lab2.Entities;

import jakarta.persistence.Column;
import org.locationtech.jts.geom.Polygon;

public class ZonaCoberturaEntity {
    private long zona_id;
    private String nombre;
    //Cambiar si en la base de datos la columna se llama distinto
    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Polygon geom;

    public ZonaCoberturaEntity(long zona_id, String nombre, Polygon geom) {
        this.zona_id = zona_id;
        this.nombre = nombre;
        this.geom = geom;
    }
    public long getZona_id() {
        return zona_id;
    }
    public String getNombre() {
        return nombre;
    }
    public Polygon getGeom() {
        return geom;
    }
    public void setZona_id(long zona_id) {
        this.zona_id = zona_id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setGeom(Polygon geom) {
        this.geom = geom;
    }
}
