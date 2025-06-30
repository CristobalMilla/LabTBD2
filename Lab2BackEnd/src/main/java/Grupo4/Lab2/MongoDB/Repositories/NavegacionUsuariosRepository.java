package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.DTO.ClienteBuscaPeroNoCompra;
import Grupo4.Lab2.MongoDB.Entities.NavegacionUsuariosEntity;
import com.mongodb.client.AggregateIterable;
import org.bson.types.ObjectId;

import java.util.List;

public interface NavegacionUsuariosRepository {
    List<NavegacionUsuariosEntity> findAll();
    NavegacionUsuariosEntity findById(ObjectId navegacion_id);
    NavegacionUsuariosEntity save(NavegacionUsuariosEntity navegacion);
    NavegacionUsuariosEntity update(NavegacionUsuariosEntity navegacion);
    void delete(NavegacionUsuariosEntity navegacion);
    // Query 5
    AggregateIterable<ClienteBuscaPeroNoCompra> getClientesQueBuscaronPeroNoCompraron();
}
