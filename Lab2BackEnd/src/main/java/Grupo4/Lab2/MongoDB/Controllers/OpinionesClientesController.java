package Grupo4.Lab2.MongoDB.Controllers;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Services.OpinionesClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This REST controller provides API endpoints for managing OpinionesClientes.
@RestController
@RequestMapping("/opiniones") // Base path for all endpoints in this controller
public class OpinionesClientesController {

    // Autowire the service to handle business logic.
    private final OpinionesClientesService opinionesClientesService;

    @Autowired
    public OpinionesClientesController(OpinionesClientesService opinionesClientesService) {
        this.opinionesClientesService = opinionesClientesService;
    }

    /**
     * GET /opiniones
     * Retrieves all client opinions.
     *
     * @return A list of OpinionesClientes.
     */
    @GetMapping
    public ResponseEntity<List<OpinionesClientes>> getAllOpiniones() {
        List<OpinionesClientes> opiniones = opinionesClientesService.getAllOpiniones();
        return ResponseEntity.ok(opiniones);
    }

    /**
     * GET /opiniones/{id}
     * Retrieves a single client opinion by its ID.
     *
     * @param id The ID of the opinion to retrieve.
     * @return The OpinionesClientes object or a 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpinionesClientes> getOpinionById(@PathVariable long id) {
        OpinionesClientes opinion = opinionesClientesService.getOpinionById(id);
        if (opinion != null) {
            return ResponseEntity.ok(opinion); // Returns 200 OK with the opinion
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found
        }
    }

    /**
     * POST /opiniones
     * Creates a new client opinion.
     *
     * @param opinion The OpinionesClientes object to create (from request body).
     * @return The created OpinionesClientes object with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<OpinionesClientes> createOpinion(@RequestBody OpinionesClientes opinion) {
        OpinionesClientes createdOpinion = opinionesClientesService.createOpinion(opinion);
        return new ResponseEntity<>(createdOpinion, HttpStatus.CREATED); // Returns 201 Created
    }

    /**
     * PUT /opiniones/{id}
     * Updates an existing client opinion.
     *
     * @param id The ID of the opinion to update.
     * @param opinion The updated OpinionesClientes object (from request body).
     * @return The updated OpinionesClientes object or a 404 Not Found if original not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpinionesClientes> updateOpinion(@PathVariable long id, @RequestBody OpinionesClientes opinion) {
        OpinionesClientes updatedOpinion = opinionesClientesService.updateOpinion(id, opinion);
        if (updatedOpinion != null) {
            return ResponseEntity.ok(updatedOpinion); // Returns 200 OK with updated opinion
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found
        }
    }

    /**
     * DELETE /opiniones/{id}
     * Deletes a client opinion by its ID.
     *
     * @param id The ID of the opinion to delete.
     * @return A 204 No Content status if successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable long id) {
        opinionesClientesService.deleteOpinion(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    /**
     * GET /opiniones/count
     * Retrieves the total number of client opinions.
     *
     * @return The count of opinions.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOpiniones() {
        long count = opinionesClientesService.countOpiniones();
        return ResponseEntity.ok(count);
    }
}
