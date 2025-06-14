package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.CallesVerticesEntity;
import Grupo4.Lab2.Repositories.CallesVerticesRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallesVerticesService { 

    @Autowired
    private CallesVerticesRepository callesVerticesRepo; 

    public CallesVerticesEntity getById(long id) {
        return callesVerticesRepo.findById(id);
    }

    public List<CallesVerticesEntity> getAll() { 
        return callesVerticesRepo.findAll();
    }
}
