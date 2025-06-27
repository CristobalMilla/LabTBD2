package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import org.bson.types.ObjectId;

import java.util.List;

public interface HistorialRepartidoresRepository {
    List<HistorialRepartidores> findAll();
    HistorialRepartidores findById(ObjectId historial_repartidor_id);
    HistorialRepartidores findByRepartidorId(long repartidor_id);
    HistorialRepartidores save(HistorialRepartidores historial);
    HistorialRepartidores update(ObjectId historial_repartidor_id, HistorialRepartidores historial);
    void delete(ObjectId historial_repartidor_id);
    long count();
}
