package Grupo4.Lab2.DTO;

public class ProductoMasPedidoDTO {
    private String categoria;
    private long producto_id;
    private String nombre;
    private int total_cantidad;

    // Getters y Setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(long producto_id) {
        this.producto_id = producto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotal_cantidad() {
        return total_cantidad;
    }

    public void setTotal_cantidad(int total_cantidad) {
        this.total_cantidad = total_cantidad;
    }
}