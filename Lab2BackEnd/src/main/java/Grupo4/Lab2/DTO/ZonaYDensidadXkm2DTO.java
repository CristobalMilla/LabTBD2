package Grupo4.Lab2.DTO;

public class ZonaYDensidadXkm2DTO {
    private long zona_id;
    private double densidad_x_km2;
    private String geom;

    public ZonaYDensidadXkm2DTO(long zona_id, double densidad_x_km2, String geom) {
        this.zona_id = zona_id;
        this.densidad_x_km2 = densidad_x_km2;
        this.geom = geom;
    }

    public long getZona_id() {
        return zona_id;
    }

    public void setZona_id(long zona_id) {
        this.zona_id = zona_id;
    }

    public double getDensidad_x_km2() {
        return densidad_x_km2;
    }

    public void setDensidad_x_km2(double densidad_x_km2) {
        this.densidad_x_km2 = densidad_x_km2;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}
