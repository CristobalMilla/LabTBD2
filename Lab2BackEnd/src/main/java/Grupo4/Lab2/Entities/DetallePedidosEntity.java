package Grupo4.Lab2.Entities;

public class DetallePedidosEntity {
    private long detalle_id;
    private long pedido_id;
    private long producto_id;
    private int cantidad;

    public DetallePedidosEntity(long detalle_id, int pedido_id, int producto_id, int cantidad) {
        this.detalle_id = detalle_id;
        this.pedido_id = pedido_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
    }

    public DetallePedidosEntity() {
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(long producto_id) {
        this.producto_id = producto_id;
    }

    public long getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public long getDetalle_id() {
        return detalle_id;
    }

    public void setDetalle_id(long detalle_id) {
        this.detalle_id = detalle_id;
    }
}
