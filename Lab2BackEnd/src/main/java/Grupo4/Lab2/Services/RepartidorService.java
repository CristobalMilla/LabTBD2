package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.RepartidorEntregaDTO;
import Grupo4.Lab2.DTO.RepartidorVistaDTO;
import Grupo4.Lab2.Entities.RepartidorEntity;
import Grupo4.Lab2.Repositories.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repo;

    public List<RepartidorEntity> findall(){
        return repo.findAll();
    }
    public RepartidorEntity findById(Long id){
        return repo.findById(id);
    }
    public void save(RepartidorEntity repartidor){
        repo.save(repartidor);
    }
    public void deleteById(Long id){
        repo.deleteById(id);
    }
    public void update(RepartidorEntity repartidor){
        repo.update(repartidor);
    }
    public List<RepartidorEntity> mejoresRepartidores(){
        return repo.mejoresRepartidores();
    }
    public List<RepartidorEntregaDTO> tiemposRepartidoresEntregaMinutos(){
        return repo.tiemposRepartidoresEntregaMinutos();
    }
    public List<RepartidorVistaDTO> repartidoresVista(){
        return repo.repartidoresVista();
    }
}
