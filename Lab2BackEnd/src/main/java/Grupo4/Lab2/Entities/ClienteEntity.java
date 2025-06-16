package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

public class ClienteEntity {
    private long cliente_id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    @JsonIgnore
    private Point ubicacion;          // tu JTS Point interno

    public ClienteEntity() {}

    public ClienteEntity(long cliente_id, String nombre, String direccion, String email, String telefono, Point ubicacion) {
        this.cliente_id = cliente_id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        return ubicacion != null
            ? ubicacion.toText()   // “POINT(lng lat)”
            : null;
    }

    @JsonProperty("ubicacion")
    public void setUbicacionWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isBlank()) {
            this.ubicacion = null;
        } else {
            this.ubicacion = (Point) new WKTReader().read(wkt);
        }
    }
}
