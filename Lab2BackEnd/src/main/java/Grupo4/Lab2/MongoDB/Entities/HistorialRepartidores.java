package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;

public class HistorialRepartidores {
    @BsonId
    private long historial_repartidor_id;

    private long repartidor_id;

    private List<Movimiento> movimientos;

    public HistorialRepartidores() {
    }

    public HistorialRepartidores(long historial_repartidor_id, long repartidor_id, List<Movimiento> movimientos) {
        this.historial_repartidor_id = historial_repartidor_id;
        this.repartidor_id = repartidor_id;
        this.movimientos = movimientos;
    }

    public long getHistorial_repartidor_id() {
        return historial_repartidor_id;
    }

    public void setHistorial_repartidor_id(long historial_repartidor_id) {
        this.historial_repartidor_id = historial_repartidor_id;
    }

    public long getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(long repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
