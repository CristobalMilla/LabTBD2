package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.PedidoYZonasQueCruzaDTO;
import Grupo4.Lab2.Entities.PedidosEntity;
import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //@PreAuthorize("hasRole('TRABAJADOR') or hasRole('ADMIN')")
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
            long pedido_id = pedidosService.registrarPedido(dto);
            return ResponseEntity.ok("Pedido registrado con ID: " + pedido_id);
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

    @PostMapping("/confirmar-descontar/{pedidoId}")
    public ResponseEntity<String> confirmarPedidoDescontar(@PathVariable("pedidoId") int pedidoId) {
        try {
            pedidosService.confirmarPedidoDescontar(pedidoId);
            return ResponseEntity.ok("Pedido confirmado y stock descontado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Query 5
    // Listar todos los pedidos cuya ruta estimada cruce más de 2 zonas de reparto.
    @GetMapping("/pedidos-que-cruzan-mas-de-2-zonas")
    public ResponseEntity<List<PedidoYZonasQueCruzaDTO>> getPedidosQueCruzanMasDe2Zonas() {
        try {
            List<PedidoYZonasQueCruzaDTO> pedidos = pedidosService.pedidosQueCruzanMasDe2Zonas();
            return ResponseEntity.ok(pedidos);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/createFull")
    public ResponseEntity<PedidosEntity> createFull(@RequestBody RegistrarPedidoDTO dto) {
        try {
            PedidosEntity pedido = pedidosService.crearPedidoCompleto(dto);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
