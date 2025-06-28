package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.NavegacionUsuariosEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NavegacionUsuariosRepositoryImpl implements NavegacionUsuariosRepository {

    @Autowired
    private MongoDatabase database;

    private static final String COLLECTION_NAME = "navegacion_usuarios";

    private MongoCollection<NavegacionUsuariosEntity> getCollection() {
        return database.getCollection(COLLECTION_NAME, NavegacionUsuariosEntity.class);
    }

    public List<NavegacionUsuariosEntity> findAll(){
        return getCollection().find().into(new ArrayList<>());
    }

    public NavegacionUsuariosEntity findById(ObjectId navegacion_id){
        return getCollection().find(Filters.eq("_id", navegacion_id)).first();
    }

    public NavegacionUsuariosEntity save(NavegacionUsuariosEntity navegacion){
        getCollection().insertOne(navegacion);
        return navegacion;
    }

    public NavegacionUsuariosEntity update(NavegacionUsuariosEntity navegacion){
        getCollection().replaceOne(Filters.eq("_id", navegacion.getNavegacion_usuarios_id()), navegacion);
        return navegacion;
    }

    public void delete(NavegacionUsuariosEntity navegacion){
        getCollection().deleteOne(Filters.eq("_id", navegacion.getNavegacion_usuarios_id()));
    }
}
