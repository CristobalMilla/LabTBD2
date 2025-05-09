package Grupo4.Lab2.Entities;

public class CalificacionEntity {
    private long calificacion_id;
    private long pedido_id;
    private int puntuacion;
    private String comentario;

    public CalificacionEntity(long calificacion_id, long pedido_id, int puntuacion, String comentario) {
        this.calificacion_id = calificacion_id;
        this.pedido_id = pedido_id;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public long getCalificacion_id() {
        return calificacion_id;
    }

    public long getPedido_id() {
        return pedido_id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setCalificacion_id(long calificacion_id) {
        this.calificacion_id = calificacion_id;
    }

    public void setPedido_id(long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
