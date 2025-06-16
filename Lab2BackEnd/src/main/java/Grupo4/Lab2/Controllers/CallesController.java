package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.CallesEntity;
import Grupo4.Lab2.Services.CallesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/calles")
@CrossOrigin("*")
public class CallesController {
    @Autowired
    private CallesService callesService;

    @GetMapping("/{id}")
    public ResponseEntity<CallesEntity> getById(@PathVariable long id) {
        CallesEntity calle = callesService.getById(id);
        if (calle != null)
            return ResponseEntity.ok(calle);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CallesEntity>> getAll() {
        List<CallesEntity> calles = callesService.getAll();
        return ResponseEntity.ok(calles);
    }

    @PostMapping("/create")
    public ResponseEntity<CallesEntity> create(@RequestBody CallesEntity calle) {
        callesService.save(calle);
        return ResponseEntity.status(HttpStatus.CREATED).body(calle);
    }

    @PutMapping("/update")
    public ResponseEntity<CallesEntity> update(@RequestBody CallesEntity calle) {
        callesService.update(calle);
        return ResponseEntity.ok(calle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        callesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
