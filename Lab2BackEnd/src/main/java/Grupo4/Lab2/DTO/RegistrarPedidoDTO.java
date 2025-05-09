package Grupo4.Lab2.DTO;

public class RegistrarPedidoDTO {
    private int cliente_id;
    private int empresa_id;
    private int repartidor_id;
    private String estado;
    private Integer[] productos;
    private Integer[] cantidades;
    private int medio_pago_id;

    public RegistrarPedidoDTO() {
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public int getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(int repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer[] getProductos() {
        return productos;
    }

    public void setProductos(Integer[] productos) {
        this.productos = productos;
    }

    public Integer[] getCantidades() {
        return cantidades;
    }

    public void setCantidades(Integer[] cantidades) {
        this.cantidades = cantidades;
    }

    public int getMedio_pago_id() {
        return medio_pago_id;
    }

    public void setMedio_pago_id(int medio_pago_id) {
        this.medio_pago_id = medio_pago_id;
    }
}
