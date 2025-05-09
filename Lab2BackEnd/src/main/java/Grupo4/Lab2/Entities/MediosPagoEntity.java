package Grupo4.Lab2.Entities;

public class MediosPagoEntity {
    private long mediosPagoId;
    private String tipo;

    public MediosPagoEntity() {
    }

    public MediosPagoEntity(long mediosPagoId, String tipo) {
        this.mediosPagoId = mediosPagoId;
        this.tipo = tipo;
    }

    public long getMediosPagoId() {
        return mediosPagoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setMediosPagoId(long mediosPagoId) {
        this.mediosPagoId = mediosPagoId;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
