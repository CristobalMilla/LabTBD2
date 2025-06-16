package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.PedidoYZonasQueCruzaDTO;
import Grupo4.Lab2.Entities.ClienteEntity;
import Grupo4.Lab2.Entities.ZonaCoberturaEntity;
import Grupo4.Lab2.Services.ZonaCoberturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zonasCobertura")
@CrossOrigin("*")
public class ZonaCoberturaController {
    @Autowired
    private ZonaCoberturaService zonaCoberturaService;

    @GetMapping("/all")
    public ResponseEntity<List<ZonaCoberturaEntity>> getAll() {
        List<ZonaCoberturaEntity> zonas = zonaCoberturaService.getAll();
        return ResponseEntity.ok(zonas);
    }
    @GetMapping("/{zona_id}")
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

    @GetMapping("/zonasConAltaDensidad")
    public ResponseEntity<List<ZonaCoberturaEntity>> getZonasConAltaDensidad(){
        List<ZonaCoberturaEntity> zonasConAltaDensidad = zonaCoberturaService.getZonasConAltaDensidad();
        return ResponseEntity.ok(zonasConAltaDensidad);
    }
    //Consulta especial 2
    //Determinar si un cliente se encuentra dentro de una zona de cobertura
    //Se devolvera la lista zonas de cobertura en las que el cliente se encuentra
    @GetMapping("/getByClienteId/{cliente_id}")
    public ResponseEntity<List<ZonaCoberturaEntity>> getZonasCoberturaByClienteId(@PathVariable long cliente_id){
        List<ZonaCoberturaEntity> zonas = zonaCoberturaService.getZonasCoberturaByClienteId(cliente_id);
        return ResponseEntity.ok(zonas);
    }
    //Lo mismo pero con un booleano
    @GetMapping("/isClientInZonaCobertura/{cliente_id}")
    public ResponseEntity<Boolean> isClientInZonaCobertura(@PathVariable long cliente_id){
        List<ZonaCoberturaEntity> zonas = zonaCoberturaService.getZonasCoberturaByClienteId(cliente_id);
        if(zonas.isEmpty()){
            return ResponseEntity.ok(false);
        }
        else {
            return ResponseEntity.ok(true);
        }
    }

    @PutMapping("/byListIds")
    public List<ZonaCoberturaEntity> getZonasCoberturaByListIds(@RequestBody PedidoYZonasQueCruzaDTO pedido){
        System.out.println("aaaaa");
        return zonaCoberturaService.getZonasCoberturaByPedido(pedido);
    }
}
