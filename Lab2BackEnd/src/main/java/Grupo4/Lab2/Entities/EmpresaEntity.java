package Grupo4.Lab2.Entities;

public class EmpresaEntity {
    private long empresaId;
    private String nombre;
    private String direccion;
    private String tipoServicio;

    public EmpresaEntity(String nombre, long empresaId, String direccion, String tipoServicio) {
        this.nombre = nombre;
        this.empresaId = empresaId;
        this.direccion = direccion;
        this.tipoServicio = tipoServicio;
    }

    public EmpresaEntity() {
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}
