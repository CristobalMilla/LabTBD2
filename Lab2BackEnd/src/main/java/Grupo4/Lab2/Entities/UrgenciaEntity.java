package Grupo4.Lab2.Entities;

public class UrgenciaEntity {

    private long urgencia_id;
    private int pedido_id;
    private String nivel; // Valores permitidos: 'urgente' o 'no urgente'

    // Constructor con todos los atributos
    public UrgenciaEntity(long urgencia_id, int pedido_id, String nivel) {
        this.urgencia_id = urgencia_id;
        this.pedido_id = pedido_id;
        this.nivel = nivel;
    }

    // Constructor vacío
    public UrgenciaEntity() {
    }

    // Getters y Setters
    public long getUrgencia_id() {
        return urgencia_id;
    }

    public void setUrgencia_id(long urgencia_id) {
        this.urgencia_id = urgencia_id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}