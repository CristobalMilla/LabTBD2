package Grupo4.Lab2.MongoDB.DTO;

import java.util.List;

public class ClienteBuscaPeroNoCompra {
    private long cliente_id;
    private String nombre_cliente;
    private List<String> productos_buscados;

    public ClienteBuscaPeroNoCompra() {
    }

    public ClienteBuscaPeroNoCompra(long cliente_id, String nombre_cliente, List<String> productos_buscados) {
        this.cliente_id = cliente_id;
        this.nombre_cliente = nombre_cliente;
        this.productos_buscados = productos_buscados;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public List<String> getProductos_buscados() {
        return productos_buscados;
    }

    public void setProductos_buscados(List<String> productos_buscados) {
        this.productos_buscados = productos_buscados;
    }
}
