package Grupo4.Lab2.MongoDB.DTO;

import java.time.Instant;

public class CambiarEstadoLogPedidoDTO {
    private Long pedido_id;
    private String estado;
    private Instant timestamp;

    public CambiarEstadoLogPedidoDTO(Long pedido_id, String estado, Instant timestamp) {
        this.pedido_id = pedido_id;
        this.estado = estado;
        this.timestamp = timestamp;
    }

    public CambiarEstadoLogPedidoDTO() {
    }

    public Long getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
