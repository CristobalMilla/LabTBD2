package Grupo4.Lab2.DTO;

public class RepartidorEntregaDTO {
    private String repartidor;
    private Double avgTiempoEntregaMin;

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public Double getAvgTiempoEntregaMin() {
        return avgTiempoEntregaMin;
    }

    public void setAvgTiempoEntregaMin(Double avgTiempoEntregaMin) {
        this.avgTiempoEntregaMin = avgTiempoEntregaMin;
    }
}
