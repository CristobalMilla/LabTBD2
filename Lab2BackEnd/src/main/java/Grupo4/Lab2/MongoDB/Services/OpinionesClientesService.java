package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Repositories.OpinionesClientesRepository;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// This service class handles the business logic for OpinionesClientes.
// It uses the repository to interact with the MongoDB database.
@Service
public class OpinionesClientesService {

    // Autowire the repository to access MongoDB operations.
    private final OpinionesClientesRepository opinionesClientesRepository;
    private static final String OPINION_ID_SEQUENCE = "opinion_id_sequence";

    @Autowired
    public OpinionesClientesService(OpinionesClientesRepository opinionesClientesRepository) {
        this.opinionesClientesRepository = opinionesClientesRepository;
    }

    public List<OpinionesClientes> getAllOpiniones() {
        return opinionesClientesRepository.findAll();
    }

    public OpinionesClientes getOpinionById(long id) {
        return opinionesClientesRepository.findById(id);
    }

    public OpinionesClientes createOpinion(OpinionesClientes opinion) {
        // Genera y asigna el nuevo ID único
        long newId = opinionesClientesRepository.getNextSequenceId(OPINION_ID_SEQUENCE);
        opinion.setOpinion_id(newId);
        
        // Guarda la opinión con el nuevo ID
        return opinionesClientesRepository.save(opinion);
    }

    public OpinionesClientes updateOpinion(long id, OpinionesClientes opinion) {
        // Check if the opinion exists before updating.
        OpinionesClientes existingOpinion = opinionesClientesRepository.findById(id);
        if (existingOpinion == null) {
            // Or throw a custom exception (e.g., NotFoundException)
            System.out.println("Opinion with ID " + id + " not found for update.");
            return null;
        }
        // Ensure the ID in the payload matches the path variable ID
        opinion.setOpinion_id(id);
        return opinionesClientesRepository.update(id, opinion);
    }

    public void deleteOpinion(long id) {
        opinionesClientesRepository.delete(id);
    }

    public long countOpiniones() {
        return opinionesClientesRepository.count();
    }

    public List<OpinionStatsPorHoraDTO> getStatsPorHora() {
        return opinionesClientesRepository.getStatsPorHora();
    }
}
