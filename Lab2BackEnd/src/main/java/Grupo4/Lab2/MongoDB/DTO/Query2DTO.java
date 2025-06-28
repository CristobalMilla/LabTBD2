package Grupo4.Lab2.MongoDB.DTO;

public class Query2DTO {
    private Long cliente_id;
    private String comentarios;

    public Query2DTO(Long cliente_id, String comentarios) {
        this.cliente_id = cliente_id;
        this.comentarios = comentarios;
    }

    public Query2DTO() {
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
