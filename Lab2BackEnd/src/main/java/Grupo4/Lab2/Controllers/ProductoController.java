package Grupo4.Lab2.Controllers;

import Grupo4.Lab2.DTO.ProductoMasPedidoDTO;
import Grupo4.Lab2.Entities.ProductoEntity;
import Grupo4.Lab2.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Endpoint para guardar un nuevo producto.
     *
     * @param producto Los detalles del nuevo producto.
     * @return La respuesta HTTP con el producto guardado o un mensaje de error.
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductoEntity producto) {
        try {
            ProductoEntity savedProducto = productoService.save(producto);
            return ResponseEntity.ok(savedProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Endpoint para obtener un producto por su ID.
     *
     * @param id El ID del producto.
     * @return El producto correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getProductoById(@PathVariable("id") long id) {
        try {
            ProductoEntity producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint para obtener todos los productos.
     *
     * @return La lista de todos los productos.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductoEntity>> getAllProductos() {
        List<ProductoEntity> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint para eliminar un producto por su ID.
     *
     * @param id El ID del producto.
     * @return La respuesta HTTP con el estado de la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable("id") long id) {
        try {
            productoService.deleteById(id);
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para la query 2: Obtener los productos más pedidos en el último mes.
     */
    @GetMapping("/mas-pedidos-ultimo-mes")
    public ResponseEntity<List<ProductoMasPedidoDTO>> getProductosMasPedidosUltimoMes() {
        List<ProductoMasPedidoDTO> resultados = productoService.getProductosMasPedidosUltimoMes();
        return ResponseEntity.ok(resultados);
    }
}