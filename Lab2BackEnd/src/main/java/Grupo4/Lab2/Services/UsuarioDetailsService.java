package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.UsuarioEntity;
import Grupo4.Lab2.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    // Dependencia para interactuar con la base de datos y recuperar información del usuario.
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Constructor que recibe el repositorio como dependencia (inyección de dependencia).
     *
     * @param usuarioRepository El repositorio de usuarios.

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
    }*/

    /**
     * Carga los detalles del usuario por su nick.
     *
     * @param email El nick del usuario.
     * @return Los detalles del usuario.
     * @throws UsernameNotFoundException Si el nick no se encuentra en el sistema.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Se busca al usuario con el repositorio
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Email no encontrado en el sistema: " + email);
        }

        System.out.println("funcion: loadUserByUsername\nUsuario encontrado: " + usuario.getEmail());

        // Si existe, se crea un objeto UserDetails utilizando la clase User de Spring Security
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRole())
                .build();

    }
}
