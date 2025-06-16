package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.CoordenadaDTO;
import Grupo4.Lab2.Entities.EmpresaEntity;
import Grupo4.Lab2.Services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/api/empresas")
@CrossOrigin("*")
public class EmpresaController {
    @Autowired
    EmpresaService empresaService;

    @GetMapping("/all")
    public ResponseEntity<List<EmpresaEntity>> getAll() {
        List<EmpresaEntity> empresas = empresaService.findAll();
        return ResponseEntity.ok(empresas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaEntity> getById(@PathVariable Integer id) {
        EmpresaEntity empresa = empresaService.findById(id);
        return ResponseEntity.ok(empresa);
    }
    @PostMapping("/")
    public ResponseEntity<EmpresaEntity> create(@RequestBody EmpresaEntity empresa) {
        empresaService.save(empresa);
        return ResponseEntity.ok(empresa);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaEntity> update(
        @PathVariable Long id,
        @RequestBody EmpresaEntity empresa
    ) {
        // Aseguramos que el ID del body coincide con el path
        empresa.setEmpresaId(id);
        empresaService.update(empresa);
        return ResponseEntity.ok(empresa);
    }
    @DeleteMapping("/")
    public ResponseEntity<EmpresaEntity> deleteById(@RequestBody long id) throws Exception {
        try {
            empresaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/entregascercanas/{id}")
    public ResponseEntity<List<Point>> getEntregasCercanas(@PathVariable Long id){
        List<Point> puntos = empresaService.getEntregasCercanas(id);
        if (puntos == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(puntos);
    }

    // 4.
    @GetMapping("/entrega-mas-lejana/{empresaId}")
    public ResponseEntity<CoordenadaDTO> getPuntoMasLejano(@PathVariable("empresaId") long empresaId) {

        CoordenadaDTO coordenada = empresaService.getEntregaMasLejana(empresaId);
        if (coordenada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coordenada);
    }
}
