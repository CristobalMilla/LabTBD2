package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;

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
    // Query 1
    List<PromedioPuntuacionXEmpresaDTO> getPromedioDePuntiacionXEmpresa();
    List<OpinionStatsPorHoraDTO> getStatsPorHora();
}

