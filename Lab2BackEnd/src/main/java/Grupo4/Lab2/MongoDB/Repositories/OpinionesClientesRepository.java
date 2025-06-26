package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionesClientesRepository {

    List<OpinionesClientes> findAll();

    // Método para buscar opiniones por el ID de la empresa
    List<OpinionesClientes> findByEmpresaId(long empresa_id);

    // Método para buscar opiniones por la puntuación
    List<OpinionesClientes> findByPuntuacion(int puntuacion);

    void save(OpinionesClientes opinionesClientes);

    void delete(long id);
}