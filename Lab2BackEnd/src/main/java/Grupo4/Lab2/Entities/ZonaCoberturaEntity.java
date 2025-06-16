package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;

public class ZonaCoberturaEntity {
    private long zona_id;
    private String nombre;

    @JsonIgnore
    private Polygon geom;          // interno JTS

    public ZonaCoberturaEntity() { }

    public ZonaCoberturaEntity(long zona_id, String nombre, Polygon geom) {
        this.zona_id = zona_id;
        this.nombre = nombre;
        this.geom = geom;
    }

    public long getZona_id() { return zona_id; }
    public void setZona_id(long zona_id) { this.zona_id = zona_id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // acceso interno al polygon
    @JsonIgnore
    public Polygon getGeomPolygon() { return geom; }
    @JsonIgnore
    public void setGeomPolygon(Polygon geom) { this.geom = geom; }

    // acceso externo como WKT en JSON
    @JsonProperty("geom")
    public String getGeomWkt() {
        return geom != null ? geom.toText() : null;
    }

    @JsonProperty("geom")
    public void setGeomWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isBlank()) {
            this.geom = null;
        } else {
            this.geom = (Polygon)new WKTReader().read(wkt);
            this.geom.setSRID(4326);
        }
    }

    // Added setter for geom
    public void setGeom(org.locationtech.jts.geom.Polygon geom) {
        this.geom = geom;
    }
    
    // Added getter for geom if required
    public org.locationtech.jts.geom.Polygon getGeom() {
        return geom;
    }
}
