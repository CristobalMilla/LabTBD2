package Grupo4.Lab2.DTO;

public class RepartidorVistaDTO {
    String repartidor;
    int pedidos;
    Double calificacion_promedio;

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public Double getCalificacion_promedio() {
        return calificacion_promedio;
    }

    public void setCalificacion_promedio(Double calificacion_promedio) {
        this.calificacion_promedio = calificacion_promedio;
    }
}
