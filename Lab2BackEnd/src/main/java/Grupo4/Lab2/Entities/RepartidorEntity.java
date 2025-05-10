package Grupo4.Lab2.Entities;

import jakarta.persistence.*;

@Entity
public class RepartidorEntity {

    @Id
    @Column(unique = true, nullable = false)
    private long repartidor_id;
    private String nombre;
    private String telefono;
    private Boolean disponible;

    public RepartidorEntity(long repartidor_id, String nombre, String telefono, Boolean disponible) {
        this.repartidor_id = repartidor_id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.disponible = disponible;
    }

    public RepartidorEntity() {

    }

    public long getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(long repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
