package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.DTO.CambiarEstadoLogPedidoDTO;
import Grupo4.Lab2.MongoDB.DTO.CrearLogPedidoDTO;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import Grupo4.Lab2.MongoDB.Services.LogsPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs_pedidos")
public class LogsPedidosController {

    private final LogsPedidosService logsPedidosService;

    @Autowired
    public LogsPedidosController(LogsPedidosService logsPedidosService){
        this.logsPedidosService = logsPedidosService;
    }

    @GetMapping
    public ResponseEntity<List<LogsPedidos>> getAllLogsPedidos(){
        return ResponseEntity.ok(logsPedidosService.getAllLogsPedidos());
    }

    @GetMapping("/{id_pedido}")
    public ResponseEntity<LogsPedidos> getLogById(@PathVariable long id_pedido){
        return ResponseEntity.ok(logsPedidosService.getLogById(id_pedido));
    }

    @PostMapping("/create")
    public ResponseEntity<LogsPedidos> createLog(@RequestBody CrearLogPedidoDTO logPedidoDTO){
        return ResponseEntity.ok(logsPedidosService.createLog(logPedidoDTO.getPedido_id(), logPedidoDTO.getTimestamp()));
    }
    @GetMapping("/update/{id}")
    public ResponseEntity<LogsPedidos> updateLog(@PathVariable long id, @RequestBody LogsPedidos log){
        return ResponseEntity.ok(logsPedidosService.updateLog(id, log));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable long id){
        logsPedidosService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cambiarestado")
    public ResponseEntity<LogsPedidos> cambiarEstado(@RequestBody CambiarEstadoLogPedidoDTO logPedidoDTO){
        LogsPedidos logs = logsPedidosService.cambiarEstado(logPedidoDTO.getPedido_id(), logPedidoDTO.getEstado(), logPedidoDTO.getTimestamp());
        if(logs != null){
            return ResponseEntity.ok(logs);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    @GetMapping("/getNumeroPedidos3Cambios")
    public ResponseEntity<Integer> countHistoriales10Minutos(){
        int count = logsPedidosService.countHistoriales10Minutos();
        if(count > 0){
            return ResponseEntity.ok(count);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}