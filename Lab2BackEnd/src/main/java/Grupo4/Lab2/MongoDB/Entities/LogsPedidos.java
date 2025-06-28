package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;

public class LogsPedidos {
    @BsonId
    Long id_pedido;
    List<EventoPedido> eventos;

    public LogsPedidos(Long id_pedido, List<EventoPedido> eventos) {
        this.id_pedido = id_pedido;
        this.eventos = eventos;
    }

    public LogsPedidos() {
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public List<EventoPedido> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoPedido> eventos) {
        this.eventos = eventos;
    }
}
