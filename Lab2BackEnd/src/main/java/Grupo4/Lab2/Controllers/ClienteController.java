package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.ClienteEntity;
import Grupo4.Lab2.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteEntity> getById(@PathVariable long idCliente) {
        ClienteEntity cliente = clienteService.getById(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClienteEntity>> getAll() {
        List<ClienteEntity> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/create")
    public ResponseEntity<ClienteEntity> create(@RequestBody ClienteEntity cliente) {
        clienteService.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteEntity> update(@RequestBody ClienteEntity cliente) {
        clienteService.update(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<ClienteEntity> delete(@PathVariable long idCliente) {
        try {
            clienteService.delete(idCliente);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
