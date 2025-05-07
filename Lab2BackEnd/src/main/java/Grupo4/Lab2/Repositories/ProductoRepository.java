package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.ProductoEntity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Producto implementado con SQL2o.
 */
@Repository
public class ProductoRepository {

    private final Sql2o sql2o;

    @Autowired
    public ProductoRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Busca un producto por su ID.
     */
    public Optional<ProductoEntity> findById(long id) {
        String sql = "SELECT * FROM productos WHERE producto_id = :id";
        try (Connection con = sql2o.open()) {
            ProductoEntity product = con.createQuery(sql)
                                        .addParameter("id", id)
                                        .executeAndFetchFirst(ProductoEntity.class);
            return Optional.ofNullable(product);
        }
    }

    /**
     * Guarda un nuevo producto.
     */
    public void save(ProductoEntity product) {
        String sql = "INSERT INTO productos (empresa_id, nombre, descripcion, precio, requiere_receta) " +
                     "VALUES (:empresa_id, :nombre, :descripcion, :precio, :requiere_receta)";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
                .addParameter("empresa_id", product.getEmpresa_id())
                .addParameter("nombre", product.getNombre())
                .addParameter("descripcion", product.getDescripcion())
                .addParameter("precio", product.getPrecio())
                .addParameter("requiere_receta", product.isRequiere_receta())
                .executeUpdate();
            con.commit();
        }
    }

    /**
     * Obtiene todos los productos.
     */
    public List<ProductoEntity> findAll() {
        String sql = "SELECT * FROM productos";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                      .executeAndFetch(ProductoEntity.class);
        }
    }

    /**
     * Elimina un producto por su ID.
     */
    public void deleteById(long id) {
        String sql = "DELETE FROM productos WHERE producto_id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
        }
    }
}