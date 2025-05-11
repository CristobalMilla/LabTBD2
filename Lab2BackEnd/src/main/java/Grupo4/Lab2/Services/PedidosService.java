package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Entities.PedidosEntity;
import Grupo4.Lab2.Repositories.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    public PedidosEntity getById(long idPedido) {
        return pedidosRepository.findById(idPedido);
    }

    public List<PedidosEntity> getAll() {
        return pedidosRepository.findAll();
    }

    public boolean registrarPedido(RegistrarPedidoDTO dto) {
        if (dto.getProductos() == null || dto.getCantidades() == null ||
                dto.getProductos().length != dto.getCantidades().length) {
            throw new IllegalArgumentException("Los arrays de productos y cantidades deben tener la misma longitud y no ser nulos.");
        }

        return pedidosRepository.registrarPedido(dto);
    }

    public void cambiarEstadoPedido(int pedidoId, String nuevoEstado) {
        pedidosRepository.cambiarEstadoPedido(pedidoId, nuevoEstado);
    }

    public void delete(long pedidoId) {
        pedidosRepository.deleteById(pedidoId);
    }
}
