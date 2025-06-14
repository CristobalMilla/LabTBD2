package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.CallesEntity;
import Grupo4.Lab2.Repositories.CallesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallesService {
    @Autowired
    private CallesRepository callesRepo;

    public CallesEntity getById(long id) {
        return callesRepo.findById(id);
    }

    public List<CallesEntity> getAll() {
        return callesRepo.findAll();
    }

    public void save(CallesEntity calle) {
        callesRepo.save(calle);
    }

    public void update(CallesEntity calle) {
        callesRepo.update(calle);
    }

    public void delete(long id) {
        callesRepo.deleteById(id);
    }
}
