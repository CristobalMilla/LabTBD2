package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.CallesVerticesEntity;
import Grupo4.Lab2.Services.CallesVerticesService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/calles-vertices") 
public class CallesVerticesController { 

    @Autowired
    private CallesVerticesService callesVerticesService;

    @GetMapping("/{id}")
    public ResponseEntity<CallesVerticesEntity> getById(@PathVariable long id) { 
        CallesVerticesEntity vertice = callesVerticesService.getById(id); 
        if (vertice != null) {
            return ResponseEntity.ok(vertice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CallesVerticesEntity>> getAll() { 
        List<CallesVerticesEntity> vertices = callesVerticesService.getAll();
        if (vertices != null && !vertices.isEmpty()) {
            return ResponseEntity.ok(vertices);
        } else if (vertices != null) { 
            return ResponseEntity.ok(vertices);
        }
        else { 
            return ResponseEntity.internalServerError().build();
        }
    }
}