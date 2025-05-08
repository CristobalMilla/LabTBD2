package Grupo4.Lab2.Controllers;


import Grupo4.Lab2.Entities.PagoEntity;
import Grupo4.Lab2.Services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/pagos")
@CrossOrigin
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping("/all")
    public ResponseEntity<List<PagoEntity>> findAll() {
        List<PagoEntity> pagos = pagoService.findAll();
        return ResponseEntity.ok(pagos);
    }
    @GetMapping("/idPago/{id}")
    public ResponseEntity<PagoEntity> findById(@PathVariable long id) {
        PagoEntity pago = pagoService.findById(id);
        return ResponseEntity.ok(pago);
    }
    @GetMapping("/idPedido/{id}")
    public ResponseEntity<List<PagoEntity>> findByPedidoId(@PathVariable long id) {
        List<PagoEntity> pagos = pagoService.findByPedidoId(id);
        return ResponseEntity.ok(pagos);
    }
    @GetMapping("/idMedio/{id}")
    public ResponseEntity<List<PagoEntity>> findByMedioId(@PathVariable long id) {
        List<PagoEntity> pagos = pagoService.findByMedioId(id);
        return ResponseEntity.ok(pagos);
    }
    @PostMapping("/")
    public ResponseEntity<PagoEntity> save(@RequestBody PagoEntity pago) {
        pagoService.save(pago);
        return ResponseEntity.ok(pago);
    }
    @PutMapping("/")
    public ResponseEntity<PagoEntity> update(@RequestBody PagoEntity pago) {
        pagoService.update(pago);
        return ResponseEntity.ok(pago);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PagoEntity> delete(@PathVariable long id) throws Exception {
        try {
            pagoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
