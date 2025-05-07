package Grupo4.Lab2.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Grupo4.Lab2.Entities.UsuarioEntity;
import Grupo4.Lab2.Repositories.UsuarioRepository; 

import java.util.Date;

/**
 * UsuarioService es una clase de servicio que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepo;

    /**
     * Registra un nuevo usuario.
     *
     * @param rut El RUT del usuario.
     * @param name El nombre del usuario.
     * @param email El email del usuario.
     * @param phone El teléfono del usuario.
     * @param fechaDeNacimiento La fecha de nacimiento del usuario.
     * @param password La contraseña del usuario.
     * @param rol El rol del usuario.
     * @return El usuario registrado.
     * @throws RuntimeException Si el correo ya está registrado.
     */
    public UsuarioEntity register(String rut, String name, String email, String phone, Date fechaDeNacimiento, String password, String rol) {
        UsuarioEntity existenteEmail = userRepo.findByEmail(email);
        if (existenteEmail != null) { // si el usuarioEmail es distinto de vacío
            throw new RuntimeException("El correo " + email + " ya está registrado. Usa uno diferente."); // es decir, existe retornamos null
        }

        UsuarioEntity user = new UsuarioEntity(rut, name, email, phone, fechaDeNacimiento, password, rol);
        userRepo.save(user);
        return user;
    }

    /**
     * Realiza el login del usuario.
     *
     * @param email El email del usuario.
     * @param password La contraseña del usuario.
     * @return Un código indicando el resultado de la operación de login.
     */
    public int login(String email, String password) {
        UsuarioEntity user = userRepo.findByEmail(email);
        if (user != null) { // si el usuarioEmail o user es distinto de vacío, existe, le pedimos la contraseña
            if (password.equals(user.getPassword())) { // Se le aplican diferentes roles, por lo que se retorna un valor distinto para cada uno
                // Cambiar el valor de retorno si es necesario
                if (user.getRole().equals("ADMIN")) {
                    return 1;
                } else if (user.getRole().equals("TRABAJADOR")) {
                    return 2;
                } else if (user.getRole().equals("CLIENTE")) { // El retorno de usuario sigue siendo el mismo, por lo que no debería generar problemas
                    return 3;
                }
            }
        }
        return 0;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario correspondiente al ID proporcionado.
     * @throws RuntimeException Si no se encuentra el usuario.
     */
    public UsuarioEntity getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Obtiene el ID de un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El ID del usuario correspondiente al email proporcionado.
     */
    public Long getIdByEmail(String email) {
        return userRepo.findIdByEmail(email);
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El usuario correspondiente al email proporcionado.
     */
    public UsuarioEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
