package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.PuntoInteresEntity;
import Grupo4.Lab2.Repositories.PuntoInteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoInteresService {

    @Autowired
    private PuntoInteresRepository repo;

    public void save(PuntoInteresEntity puntoInteres){
        repo.save(puntoInteres);
    }

    public void deleteById(Long id){
        repo.deleteById(id);
    }

    public void update(PuntoInteresEntity puntoInteres){
        repo.update(puntoInteres);
    }

    public PuntoInteresEntity findById(Long id){
        return repo.findById(id);
    }

    public List<PuntoInteresEntity> findAll(){
        return repo.findAll();
    }

}
