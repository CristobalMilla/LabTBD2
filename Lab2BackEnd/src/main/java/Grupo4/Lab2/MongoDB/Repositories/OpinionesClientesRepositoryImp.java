package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import Grupo4.Lab2.Config.MongoDbConfig.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OpinionesClientesRepositoryImp implements OpinionesClientesRepository{
    MongoDatabase mongoDatabase = MongoDB();

    public List<OpinionesClientes> findAll() {
        MongoCollection<OpinionesClientes> mongo = mongoDatabase.getCollection("opiniones_clientes", OpinionesClientes.class);
        return mongo.find().into(new ArrayList<>());
    }

    public List<OpinionesClientes> findByEmpresaId(long empresa_id) {
        MongoCollection<OpinionesClientes> mongo = mongoDatabase.getCollection("opiniones_clientes", OpinionesClientes.class);
        Bson filter = Filters.eq("empresa_id", empresa_id);
        return mongo.find().sort(filter).into(new ArrayList<>());
        //Le da toc al lucas
    }


    public List<OpinionesClientes> findByPuntuacion(int puntuacion) {
        return List.of();
    }

    public void save(OpinionesClientes opinionesClientes) {
        MongoCollection<OpinionesClientes> mongo = mongoDatabase.getCollection("opiniones_clientes", OpinionesClientes.class);
        mongo.insertOne(opinionesClientes);
    }

    public void delete(long id){
        MongoCollection<OpinionesClientes> mongo = mongoDatabase.getCollection("opiniones_clientes", OpinionesClientes.class);
        Bson filter = Filters.eq("_id", id);
        mongo.deleteOne(filter);
    }


}
