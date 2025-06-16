package Grupo4.Lab2.Controllers;


import Grupo4.Lab2.Entities.CalificacionEntity;
import Grupo4.Lab2.Services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/calificaciones")
@CrossOrigin("*")
public class CalificacionController {
    @Autowired
    private CalificacionService calificacionService;

    @GetMapping("/{idCalificacion}")
    public ResponseEntity<CalificacionEntity> getById(@PathVariable long idCalificacion) {
        CalificacionEntity calificacion = calificacionService.getById(idCalificacion);
        return ResponseEntity.ok(calificacion);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CalificacionEntity>> getAll() {
        List<CalificacionEntity> calificacions = calificacionService.getAll();
        return ResponseEntity.ok(calificacions);
    }

    @PostMapping("/create")
    public ResponseEntity<CalificacionEntity> create(@RequestBody CalificacionEntity calificacion) {
        calificacionService.save(calificacion);
        return ResponseEntity.ok(calificacion);
    }

    @PutMapping("/update")
    public ResponseEntity<CalificacionEntity> update(@RequestBody CalificacionEntity calificacion) {
        calificacionService.update(calificacion);
        return ResponseEntity.ok(calificacion);
    }

    @DeleteMapping("/{idCalificacion}")
    public ResponseEntity<CalificacionEntity> delete(@PathVariable long idCalificacion) {
        try {
            calificacionService.delete(idCalificacion);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
