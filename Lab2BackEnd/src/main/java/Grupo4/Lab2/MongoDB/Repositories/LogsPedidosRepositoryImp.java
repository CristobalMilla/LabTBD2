package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class LogsPedidosRepositoryImp implements LogsPedidosRepository {

    @Autowired
    private MongoDatabase database;

    private static final String COLLECTION_NAME = "logs_pedidos";

    private MongoCollection<LogsPedidos> getCollection() {
        return database.getCollection(COLLECTION_NAME, LogsPedidos.class);
    }

    @Override
    public List<LogsPedidos> findAll() {
        return getCollection().find().into(new java.util.ArrayList<>());
    }

    @Override
    public LogsPedidos findById(long id) {
        return getCollection().find(eq("id_pedido", id)).first();
    }

    @Override
    public LogsPedidos save(LogsPedidos log) {
        getCollection().insertOne(log);
        return log;
    }

    @Override
    public LogsPedidos update(long id, LogsPedidos log) {
        getCollection().replaceOne(eq("id_pedido", id), log);
        return log;
    }

    @Override
    public void delete(long id) {
        getCollection().deleteOne(eq("id_pedido", id));
    }

    @Override
    public long count(){
        return getCollection().countDocuments();
    }
}
