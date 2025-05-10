package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.MediosPagoEntity;
import Grupo4.Lab2.Repositories.MediosPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediosPagoService {
    @Autowired
    private MediosPagoRepository mediosPagoRepository;

    public List<MediosPagoEntity> findAll(){
        return mediosPagoRepository.findAll();
    }
    public MediosPagoEntity findById(long medio_id){
        return mediosPagoRepository.findById(medio_id);
    }
    public void save(MediosPagoEntity mediosPago){
        mediosPagoRepository.save(mediosPago);
    }
    public void update(MediosPagoEntity mediosPago){
        mediosPagoRepository.update(mediosPago);
    }
    public void delete(long medio_id){
        mediosPagoRepository.deleteById(medio_id);
    }
}
