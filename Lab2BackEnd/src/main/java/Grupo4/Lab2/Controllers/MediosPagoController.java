package Grupo4.Lab2.Controllers;


import Grupo4.Lab2.Entities.MediosPagoEntity;
import Grupo4.Lab2.Services.MediosPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/mediosPago")
@CrossOrigin
public class MediosPagoController {
    @Autowired
    private MediosPagoService mediosPagoService;

    @GetMapping("/all")
    public ResponseEntity<List<MediosPagoEntity>> getAllMediosPago() {
        List<MediosPagoEntity> mediosPago = mediosPagoService.findAll();
        return ResponseEntity.ok(mediosPago);
    }
    @GetMapping("/id/{medio_id}")
    public ResponseEntity<MediosPagoEntity> getMediosPagoById(@PathVariable long medio_id){
        MediosPagoEntity medioPago = mediosPagoService.findById(medio_id);
        return ResponseEntity.ok(medioPago);
    }
    @PostMapping("/")
    public ResponseEntity<MediosPagoEntity> addMediosPago(@RequestBody MediosPagoEntity mediosPago){
        mediosPagoService.save(mediosPago);
        return ResponseEntity.ok(mediosPago);
    }
    @PutMapping("/")
    public ResponseEntity<MediosPagoEntity> updateMediosPago(@RequestBody MediosPagoEntity mediosPago){
        mediosPagoService.update(mediosPago);
        return ResponseEntity.ok(mediosPago);
    }
    @DeleteMapping("/delete/{medio_id}")
    public ResponseEntity<MediosPagoEntity> deleteMediosPago(@PathVariable long medio_id) throws Exception{
        try {
            mediosPagoService.delete(medio_id);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
