package Grupo4.Lab2.Entities;

import java.time.LocalDateTime;

public class PedidosEntity {

    private long pedido_id;
    private int cliente_id;
    private int empresa_id;
    private int repartidor_id;
    private LocalDateTime fecha;
    private LocalDateTime fecha_entrega;
    private String estado;

    public PedidosEntity(long pedido_id, int cliente_id, int empresa_id, int repartidor_id, LocalDateTime fecha, LocalDateTime fecha_entrega, String estado) {
        this.pedido_id = pedido_id;
        this.cliente_id = cliente_id;
        this.empresa_id = empresa_id;
        this.repartidor_id = repartidor_id;
        this.fecha = fecha;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
    }

    public PedidosEntity() {
    }

    public long getPedido_id() {
        return pedido_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public int getRepartidor_id() {
        return repartidor_id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public LocalDateTime getFecha_entrega() {
        return fecha_entrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setPedido_id(long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public void setRepartidor_id(int repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setFecha_entrega(LocalDateTime fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
