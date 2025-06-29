package Grupo4.Lab2.MongoDB.DTO;

public class Query2DTO {
    private String cliente;
    private String comentarios;

    public Query2DTO(String cliente, String comentarios) {
        this.cliente = cliente;
        this.comentarios = comentarios;
    }

    public Query2DTO() {
    }


    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
