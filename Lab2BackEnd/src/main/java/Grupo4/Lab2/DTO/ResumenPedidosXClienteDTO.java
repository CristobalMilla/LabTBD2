package Grupo4.Lab2.DTO;

public class ResumenPedidosXClienteDTO {
    private long cliente_id;
    private String nombre_cliente;
    private int num_pedidos;
    private int monto_total;

    public ResumenPedidosXClienteDTO(long cliente_id, String nombre_cliente, int num_pedidos, int monto_total) {
        this.cliente_id = cliente_id;
        this.nombre_cliente = nombre_cliente;
        this.num_pedidos = num_pedidos;
        this.monto_total = monto_total;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public int getNum_pedidos() {
        return num_pedidos;
    }

    public int getMonto_total() {
        return monto_total;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public void setNum_pedidos(int num_pedidos) {
        this.num_pedidos = num_pedidos;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
    }
}
