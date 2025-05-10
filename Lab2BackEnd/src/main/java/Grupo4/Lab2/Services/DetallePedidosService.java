package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.DetallePedidosEntity;
import Grupo4.Lab2.Repositories.DetallePedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidosService {

    @Autowired
    private DetallePedidosRepository detallePedidosRepository;

    public DetallePedidosEntity getById(long idDetalle) {
        return detallePedidosRepository.findById(idDetalle);
    }

    public List<DetallePedidosEntity> getAll() {
        return detallePedidosRepository.findAll();
    }

    public void save(DetallePedidosEntity detalle) {
        detallePedidosRepository.save(detalle);
    }

    public void update(DetallePedidosEntity detalle) {
        detallePedidosRepository.update(detalle);
    }

    public void delete(long pedidoId) {
        detallePedidosRepository.deleteById(pedidoId);
    }

}
