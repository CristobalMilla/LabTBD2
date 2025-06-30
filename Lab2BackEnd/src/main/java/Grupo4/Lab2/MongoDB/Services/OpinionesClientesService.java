package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.Entities.ClienteEntity;
import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.DTO.Query2DTO;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Repositories.OpinionesClientesRepository;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import Grupo4.Lab2.Repositories.ClienteRepository;
import Grupo4.Lab2.Repositories.EmpresaRepository;
import com.mongodb.client.AggregateIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OpinionesClientesService {

    private final OpinionesClientesRepository opinionesClientesRepository;
    private static final String OPINION_ID_SEQUENCE = "opinion_id_sequence";
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepo;

    @Autowired
    public OpinionesClientesService(OpinionesClientesRepository opinionesClientesRepository, ClienteRepository clienteRepository, EmpresaRepository empresaRepo) {
        this.opinionesClientesRepository = opinionesClientesRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepo = empresaRepo;
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
        AggregateIterable<PromedioPuntuacionXEmpresaDTO> promedios = opinionesClientesRepository.getPromedioDePuntuacionXEmpresa();
        List<PromedioPuntuacionXEmpresaDTO> promediosList = new ArrayList<>();

        for (PromedioPuntuacionXEmpresaDTO promedio : promedios) {
            String nombreEmpresa = empresaRepo.findById(promedio.getEmpresa_id()).getNombre();
            promedio.setNombre_empresa(nombreEmpresa);
            promediosList.add(promedio);
        }
        return promediosList;
    }

    public List<OpinionStatsPorHoraDTO> getStatsPorHora() {
        return opinionesClientesRepository.getStatsPorHora();
    }

    public List<Query2DTO> getOpinionesQuery2() {
        List<OpinionesClientes> opiniones = opinionesClientesRepository.getOpinionesQuery2();
        List<Query2DTO> opinionesDTO = new ArrayList<>();
        for (OpinionesClientes opinion : opiniones) {
            ClienteEntity cliente = clienteRepository.findById(opinion.getCliente_id());
            if (cliente != null) {
                opinionesDTO.add(new Query2DTO(cliente.getNombre(),opinion.getComentarios()));
            }

        }
        return opinionesDTO;
    }
}
