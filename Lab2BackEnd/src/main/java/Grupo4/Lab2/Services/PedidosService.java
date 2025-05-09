package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Repositories.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

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

}
