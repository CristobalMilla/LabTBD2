package Grupo4.Lab2.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

public class RepartidorEntity {
    private long repartidor_id;
    private String nombre;
    private String telefono;
    private Boolean disponible;

    @JsonIgnore
    private Point ubicacion_actual; 
    public RepartidorEntity() { }
    public RepartidorEntity(long id, String nombre, String tel, Boolean disp, Point ubic) {
      this.repartidor_id = id;
      this.nombre = nombre;
      this.telefono = tel;
      this.disponible = disp;
      this.ubicacion_actual = ubic;
    }

    public long getRepartidor_id() { return repartidor_id; }
    public void setRepartidor_id(long repartidor_id) { this.repartidor_id = repartidor_id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }


    @JsonIgnore
    public Point getUbicacion_actual() {
      return ubicacion_actual;
    }
    @JsonIgnore
    public void setUbicacion_actual(Point ubicacion_actual) {
      this.ubicacion_actual = ubicacion_actual;
    }

    @JsonProperty("ubicacion_actual")
    public String getUbicacionWkt() {
      return ubicacion_actual != null
        ? ubicacion_actual.toText() 
        : null;
    }
    @JsonProperty("ubicacion_actual")
    public void setUbicacionWkt(String wkt) throws Exception {
      if (wkt == null || wkt.isBlank()) {
        this.ubicacion_actual = null;
      } else {
        this.ubicacion_actual = (Point) new WKTReader().read(wkt);
      }
    }
}
