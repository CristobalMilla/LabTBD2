package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.PedidosEntity;
import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/pedidos")
@CrossOrigin
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidosEntity> getById(@PathVariable long idPedido) {
        PedidosEntity pedido = pedidosService.getById(idPedido);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/")
    public ResponseEntity<List<PedidosEntity>> getAll() {
        try {
            List<PedidosEntity> pedidos = pedidosService.getAll();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> registrarPedido(@RequestBody RegistrarPedidoDTO dto) {
        try {
            boolean registrado = pedidosService.registrarPedido(dto);
            return registrado
                    ? ResponseEntity.ok("Pedido registrado correctamente.")
                    : ResponseEntity.status(500).body("Error al registrar el pedido.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/cambiar-estado/{pedido_id}/{nuevo_estado}")
    public ResponseEntity<String> cambiarEstadoPedido(@PathVariable("pedido_id") int pedidoId, @PathVariable("nuevo_estado") String nuevoEstado) {
        try {
            pedidosService.cambiarEstadoPedido(pedidoId, nuevoEstado);
            return ResponseEntity.ok("Estado del pedido actualizado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{pedido_id}")
    public ResponseEntity<String> delete(@PathVariable("pedido_id") long pedidoId) {
        try {
            pedidosService.delete(pedidoId);
            return ResponseEntity.ok("Pedido eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
