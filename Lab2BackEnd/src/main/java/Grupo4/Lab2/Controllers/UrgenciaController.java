package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.UrgenciaEntity;
import Grupo4.Lab2.Services.UrgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urgencias")
@CrossOrigin("*")
public class UrgenciaController {

    @Autowired
    private UrgenciaService urgenciaService;

    /**
     * Endpoint para guardar una nueva urgencia.
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UrgenciaEntity urgencia) {
        try {
            UrgenciaEntity savedUrgencia = urgenciaService.save(urgencia);
            return ResponseEntity.ok(savedUrgencia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Endpoint para obtener una urgencia por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UrgenciaEntity> getUrgenciaById(@PathVariable("id") long id) {
        try {
            UrgenciaEntity urgencia = urgenciaService.findById(id);
            return ResponseEntity.ok(urgencia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint para obtener todas las urgencias.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UrgenciaEntity>> getAllUrgencias() {
        List<UrgenciaEntity> urgencias = urgenciaService.findAll();
        return ResponseEntity.ok(urgencias);
    }

    /**
     * Endpoint para eliminar una urgencia por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUrgenciaById(@PathVariable("id") long id) {
        try {
            urgenciaService.deleteById(id);
            return ResponseEntity.ok("Urgencia eliminada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}