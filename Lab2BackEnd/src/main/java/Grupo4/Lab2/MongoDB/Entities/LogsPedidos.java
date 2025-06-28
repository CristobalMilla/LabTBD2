package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

public class LogsPedidos {
    @BsonId
    ObjectId id_pedido;
    List<EventoPedido> eventos;

    public LogsPedidos(ObjectId id_pedido, List<EventoPedido> eventos) {
        this.id_pedido = id_pedido;
        this.eventos = eventos;
    }

    public LogsPedidos() {
    }

    public LogsPedidos(Long idPedido, List<EventoPedido> eventos) {
        this.id_pedido = new ObjectId(idPedido.toString());
        this.eventos = eventos;
    }

    public ObjectId getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(ObjectId id_pedido) {
        this.id_pedido = id_pedido;
    }

    public List<EventoPedido> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoPedido> eventos) {
        this.eventos = eventos;
    }
}
