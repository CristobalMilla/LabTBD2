package Grupo4.Lab2.DTO;

public class CoordenadaDTO {
    private double longitude;
    private double latitude;

    public CoordenadaDTO(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CoordenadaDTO() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
