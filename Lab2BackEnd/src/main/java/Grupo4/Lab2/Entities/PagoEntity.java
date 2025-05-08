package Grupo4.Lab2.Entities;

import java.math.BigDecimal;

public class PagoEntity {
    private long pagoId;
    private long pedidoId;
    private long medioId;
    private BigDecimal monto;

    public PagoEntity(long pagoId, long pedidoId, long medioId, BigDecimal monto) {
        this.pagoId = pagoId;
        this.pedidoId = pedidoId;
        this.medioId = medioId;
        this.monto = monto;
    }

    public PagoEntity() {
    }

    public long getPagoId() {
        return pagoId;
    }

    public long getPedidoId() {
        return pedidoId;
    }

    public long getMedioId() {
        return medioId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setPagoId(long pagoId) {
        this.pagoId = pagoId;
    }

    public void setPedidoId(long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setMedioId(long medioId) {
        this.medioId = medioId;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
