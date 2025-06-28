package Grupo4.Lab2.MongoDB.Entities;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

public class HistorialRepartidores {
    @BsonId
    private ObjectId historial_repartidor_id;

    private long repartidor_id;

    private List<Movimiento> ruta;

    public HistorialRepartidores() {
    }

    public HistorialRepartidores(ObjectId historial_repartidor_id, long repartidor_id, List<Movimiento> ruta) {
        this.historial_repartidor_id = historial_repartidor_id;
        this.repartidor_id = repartidor_id;
        this.ruta = ruta;
    }

    public ObjectId getHistorial_repartidor_id() {
        return historial_repartidor_id;
    }

    public void setHistorial_repartidor_id(ObjectId historial_repartidor_id) {
        this.historial_repartidor_id = historial_repartidor_id;
    }

    public long getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(long repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public List<Movimiento> getRuta() {
        return ruta;
    }

    public void setRuta(List<Movimiento> ruta) {
        this.ruta = ruta;
    }
}
