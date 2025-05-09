package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.UrgenciaEntity;
import Grupo4.Lab2.Repositories.UrgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrgenciaService {

    @Autowired
    private UrgenciaRepository urgenciaRepo;

    // Guarda una nueva urgencia
    public UrgenciaEntity save(UrgenciaEntity urgencia) {
        urgenciaRepo.save(urgencia);
        return urgencia;
    }

    // Busca una urgencia por su ID
    public UrgenciaEntity findById(long id) {
        Optional<UrgenciaEntity> urgenciaOpt = urgenciaRepo.findById(id);
        return urgenciaOpt.orElseThrow(() -> new RuntimeException("Urgencia no encontrada con ID: " + id));
    }

    // Obtiene todas las urgencias
    public List<UrgenciaEntity> findAll() {
        return urgenciaRepo.findAll();
    }

    // Elimina una urgencia por su ID
    public void deleteById(long id) {
        urgenciaRepo.deleteById(id);
    }
}