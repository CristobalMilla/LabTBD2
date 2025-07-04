package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.DTO.Query2DTO;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Services.OpinionesClientesService;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opiniones")
public class OpinionesClientesController {

    private final OpinionesClientesService opinionesClientesService;

    @Autowired
    public OpinionesClientesController(OpinionesClientesService opinionesClientesService) {
        this.opinionesClientesService = opinionesClientesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OpinionesClientes>> getAllOpiniones() {
        List<OpinionesClientes> opiniones = opinionesClientesService.getAllOpiniones();
        return ResponseEntity.ok(opiniones);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<OpinionesClientes> getOpinionById(@PathVariable long id) {
        OpinionesClientes opinion = opinionesClientesService.getOpinionById(id);
        if (opinion != null) {
            return ResponseEntity.ok(opinion); // Returns 200 OK with the opinion
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found
        }
    }

    @PostMapping("/save")
    public ResponseEntity<OpinionesClientes> createOpinion(@RequestBody OpinionesClientes opinion) {
        OpinionesClientes createdOpinion = opinionesClientesService.createOpinion(opinion);
        return new ResponseEntity<>(createdOpinion, HttpStatus.CREATED); // Returns 201 Created
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OpinionesClientes> updateOpinion(@PathVariable long id, @RequestBody OpinionesClientes opinion) {
        OpinionesClientes updatedOpinion = opinionesClientesService.updateOpinion(id, opinion);
        if (updatedOpinion != null) {
            return ResponseEntity.ok(updatedOpinion); // Returns 200 OK with updated opinion
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable long id) {
        opinionesClientesService.deleteOpinion(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOpiniones() {
        long count = opinionesClientesService.countOpiniones();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/promedioXempresa")
    public ResponseEntity<List<PromedioPuntuacionXEmpresaDTO>> getPromedioXempresa() {
        List<PromedioPuntuacionXEmpresaDTO> promedios = opinionesClientesService.getPuntuacionPromedioXEmpresa();
        return ResponseEntity.ok(promedios);
    }

    /**
     * GET /opiniones/stats/por-hora
     * Retrieves client opinion statistics grouped by hour.
     *
     * @return A list of OpinionStatsPorHoraDTO containing the statistics.
     */
    @GetMapping("/stats/por-hora")
    public ResponseEntity<List<OpinionStatsPorHoraDTO>> getStatsPorHora() {
        List<OpinionStatsPorHoraDTO> stats = opinionesClientesService.getStatsPorHora();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/query2")
    public ResponseEntity<List<Query2DTO>> getQuery2() {
        List<Query2DTO> paquete = opinionesClientesService.getOpinionesQuery2();
        if (paquete != null) {
            return ResponseEntity.ok(paquete);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
