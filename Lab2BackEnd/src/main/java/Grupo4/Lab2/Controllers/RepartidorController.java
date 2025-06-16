package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.RepartidorEntregaDTO;
import Grupo4.Lab2.DTO.RepartidorVistaDTO;
import Grupo4.Lab2.Entities.RepartidorEntity;
import Grupo4.Lab2.Services.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
@CrossOrigin("*")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @GetMapping("/all")
    public List<RepartidorEntity> getRepartidores(){
        return repartidorService.findall();
    }

    @GetMapping("/{id}")
    public RepartidorEntity getRepartidorById(@PathVariable Long id){
        return repartidorService.findById(id);
    }

    @PostMapping("/")
    public RepartidorEntity saveRepartidor(@RequestBody RepartidorEntity repartidor){
        repartidorService.save(repartidor);
        return repartidor;
    }

    @DeleteMapping("/{id}")
    public void deleteRepartidorById(@PathVariable Long id){
        repartidorService.deleteById(id);
    }
    @PutMapping("/")
    public void updateRepartidor(@RequestBody RepartidorEntity repartidor){
        repartidorService.update(repartidor);
    }

    @GetMapping("/mejores")
    public List<RepartidorEntity> mejoresRepartidores(){
        return repartidorService.mejoresRepartidores();
    }

    @GetMapping("/tiempos")
    public List<RepartidorEntregaDTO> tiemposRepartidoresEntregaMinutos(){
        return repartidorService.tiemposRepartidoresEntregaMinutos();
    }

    @GetMapping("/vista")
    public List<RepartidorVistaDTO> repartidoresVista(){
        return repartidorService.repartidoresVista();
    }
    
    @GetMapping("/{id}/distancia-mensual")
    public ResponseEntity<Double> distanciaMensual(@PathVariable long id) {
        double metros = repartidorService.getDistanciaMensual(id);
        return ResponseEntity.ok(metros);
    }
}
