package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.CalificacionEntity;
import Grupo4.Lab2.Repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionService {
    @Autowired
    private CalificacionRepository calificacionRepo;

    public CalificacionEntity getById(long idCalificacion) {
        return calificacionRepo.findById(idCalificacion);
    }

    public List<CalificacionEntity> getAll() {
        return calificacionRepo.findAll();
    }

    public void save(CalificacionEntity calificacion) {
        calificacionRepo.save(calificacion);
    }

    public void update(CalificacionEntity calificacion) {
        calificacionRepo.update(calificacion);
    }

    public void delete(long idCalificacion) {
        calificacionRepo.deleteById(idCalificacion);
    }
}
