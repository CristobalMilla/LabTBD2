package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.Services.LogsPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs_pedidos")
public class LogsPedidosController {

    private final LogsPedidosService logsPedidosService;

    @Autowired
    public LogsPedidosController(LogsPedidosService logsPedidosService) {
        this.logsPedidosService = logsPedidosService;
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
