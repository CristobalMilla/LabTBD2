package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import Grupo4.Lab2.MongoDB.Services.LogsPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/create/{id_pedido}")
    public ResponseEntity<LogsPedidos> createLog(@PathVariable long id_pedido){
        return ResponseEntity.ok(logsPedidosService.createLog(id_pedido));
    }
    @GetMapping("/update/{id}")
    public ResponseEntity<LogsPedidos> updateLog(@PathVariable long id, LogsPedidos log){
        return ResponseEntity.ok(logsPedidosService.updateLog(id, log));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable long id){
        logsPedidosService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
