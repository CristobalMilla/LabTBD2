package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.PuntoInteresEntity;
import Grupo4.Lab2.Repositories.ClienteRepository;
import Grupo4.Lab2.Repositories.PuntoInteresRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoInteresService {

    @Autowired
    private PuntoInteresRepository repo;

    @Autowired
    private ClienteRepository repoCliente;

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

    public List<PuntoInteresEntity> findNearby(Long idCliente){
        Point point = repoCliente.findById(idCliente).getUbicacion();
        return repo.findNearby(point);
    }
}
