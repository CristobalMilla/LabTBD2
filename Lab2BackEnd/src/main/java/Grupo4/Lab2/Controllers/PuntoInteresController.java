package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.Entities.PuntoInteresEntity;
import Grupo4.Lab2.Services.PuntoInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos-interes")
public class PuntoInteresController {

    @Autowired
    private PuntoInteresService service;

    @GetMapping("/")
    public ResponseEntity<List<PuntoInteresEntity>> findAll() {
        List<PuntoInteresEntity> puntos = service.findAll();
        if (puntos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(puntos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PuntoInteresEntity> findById(@PathVariable Long id) {
        PuntoInteresEntity punto = service.findById(id);
        if (punto == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(punto);
        }
    }

    @PostMapping("/")
    public ResponseEntity<PuntoInteresEntity> postPuntoInteresEntity(@RequestBody PuntoInteresEntity puntoInteresEntity) {
        service.save(puntoInteresEntity);
        if (service.findById(puntoInteresEntity.getInteres_id())  == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(puntoInteresEntity);
        }
    }
    @PutMapping("/")
    public ResponseEntity<PuntoInteresEntity> putPuntoInteresEntity(@RequestBody PuntoInteresEntity puntoInteresEntity) {
        service.update(puntoInteresEntity);
        if (service.findById(puntoInteresEntity.getInteres_id())  == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(puntoInteresEntity);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PuntoInteresEntity> deletePuntoInteresEntity(@PathVariable Long id) {
        service.deleteById(id);
        if (service.findById(id)  == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(service.findById(id));
        }
    }
    @GetMapping("/puntos/{id}")
    public ResponseEntity<List<PuntoInteresEntity>> getPuntosCliente(@PathVariable Long id){
        List<PuntoInteresEntity> puntos = service.findNearby(id);
        if (puntos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(puntos);
    }
}
