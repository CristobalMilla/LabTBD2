package Grupo4.Lab2.Entities;

public class MediosPagoEntity {
    private long medios_id;
    private String tipo;

    public MediosPagoEntity() {
    }

    public MediosPagoEntity(long medios_id, String tipo) {
        this.medios_id = medios_id;
        this.tipo = tipo;
    }

    public long getMediosPagoId() {
        return medios_id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setMediosPagoId(long medios_id) {
        this.medios_id = medios_id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
