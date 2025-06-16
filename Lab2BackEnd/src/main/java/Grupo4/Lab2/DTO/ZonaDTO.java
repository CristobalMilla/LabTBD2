package Grupo4.Lab2.DTO;

public class ZonaDTO {
    private long zonaId;
    private String nombre;
    private String geom;
    private String clienteUbicacion;

    public ZonaDTO() {
    }

    public ZonaDTO(long zonaId, String nombre, String geom, String clienteUbicacion) {
        this.zonaId = zonaId;
        this.nombre = nombre;
        this.geom = geom;
    }

    public String getClienteUbicacion() {
        return clienteUbicacion;
    }

    public void setClienteUbicacion(String clienteUbicacion) {
        this.clienteUbicacion = clienteUbicacion;
    }

    public long getZonaId() {
        return zonaId;
    }

    public void setZonaId(long zonaId) {
        this.zonaId = zonaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}
