package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import Grupo4.Lab2.MongoDB.Services.HistorialRepartidoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial_repartidores")
public class HistorialRepartidoresController {
    private final HistorialRepartidoresService historialRepartidoresService;

    @Autowired
    public HistorialRepartidoresController(HistorialRepartidoresService historialRepartidoresService) {
        this.historialRepartidoresService = historialRepartidoresService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HistorialRepartidores>> getAllHistoriales() {
        List<HistorialRepartidores> historiales = historialRepartidoresService.getAllHistoriales();
        return ResponseEntity.ok(historiales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialRepartidores> getHistorialById(@PathVariable long id) {
        HistorialRepartidores historial = historialRepartidoresService.getHistorialById(id);
        if (historial != null) {
            return ResponseEntity.ok(historial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/repartidor/{id}")
    public ResponseEntity<HistorialRepartidores> getHistorialByRepartidorId(@PathVariable long id) {
        HistorialRepartidores historial = historialRepartidoresService.getHistorialByRepartidorId(id);
        if (historial != null) {
            return ResponseEntity.ok(historial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HistorialRepartidores> createOpinion(@RequestBody HistorialRepartidores historial) {
        HistorialRepartidores createdHistorial = historialRepartidoresService.createHistorial(historial);
        return new ResponseEntity<>(createdHistorial, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialRepartidores> updateHistorial(@PathVariable long id, @RequestBody HistorialRepartidores historial) {
        HistorialRepartidores updatedHistorial = historialRepartidoresService.updateHistorial(id, historial);
        if (updatedHistorial != null) {
            return ResponseEntity.ok(updatedHistorial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable long id) {
        historialRepartidoresService.deleteHistorial(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countHistoriales() {
        long count = historialRepartidoresService.countHistoriales();
        return ResponseEntity.ok(count);
    }
}
