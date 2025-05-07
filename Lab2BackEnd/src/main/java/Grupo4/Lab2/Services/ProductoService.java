package Grupo4.Lab2.Services;

import Grupo4.Lab2.Entities.ProductoEntity;
import Grupo4.Lab2.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepo;

    /**
     * Guarda un nuevo producto.
     *
     * @param producto El producto a guardar.
     * @return El producto guardado.
     */
    public ProductoEntity save(ProductoEntity producto) {
        productoRepo.save(producto);
        return producto;
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return El producto correspondiente al ID proporcionado.
     * @throws RuntimeException Si no se encuentra el producto.
     */
    public ProductoEntity findById(long id) {
        Optional<ProductoEntity> productoOpt = productoRepo.findById(id);
        return productoOpt.orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los productos.
     *
     * @return Una lista de todos los productos.
     */
    public List<ProductoEntity> findAll() {
        return productoRepo.findAll();
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     */
    public void deleteById(long id) {
        productoRepo.deleteById(id);
    }
}