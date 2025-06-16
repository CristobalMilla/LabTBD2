package Grupo4.Lab2.DTO;

public class ZonaYDensidadXkm2DTO {
    long zona_id;
    double densidad_x_km2;

    public ZonaYDensidadXkm2DTO(long zona_id, double densidad_x_km2) {
        this.zona_id = zona_id;
        this.densidad_x_km2 = densidad_x_km2;
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
}
