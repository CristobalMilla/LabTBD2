package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

public class NavegacionUsuariosEntity {

    @BsonId
    private ObjectId navegacion_usuarios_id;

    private String cliente_id;

    private List<EventoNavegacion> eventos;

    public NavegacionUsuariosEntity() {
    }

    public NavegacionUsuariosEntity(ObjectId navegacion_usuarios_id, String cliente_id, List<EventoNavegacion> eventos) {
        this.navegacion_usuarios_id = navegacion_usuarios_id;
        this.cliente_id = cliente_id;
        this.eventos = eventos;
    }

    public ObjectId getNavegacion_usuarios_id() {
        return navegacion_usuarios_id;
    }

    public void setNavegacion_usuarios_id(ObjectId navegacion_usuarios_id) {
        this.navegacion_usuarios_id = navegacion_usuarios_id;
    }

    public List<EventoNavegacion> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoNavegacion> eventos) {
        this.eventos = eventos;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }
}
