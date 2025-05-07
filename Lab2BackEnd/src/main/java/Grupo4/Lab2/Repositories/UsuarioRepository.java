package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.UsuarioEntity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para Usuario implementado con SQL2o.
 */
@Repository
public class UsuarioRepository {

    private final Sql2o sql2o;

    @Autowired
    public UsuarioRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Busca un usuario por email.
     */
    public UsuarioEntity findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = :email";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                      .addParameter("email", email)
                      .executeAndFetchFirst(UsuarioEntity.class);
        }
    }

    /**
     * Busca un usuario por su ID.
     */
    public Optional<UsuarioEntity> findById(long id) {
        String sql = "SELECT * FROM usuario WHERE idUsuario = :id";
        try (Connection con = sql2o.open()) {
            UsuarioEntity user = con.createQuery(sql)
                                    .addParameter("id", id)
                                    .executeAndFetchFirst(UsuarioEntity.class);
            return Optional.ofNullable(user);
        }
    }

    /**
     * Obtiene el id de un usuario mediante su email.
     */
    public Long findIdByEmail(String email) {
        String sql = "SELECT idUsuario FROM usuario WHERE email = :email";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                      .addParameter("email", email)
                      .executeScalar(Long.class);
        }
    }

    /**
     * Guarda un nuevo usuario y actualiza su id generado.
     */
    public void save(UsuarioEntity user) {
        String sql = "INSERT INTO usuario (rut, nameParam, email, phone, birthdate, password, role) " +
                     "VALUES (:rut, :nameParam, :email, :phone, :birthdate, :password, :role)";
        try (Connection con = sql2o.beginTransaction()) {
            Long generatedId = con.createQuery(sql, true)
                .addParameter("rut", user.getRut())
                .addParameter("nameParam", user.getNameParam())
                .addParameter("email", user.getEmail())
                .addParameter("phone", user.getPhone())
                .addParameter("birthdate", user.getBirthdate())
                .addParameter("password", user.getPassword())
                .addParameter("role", user.getRole())
                .executeUpdate()
                .getKey(Long.class);
            user.setIdUsuario(generatedId);
            con.commit();
        }
    }
}
