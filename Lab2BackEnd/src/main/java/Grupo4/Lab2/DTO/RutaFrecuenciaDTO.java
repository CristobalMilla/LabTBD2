package Grupo4.Lab2.DTO;

public class RutaFrecuenciaDTO {
    private String rutaEstimadaWkt;
    private int frecuencia;

    public String getRutaEstimadaWkt() {
        return rutaEstimadaWkt;
    }

    public void setRutaEstimadaWkt(String rutaEstimadaWkt) {
        this.rutaEstimadaWkt = rutaEstimadaWkt;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}