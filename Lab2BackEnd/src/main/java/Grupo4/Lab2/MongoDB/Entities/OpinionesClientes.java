package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;
import java.time.Instant; 

// This class represents the "OpinionesClientes" entity stored in MongoDB.
public class OpinionesClientes {

    // @BsonId maps this field to MongoDB's special "_id" field, which must be unique.
    // Using 'long' for opinion_id is acceptable, but ObjectId is also common for _id.
    @BsonId
    private long opinion_id;
    private String comentarios;
    private int puntuacion;
    private Instant fecha; // se cambia de localdate a Instant para manejar mejor las fechas en MongoDB
    private long cliente_id; // ID of the client who gave the opinion
    private long empresa_id; // ID of the company the opinion is for

    // --- Constructors ---
    // Default constructor is required by the POJO codec for object creation.
    public OpinionesClientes() {
    }

    // Parameterized constructor for easy object creation.
    public OpinionesClientes(long opinion_id, String comentarios, int puntuacion, Instant fecha, long cliente_id, long empresa_id) {
        this.opinion_id = opinion_id;
        this.comentarios = comentarios;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.cliente_id = cliente_id;
        this.empresa_id = empresa_id;
    }

    // --- Getters and Setters ---
    // These methods are essential for the POJO codec to access and set field values.
    public long getOpinion_id() {
        return opinion_id;
    }

    public void setOpinion_id(long opinion_id) {
        this.opinion_id = opinion_id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public long getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(long empresa_id) {
        this.empresa_id = empresa_id;
    }

    // --- toString method for easy logging/debugging ---
    @Override
    public String toString() {
        return "OpinionesClientes{" +
                "opinion_id=" + opinion_id +
                ", comentarios='" + comentarios + '\'' +
                ", puntuacion=" + puntuacion +
                ", fecha=" + fecha +
                ", cliente_id=" + cliente_id +
                ", empresa_id=" + empresa_id +
                '}';
    }
}