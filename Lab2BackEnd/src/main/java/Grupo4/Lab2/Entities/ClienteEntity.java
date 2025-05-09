package Grupo4.Lab2.Entities;

public class ClienteEntity {
    private long cliente_id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    public ClienteEntity(long cliente_id, String nombre, String direccion, String email, String telefono) {
        this.cliente_id = cliente_id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public ClienteEntity() {
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
