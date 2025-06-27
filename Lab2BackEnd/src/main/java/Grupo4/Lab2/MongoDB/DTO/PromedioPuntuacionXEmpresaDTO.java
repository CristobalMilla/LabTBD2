package Grupo4.Lab2.MongoDB.DTO;

public class PromedioPuntuacionXEmpresaDTO {
    private long empresa_id;
    private String nombre_empresa;
    private double promedio;

    public PromedioPuntuacionXEmpresaDTO() {}

    public PromedioPuntuacionXEmpresaDTO(long empresa_id, String nombre_empresa, double promedio) {
        this.empresa_id = empresa_id;
        this.nombre_empresa = nombre_empresa;
        this.promedio = promedio;
    }

    public long getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(long empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }
}
