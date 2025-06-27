package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class HistorialRepartidoresRepositoryImp implements HistorialRepartidoresRepository {
    @Autowired
    private MongoDatabase database;
    private static final String COLLECTION_NAME = "historial_repartidores";

    private MongoCollection<HistorialRepartidores> getCollection() {
        return database.getCollection(COLLECTION_NAME, HistorialRepartidores.class);
    }

    public List<HistorialRepartidores> findAll() {
        return getCollection().find().into(new ArrayList<>());
    }

    public HistorialRepartidores findById(long historial_repartidor_id) {
        return getCollection().find(eq("_id", historial_repartidor_id)).first();
    }

    public HistorialRepartidores findByRepartidorId(long repartidor_id) {
        return getCollection().find(eq("repartidor_id", repartidor_id)).first();
    }

    public HistorialRepartidores save(HistorialRepartidores historial) {
        getCollection().insertOne(historial);
        return historial;
    }

    public HistorialRepartidores update(long historial_repartidor_id, HistorialRepartidores historial) {
        getCollection().replaceOne(eq("_id", historial_repartidor_id), historial);
        return historial;
    }

    public void delete(long historial_repartidor_id) {
        DeleteResult result = getCollection().deleteOne(eq("_id", historial_repartidor_id));
        if (result.getDeletedCount() == 0) {
            System.out.println("No se elimino ningun documento ya que el id:" + historial_repartidor_id + " no estaba en ninguno.");
        }
    }

    public long count() {
        // Count the total number of documents in the collection.
        return getCollection().countDocuments();
    }
}
