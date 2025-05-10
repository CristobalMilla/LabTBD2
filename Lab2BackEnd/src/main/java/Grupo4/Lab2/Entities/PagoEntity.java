package Grupo4.Lab2.Entities;

import java.math.BigDecimal;

public class PagoEntity {
    private long pago_id;
    private long pedido_id;
    private long medio_id;
    private BigDecimal monto;

    public PagoEntity(long pago_id, long pedido_id, long medio_id, BigDecimal monto) {
        this.pago_id = pago_id;
        this.pedido_id = pedido_id;
        this.medio_id = medio_id;
        this.monto = monto;
    }

    public PagoEntity() {
    }

    public long getPagoId() {
        return pago_id;
    }

    public long getPedidoId() {
        return pedido_id;
    }

    public long getMedioId() {
        return medio_id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setPagoId(long pago_id) {
        this.pago_id = pago_id;
    }

    public void setPedidoId(long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public void setMedioId(long medio_id) {
        this.medio_id = medio_id;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
