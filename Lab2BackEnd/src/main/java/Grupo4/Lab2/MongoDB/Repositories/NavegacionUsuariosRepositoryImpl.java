package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.DTO.ClienteBuscaPeroNoCompra;
import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.Entities.NavegacionUsuariosEntity;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.Repositories.ClienteRepository;
import Grupo4.Lab2.Repositories.PedidosRepository;
import Grupo4.Lab2.Repositories.ProductoRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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

    // Query 5
    public AggregateIterable<ClienteBuscaPeroNoCompra> getClientesQueBuscaronPeroNoCompraron(){
        MongoCollection<NavegacionUsuariosEntity> collection = getCollection();
        AggregateIterable<ClienteBuscaPeroNoCompra> clientes = collection.aggregate(Arrays.asList(
                Aggregates.unwind("$eventos"),
                Aggregates.match(Filters.eq("eventos.tipo", "busqueda")),
                Aggregates.group("$cliente_id", Accumulators.push("productos_buscados", "$eventos.valor")),
                Aggregates.project(
                        Projections.fields(
                                Projections.computed("cliente_id", "$_id"),
                                Projections.include("productos_buscados")
                        )
                )
        ), ClienteBuscaPeroNoCompra.class);
        return clientes;
    }
}
