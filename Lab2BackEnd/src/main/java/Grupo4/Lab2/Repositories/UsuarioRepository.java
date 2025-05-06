package Grupo4.Lab2.Repositories;

import com.Alvolante.Backend.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * UsuarioRepository es una interfaz que extiende JpaRepository para manejar operaciones CRUD en la entidad UsuarioEntity.
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    /**
     * Encuentra un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El usuario correspondiente al email proporcionado.
     */
    UsuarioEntity findByEmail(String email);

    /**
     * Encuentra el ID de un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El ID del usuario correspondiente al email proporcionado.
     */
    @Query("SELECT u.idUsuario FROM UsuarioEntity u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);
}
