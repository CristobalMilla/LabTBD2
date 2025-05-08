package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.PagoEntity;
import Grupo4.Lab2.Repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    public PagoEntity findById(long id) {
        return pagoRepository.findById(id);
    }
    public List<PagoEntity> findAll() {
        return pagoRepository.findAll();
    }
    public List<PagoEntity> findByPedidoId(long id) {
        return pagoRepository.findByPedidoId(id);
    }
    public List<PagoEntity> findByMedioId(long id) {
        return pagoRepository.findByMedioId(id);
    }
    public void save(PagoEntity pago) {
        pagoRepository.save(pago);
    }
    public void update(PagoEntity pago) {
        pagoRepository.save(pago);
    }
    public void delete(long pago_id) {
        pagoRepository.deleteById(pago_id);
    }
}
