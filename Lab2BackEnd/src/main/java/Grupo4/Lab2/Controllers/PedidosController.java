package Grupo4.Lab2.Controllers;


import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/pedidos")
@CrossOrigin
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;


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
}
