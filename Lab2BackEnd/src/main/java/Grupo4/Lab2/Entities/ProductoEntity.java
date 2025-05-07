package Grupo4.Lab2.Entities;

import java.math.BigDecimal;

/**
 * ProductoEntity es una entidad que representa los datos de un producto.
 */
public class ProductoEntity {

    private long producto_id;
    private int empresa_id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private boolean requiere_receta;

    /**
     * Constructor con todos los atributos.
     *
     * @param producto_id El ID del producto.
     * @param empresa_id El ID de la empresa.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param precio El precio del producto.
     * @param requiere_receta Indica si el producto requiere receta.
     */
    public ProductoEntity(long producto_id, int empresa_id, String nombre, String descripcion, BigDecimal precio, boolean requiere_receta) {
        this.producto_id = producto_id;
        this.empresa_id = empresa_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.requiere_receta = requiere_receta;
    }

    /**
     * Constructor vacío.
     */
    public ProductoEntity() {
    }

    // Getters y Setters

    public long getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(long producto_id) {
        this.producto_id = producto_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isRequiere_receta() {
        return requiere_receta;
    }

    public void setRequiere_receta(boolean requiere_receta) {
        this.requiere_receta = requiere_receta;
    }
}