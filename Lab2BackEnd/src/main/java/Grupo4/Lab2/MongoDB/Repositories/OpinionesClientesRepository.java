package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult; // For delete operations
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq; // For filtering documents

// Interface for OpinionesClientes data access operations in MongoDB.
public interface OpinionesClientesRepository {
    List<OpinionesClientes> findAll();
    OpinionesClientes findById(long id);
    OpinionesClientes save(OpinionesClientes opinion);
    OpinionesClientes update(long id, OpinionesClientes opinion);
    void delete(long id);
    long count();
    long getNextSequenceId(String sequenceName); // para manejar secuencias de ID
    List<OpinionStatsPorHoraDTO> getStatsPorHora();
}

