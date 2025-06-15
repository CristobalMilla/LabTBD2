package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

public class EmpresaEntity {
    private long empresa_id;
    private String nombre;
    private String direccion;
    private String tipo_servicio;

    @JsonIgnore
    private Point ubicacion;      // tu JTS Point interno

    private String ubicacionWkt;  // WKT para JSON

    public EmpresaEntity(String nombre, long empresa_id, String direccion, String tipo_servicio, Point ubicacion) {
        this.nombre = nombre;
        this.empresa_id = empresa_id;
        this.direccion = direccion;
        this.tipo_servicio = tipo_servicio;
        this.ubicacion = ubicacion;
    }

    public EmpresaEntity() {
    }

    public long getEmpresaId() {
        return empresa_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipoServicio() {
        return tipo_servicio;
    }

    public void setEmpresaId(long empresa_id) {
        this.empresa_id = empresa_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipoServicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    @JsonIgnore
    public Point getUbicacion() { return ubicacion; }
    @JsonIgnore
    public void setUbicacion(Point u) { this.ubicacion = u; }

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
