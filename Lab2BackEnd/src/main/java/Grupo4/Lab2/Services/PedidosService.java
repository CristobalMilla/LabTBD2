package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.PedidoYZonasQueCruzaDTO;
import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.DTO.RutaFrecuenciaDTO;
import Grupo4.Lab2.Entities.DetallePedidosEntity;
import Grupo4.Lab2.Entities.PedidosEntity;
import Grupo4.Lab2.Repositories.DetallePedidosRepository;
import Grupo4.Lab2.Repositories.PedidosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private DetallePedidosRepository detallePedidosRepository; // Inyectar el repositorio de detalles

    public PedidosEntity getById(long idPedido) {
        return pedidosRepository.findById(idPedido);
    }

    public List<PedidosEntity> getAll() {
        return pedidosRepository.findAll();
    }

    // ELIMINA O COMENTA EL ANTIGUO MÉTODO registrarPedido
    /*
    public long registrarPedido(RegistrarPedidoDTO dto) {
        // ... este método ya no se usará directamente
    }
    */

    public void cambiarEstadoPedido(int pedidoId, String nuevoEstado) {
        pedidosRepository.cambiarEstadoPedido(pedidoId, nuevoEstado);
    }

    public void delete(long pedidoId) {
        pedidosRepository.deleteById(pedidoId);
    }

    public void confirmarPedidoDescontar(int pedidoId) {
        pedidosRepository.confirmarPedidoDescontar(pedidoId);
    }

    // Query 5
    // Listar todos los pedidos cuya ruta estimada cruce más de 2 zonas de reparto.
    public List<PedidoYZonasQueCruzaDTO> pedidosQueCruzanMasDe2Zonas(){
        List<PedidoYZonasQueCruzaDTO> pedidos = pedidosRepository.getPedidosQueCruzanMasDe2Zonas();
        for(PedidoYZonasQueCruzaDTO pedido : pedidos){
            try{
                pedido.setIds_zonas(pedidosRepository.getListaIdsZonasCruzadasById(pedido.getPedido_id()));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return pedidos;
    }

    //Funciones de actualizar pedido
    public PedidosEntity updatePedidoPuntos(PedidosEntity pedido) {
        return pedidosRepository.updatePedidoPuntos(pedido);
    }
    public boolean updatePedidoRuta(PedidosEntity pedido) {
        return pedidosRepository.updatePedidoRuta(pedido);
    }

    // Query 4
    // Obtiene las rutas únicas y su frecuencia para un repartidor en los últimos 7 días.
    public List<RutaFrecuenciaDTO> getRutaFrecuenciaPorRepartidor(long repartidorId) {
        return pedidosRepository.findRutaFrecuenciaByRepartidorEnUltimos7Dias(repartidorId);
    }

    //Funcion de crear pedido por completo
    @Transactional
    public PedidosEntity crearPedidoCompleto(RegistrarPedidoDTO dto) {
        // 1. Crear el pedido básico para obtener el ID
        long pedidoId = pedidosRepository.crearPedidoBasico(dto);
        
        // 2. Obtener la entidad recién creada
        PedidosEntity pedidoRegistrado = pedidosRepository.findById(pedidoId);
        if (pedidoRegistrado == null) {
            throw new RuntimeException("No se pudo encontrar el pedido recién creado con ID: " + pedidoId);
        }

        // 3. Actualizar el pedido con puntos de inicio/final y calcular la ruta
        PedidosEntity pedidoConPuntos = pedidosRepository.updatePedidoPuntos(pedidoRegistrado);
        boolean rutaActualizada = pedidosRepository.updatePedidoRuta(pedidoConPuntos);
        if (!rutaActualizada) {
            System.err.println("Advertencia: No se pudo calcular la ruta para el pedido con ID: " + pedidoId);
        }

        // 4. Crear los detalles del pedido
        Integer[] productos = dto.getProductos();
        Integer[] cantidades = dto.getCantidades();
        if (productos != null && cantidades != null && productos.length == cantidades.length) {
            for (int i = 0; i < productos.length; i++) {
                DetallePedidosEntity detalle = new DetallePedidosEntity();
                detalle.setPedido_id(pedidoId);
                detalle.setProducto_id(productos[i]);
                detalle.setCantidad(cantidades[i]);
                detallePedidosRepository.save(detalle);
            }
        }

        // 5. Devolver la entidad del pedido completa con su ruta
        return pedidosRepository.findById(pedidoId);
    }
}
