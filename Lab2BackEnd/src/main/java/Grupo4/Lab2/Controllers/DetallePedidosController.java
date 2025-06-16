package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.DetallePedidosEntity;
import Grupo4.Lab2.Services.DetallePedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/detalle-pedidos")
@CrossOrigin("*")
public class DetallePedidosController {

    @Autowired
    private DetallePedidosService detallePedidosService;

    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetallePedidosEntity> getById(@PathVariable long idDetalle) {
        DetallePedidosEntity pedido = detallePedidosService.getById(idDetalle);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/")
    public ResponseEntity<List<DetallePedidosEntity>> getAll() {
        try {
            List<DetallePedidosEntity> detalle = detallePedidosService.getAll();
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody DetallePedidosEntity detalle) {
        try {
            detallePedidosService.save(detalle);
            return ResponseEntity.ok("Detalle guardado con ID: " + detalle.getDetalle_id());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar detalle");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody DetallePedidosEntity detalle) {
        try {
            detalle.setDetalle_id(id); // Aseguramos que use el ID de la URL
            detallePedidosService.update(detalle);
            return ResponseEntity.ok("Detalle actualizado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar detalle");
        }
    }

    @DeleteMapping("/delete/{pedido_id}")
    public ResponseEntity<String> delete(@PathVariable("pedido_id") long pedidoId) {
        try {
            detallePedidosService.delete(pedidoId);
            return ResponseEntity.ok("Pedido eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
