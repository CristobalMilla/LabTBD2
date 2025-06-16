package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

public class PuntoInteresEntity {

    private long interes_id;
    private String nombre;
    @JsonIgnore
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
    @JsonIgnore
    public Point getUbicacion() {
        return ubicacion;
    }
    @JsonIgnore
    public void setUbicacion(Point ubicacion) {
        this.ubicacion = ubicacion;
    }
    @JsonProperty("ubicacion")
    public String getUbicacionWkt() {
        return ubicacion != null ? ubicacion.toText() : null;
    }
    @JsonProperty("ubicacion")
    public void setUbicacionWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isEmpty()) {
            this.ubicacion = null;
        } else {
            this.ubicacion = (Point) new WKTReader().read(wkt);
        }
    }
}
