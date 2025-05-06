package Grupo4.Lab2.Controllers;

import com.Alvolante.Backend.Entity.UsuarioEntity;
import com.Alvolante.Backend.Service.UsuarioService;
import com.Alvolante.Backend.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * UsuarioController es un controlador que maneja las solicitudes HTTP relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService userservice;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param nuevo Los detalles del nuevo usuario.
     * @return La respuesta HTTP con el usuario registrado o un mensaje de error.
     */
    @PreAuthorize("hasRole('TRABAJADOR') or hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioEntity nuevo) {
        try {
            UsuarioEntity user = userservice.register(nuevo.getRut(), nuevo.getNameParam(), nuevo.getEmail(), nuevo.getPhone(), nuevo.getBirthdate(), nuevo.getPassword(), nuevo.getRole());
            return ResponseEntity.ok(user); // Retorna al usuario si todo salió bien
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // Imprime el error
        }
    }

    /**
     * Endpoint para realizar el login del usuario.
     *
     * @param loginDto Los detalles de inicio de sesión del usuario.
     * @return Un código indicando el resultado de la operación de login.
     */
    @PostMapping("/login")
    public int login(@RequestBody LoginDto loginDto) {
        return usuarioService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    /**
     * Endpoint para obtener un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario correspondiente al ID proporcionado.
     */
    @GetMapping("/id")
    public UsuarioEntity getUsuarioById(@RequestParam("id") String id) {
        System.out.printf("Id a buscar: " + id);
        return userservice.getUserById(Integer.parseInt(id));
    }

    /**
     * Endpoint para obtener el ID de un usuario por su email.
     *
     * @param email El email del usuario.
     * @return La respuesta HTTP con el ID del usuario o un mensaje de error.
     */
    @GetMapping("/idByEmail")
    public ResponseEntity<Long> getIdByEmail(@RequestParam String email) {
        try {
            Long id = userservice.getIdByEmail(email);
            return ResponseEntity.ok(id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint para obtener un usuario por su email.
     *
     * @param email El email del usuario.
     * @return La respuesta HTTP con el usuario encontrado o un mensaje de error.
     */
    @GetMapping("/userByEmail")
    public ResponseEntity<UsuarioEntity> getUserByEmail(@RequestParam String email) {
        try {
            UsuarioEntity user = userservice.getUserByEmail(email);
            System.out.println("\nSe encontró Usuario: " + user + "\n");
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            System.out.println("\nHay un error\n");
            throw new RuntimeException("\nError al obtener el usuario\n");
        }
    }
}
