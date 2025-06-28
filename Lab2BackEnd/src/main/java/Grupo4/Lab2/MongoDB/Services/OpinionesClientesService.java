package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Repositories.OpinionesClientesRepository;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OpinionesClientesService {

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
        OpinionesClientes existingOpinion = opinionesClientesRepository.findById(id);
        if (existingOpinion == null) {
            System.out.println("Opinion con ID " + id + " no se encontro.");
            return null;
        }
        opinion.setOpinion_id(id);
        return opinionesClientesRepository.update(id, opinion);
    }

    public void deleteOpinion(long id) {
        opinionesClientesRepository.delete(id);
    }

    public long countOpiniones() {
        return opinionesClientesRepository.count();
    }

    public List<PromedioPuntuacionXEmpresaDTO> getPuntuacionPromedioXEmpresa() {
        return opinionesClientesRepository.getPromedioDePuntuacionXEmpresa();
    }

    public List<OpinionStatsPorHoraDTO> getStatsPorHora() {
        return opinionesClientesRepository.getStatsPorHora();
    }
}
