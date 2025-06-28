package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.Entities.EventoNavegacion;
import Grupo4.Lab2.MongoDB.Entities.NavegacionUsuariosEntity;
import Grupo4.Lab2.MongoDB.Services.NavegacionUsuariosService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/navegacion")
public class NavegacionUsuariosController {

    @Autowired
    private NavegacionUsuariosService service;

    // 🔹 Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<NavegacionUsuariosEntity>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodasLasNavegaciones());
    }

    // 🔹 Obtener navegación por ID (ObjectId en formato string)
    @GetMapping("/{id}")
    public ResponseEntity<NavegacionUsuariosEntity> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.obtenerPorId(new ObjectId(id)));
    }

    // 🔹 Crear un nuevo documento completo
    @PostMapping
    public ResponseEntity<NavegacionUsuariosEntity> crear(@RequestBody NavegacionUsuariosEntity entidad) {
        return ResponseEntity.ok(service.guardar(entidad));
    }

    @PutMapping
    public ResponseEntity<NavegacionUsuariosEntity> actualizar(@RequestBody NavegacionUsuariosEntity entidad) {
        return ResponseEntity.ok(service.actualizar(entidad));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(@RequestBody NavegacionUsuariosEntity entidad) {
        service.eliminar(entidad);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/evento")
    public ResponseEntity<Void> agregarEvento(@PathVariable String clienteId, @RequestBody EventoNavegacion evento) {
        service.agregarEvento(clienteId, evento);
        return ResponseEntity.ok().build();
    }
}
