package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.RegisterDTO;
import Grupo4.Lab2.Repositories.UsuarioRepository;
import Grupo4.Lab2.Services.UsuarioService;
import Grupo4.Lab2.Entities.UsuarioEntity;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import Grupo4.Lab2.DTO.LoginDTO;
import Grupo4.Lab2.Config.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * UsuarioController es un controlador que maneja las solicitudes HTTP relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/auth/usuarios") //Cambiar segun uso de autorizacion
@CrossOrigin("*")
public class UsuarioController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param nuevo Los detalles del nuevo usuario.
     * @return La respuesta HTTP con el usuario registrado o un mensaje de error.
     */
    //@PreAuthorize("hasRole('TRABAJADOR') or hasRole('ADMIN')")
    /**
     * Registra un nuevo usuario.
     *
     * @param registerDto Los datos de registro del nuevo usuario.
     * @return La respuesta HTTP con el mensaje de éxito o error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        // Se verifica si el usuario ya existe con ese email
        UsuarioEntity foundUser = usuarioService.getUserByEmail(registerDto.getEmail());

        if (foundUser != null) {
            // 409 La solicitud no se puede completar por un conflicto (ya existe)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya se encuentra registrado.\n");
        }


        // Se puede implementar la validación de datos aquí y dar error 400 en caso de no cumplirse
        try {
            // Asignar rol por defecto si no se proporciona
            String rol = (registerDto.getRole() == null || registerDto.getRole().isEmpty()) ? "CLIENTE" : registerDto.getRole();

            // Cifrar la contraseña
            String hashedPassword = passwordEncoder.encode(registerDto.getPassword());

            // Se crea un nuevo usuario
            UsuarioEntity newUser = new UsuarioEntity(
                    registerDto.getRut(),
                    registerDto.getNameParam(),
                    registerDto.getEmail(),
                    registerDto.getPhone(),
                    registerDto.getBirthdate(),
                    hashedPassword,
                    rol
            );

            usuarioRepository.save(newUser);

            // Generamos el token de inmediato para devolverlo al front
            String jwt = jwtUtil.createToken(newUser.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("id_usuario", newUser.getIdUsuario());
            response.put("email", newUser.getEmail());
            response.put("role", newUser.getRole());

            System.out.println("REGISTER----");
            System.out.println("id_usuario: " + newUser.getIdUsuario());
            System.out.println("email: "+ newUser.getEmail());
            System.out.println("role: " + newUser.getRole());

            // 201 Solicitud exitosa
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            // 500 Error en la lógica del backend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor.\n");
        }
    }

    /**
     * Realiza el login del usuario.
     *
     * @param loginDto Los datos de inicio de sesión del usuario.
     * @return La respuesta HTTP con el token y el ID del usuario o un mensaje de error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        // Se imprimen los datos recibidos en el backend
        System.out.println("Datos recibidos en el backend: " + loginDto);

        try {
            // Verificar si el usuario existe en la base de datos antes de autenticar
            UsuarioEntity user = usuarioRepository.findByEmail(loginDto.getEmail());
            if (user == null) {
                // Error 400
                System.out.println("No se encontró el usuario con el email proporcionado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El email no está registrado. Por favor, regístrese primero.");
            }

            // Se autentica al usuario con los datos proporcionados
            UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            );

            System.out.println("Datos del login: \n\temail: " + loginToken.getPrincipal().toString());
            try {
                authenticationManager.authenticate(loginToken);
                System.out.println("Autenticación exitosa.");
            } catch (Exception e) {
                System.out.println("Error en el AuthenticationManager: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
            }

            // Si la autenticación es exitosa, se genera JWT
            System.out.println("\nSe llama a jwtUtil para crear el token.");
            String jwt = jwtUtil.createToken(loginDto.getEmail());

            // Se construye la respuesta con el token y el ID del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("id_usuario", user.getIdUsuario());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());

            System.out.println("LOGIN----");
            System.out.println("id_usuario: " + user.getIdUsuario());
            System.out.println("email: "+ user.getEmail());
            System.out.println("role: " + user.getRole());

            return ResponseEntity.ok(response); // Devolver el token +id+email+rol

        } catch (BadCredentialsException e) {
            // Maneja credenciales incorrectas
            // 401 Unauthorized
            System.out.println("Credenciales incorrectas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas. Verifique su correo y contraseña.");

        } catch (Exception e) {
            // Cualquier otro error
            System.out.println("Error en el proceso de autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor.");
        }
    }

    /**
     * Endpoint para obtener un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario correspondiente al ID proporcionado.
     */
    @GetMapping("/id")
    public UsuarioEntity getUsuarioById(@RequestParam("id") Long id) {
        System.out.println("Id a buscar: " + id);
        return userService.getUserById(id);
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
            Long id = userService.getIdByEmail(email);
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
            UsuarioEntity user = userService.getUserByEmail(email);
            System.out.println("\nSe encontró Usuario: " + user + "\n");
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            System.out.println("\nHay un error\n");
            throw new RuntimeException("\nError al obtener el usuario\n");
        }
    }

    /**
     * Verifica la validez de un token JWT.
     *
     * @param token El token JWT a verificar.
     * @return La respuesta HTTP con el mensaje de validez del token.
     */
    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestBody String token) {
        try {
            if (token == null || !token.startsWith("Bearer")) {
                // 403 El cliente no tiene permisos para acceder al recurso solicitado
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El token no es válido.");
            }
            // Se elimina el prefijo "Bearer"
            token = token.replace("Bearer ", "");
            boolean isValid = jwtUtil.isValid(token);

            if (!isValid) {
                // 403 El cliente no tiene permisos para acceder al recurso solicitado
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El token no es válido.");
            }
            return ResponseEntity.ok(Map.of("message", "Token válido."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor.\n");
        }
    }
}
