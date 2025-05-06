package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * UsuarioEntity es una entidad que representa los datos de un usuario.
 */

public class UsuarioEntity {

    private long idUsuario;

    private String rut;
    private String nameParam;
    private String email;
    private String phone;

    @JsonFormat
    private Date birthdate;

    private String password;
    private String role;

    /**
     * Constructor con todos los atributos.
     *
     * @param rut El RUT del usuario.
     * @param nameParam El nombre del usuario.
     * @param email El email del usuario.
     * @param phone El teléfono del usuario.
     * @param birthdate La fecha de nacimiento del usuario.
     * @param password La contraseña del usuario.
     * @param role El rol del usuario.
     */
    public UsuarioEntity(String rut, String nameParam, String email, String phone, Date birthdate, String password, String role) {
        this.rut = rut;
        this.nameParam = nameParam;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor vacío.
     */
    public UsuarioEntity() {
    }
}
