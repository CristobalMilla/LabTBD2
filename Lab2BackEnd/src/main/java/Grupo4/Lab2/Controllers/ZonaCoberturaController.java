package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.ZonaCoberturaEntity;
import Grupo4.Lab2.Services.ZonaCoberturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/zonasCobertura")
@CrossOrigin
public class ZonaCoberturaController {
    @Autowired
    private ZonaCoberturaService zonaCoberturaService;

    @GetMapping("/all")
    public ResponseEntity<List<ZonaCoberturaEntity>> getAll() {
        List<ZonaCoberturaEntity> zonas = zonaCoberturaService.getAll();
        return ResponseEntity.ok(zonas);
    }
    @GetMapping("{id}")
    public ResponseEntity<ZonaCoberturaEntity> getById(@PathVariable long zona_id){
        ZonaCoberturaEntity zona = zonaCoberturaService.getById(zona_id);
        return ResponseEntity.ok(zona);
    }
    @PostMapping("/save")
    public ResponseEntity<ZonaCoberturaEntity> save(@RequestBody ZonaCoberturaEntity zona){
        zonaCoberturaService.save(zona);
        return ResponseEntity.ok(zona);
    }
    @PutMapping("/update")
    public ResponseEntity<ZonaCoberturaEntity> update(@RequestBody ZonaCoberturaEntity zona){
        zonaCoberturaService.update(zona);
        return ResponseEntity.ok(zona);
    }
}
