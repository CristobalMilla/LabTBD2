package Grupo4.Lab2.DTO;
import java.util.Date;

/**

 RegisterDto es una clase de transferencia de datos que representa los datos de registro de un usuario.*/

public class RegisterDTO {
    private String rut;
    private String nameParam;
    private String email;
    private String phone;
    private Date birthdate;
    private String password;
    private String role;

    public RegisterDTO(String rut, String nameParam, String email, String phone, Date birthdate, String password, String role) {
        this.rut = rut;
        this.nameParam = nameParam;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.password = password;
        this.role = role;
    }

    public RegisterDTO() {
    }

    public String getRut() {
        return rut;
    }

    public String getNameParam() {
        return nameParam;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNameParam(String nameParam) {
        this.nameParam = nameParam;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}