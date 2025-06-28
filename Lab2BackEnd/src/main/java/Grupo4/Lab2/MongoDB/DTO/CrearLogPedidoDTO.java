package Grupo4.Lab2.MongoDB.DTO;

import java.time.Instant;

public class CrearLogPedidoDTO {
    private Long pedido_id;
    private Instant timestamp;

    public CrearLogPedidoDTO(Long pedido_id, Instant timestamp) {
        this.pedido_id = pedido_id;
        this.timestamp = timestamp;
    }

    public CrearLogPedidoDTO() {
    }

    public Long getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
