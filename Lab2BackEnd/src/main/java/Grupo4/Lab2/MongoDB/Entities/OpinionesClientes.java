package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;


public class OpinionesClientes {

    @BsonId
    private long opinion_id;
    private String comentarios;
    private int puntuacion;
    private LocalDate fecha;
    private long cliente_id;
    private long empresa_id;

    public OpinionesClientes(long opinion_id, String comentarios, int puntuacion, LocalDate fecha, long cliente_id, long empresa_id) {
        this.opinion_id = opinion_id;
        this.comentarios = comentarios;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.cliente_id = cliente_id;
        this.empresa_id = empresa_id;
    }

    public long getOpinion_id() {
        return opinion_id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public long getEmpresa_id() {
        return empresa_id;
    }

    public void setOpinion_id(long opinion_id) {
        this.opinion_id = opinion_id;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setEmpresa_id(long empresa_id) {
        this.empresa_id = empresa_id;
    }
}
