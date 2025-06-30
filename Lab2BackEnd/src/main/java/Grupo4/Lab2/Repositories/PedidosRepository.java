package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.DTO.PedidoYZonasQueCruzaDTO;
import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.DTO.RutaFrecuenciaDTO;
import Grupo4.Lab2.Entities.PedidosEntity;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PedidosRepository {

    private final Sql2o sql2o;
    private final RepartidorRepository repartidorRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public PedidosRepository(Sql2o sql2o, RepartidorRepository repartidorRepository, ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.sql2o = sql2o;
        this.repartidorRepository = repartidorRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    public PedidosEntity findById(long idPedido) {
        try (Connection conn = sql2o.open()) {
            PedidosEntity pedido;
            String query = "SELECT pedido_id, " +
                    "cliente_id, " +
                    "empresa_id, " +
                    "repartidor_id, " +
                    "estado," +
                    "fecha," +
                    "fecha_entrega," +
                    "ST_AsText(punto_inicio) AS puntoInicioWkt, " +
                    "ST_AsText(punto_final) AS puntoFinalWkt, " +
                    "ST_AsText(ruta_estimada) AS rutaEstimadaWkt " +
                    "FROM pedidos WHERE pedido_id = :idPedido";
            pedido = conn.createQuery(query)
                    .addParameter("idPedido", idPedido)
                    .executeAndFetchFirst(PedidosEntity.class);
            return pedido;
        } catch (Exception e){
            System.err.println("Error al obtener el pedido de id : "+ idPedido +".\n"+ e.getMessage());
            return null;
        }
    }

    public List<PedidosEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<PedidosEntity> pedidos;
            String query = "SELECT pedido_id, " +
                    "cliente_id, " +
                    "empresa_id, " +
                    "repartidor_id," +
                    "estado," +
                    "fecha, " +
                    "fecha_entrega," +
                    "ST_AsText(punto_inicio) AS puntoInicioWkt, " +
                    "ST_AsText(punto_final) AS puntoFinalWkt, " +
                    "ST_AsText(ruta_estimada) AS rutaEstimadaWkt " +
                    "FROM pedidos ORDER BY pedido_id";
            pedidos = conn.createQuery(query)
                    .executeAndFetch(PedidosEntity.class);
            return pedidos;
        } catch (Exception e){
            System.err.println("Error al obtener los pedidos.\n " + e.getMessage());
            return null;
        }
    }

    // 7. Crear un pedido básico (sin detalles) y retornar el ID del pedido creado.
    public long crearPedidoBasico(RegistrarPedidoDTO dto) {
        String sql = "INSERT INTO pedidos (cliente_id, empresa_id, repartidor_id, estado, fecha) " +
                     "VALUES (:clienteId, :empresaId, :repartidorId, :estado, NOW())";
        
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql, true) // true para retornar la llave generada
                    .addParameter("clienteId", dto.getCliente_id())
                    .addParameter("empresaId", dto.getEmpresa_id())
                    .addParameter("repartidorId", dto.getRepartidor_id())
                    .addParameter("estado", dto.getEstado())
                    .executeUpdate()
                    .getKey(Long.class); // Obtiene el pedido_id generado
        } catch (Exception e) {
            System.err.println("Error al crear el pedido básico: \n" + e.getMessage());
            throw new RuntimeException("Error al crear el pedido básico.", e);
        }
    }

    // 8. y 10 (trigger)
    public void cambiarEstadoPedido(int pedidoId, String nuevoEstado) {
        String sql = "SELECT cambiar_estado_pedido(:pedidoId, :nuevoEstado)";

        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("pedidoId", pedidoId)
                    .addParameter("nuevoEstado", nuevoEstado)
                    .executeScalar();
        } catch (Exception e) {
            System.err.println("Error al cambiar estado del pedido " + pedidoId + "\n" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteById(long idPedido) {
        try (Connection conn = sql2o.open()) {
            String query = "DELETE FROM pedidos WHERE pedido_id = :idPedido";
            conn.createQuery(query)
                    .addParameter("idPedido", idPedido)
                    .executeUpdate();
        } catch (Exception e){
            System.err.println("Error al borrar el pedido de id : "+ idPedido +".\n"+ e.getMessage());
        }
    }

    // Procedimiento 9.
    public void confirmarPedidoDescontar(int pedidoId) {
        String sql = "SELECT confirmar_pedido_descontar(:pedidoId)";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                .addParameter("pedidoId", pedidoId)
                .executeScalar();
        } catch (Exception e) {
            System.err.println("Error al confirmar y descontar el pedido " + pedidoId + "\n" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // Query 5
    // Listar todos los pedidos cuya ruta estimada cruce más de 2 zonas de reparto.
    public List<Long> getListaIdsZonasCruzadasById(long pedido_id) {
        try (Connection conn = sql2o.open()) {
            String query = "SELECT z.zona_id " +
                           "FROM pedidos p " +
                           "INNER JOIN zonas_cobertura z ON ST_Intersects(p.ruta_estimada, z.geom) " +
                           "WHERE p.pedido_id = :pedido_id " +
                           "GROUP BY p.pedido_id, z.zona_id";
            List<Map<String, Object>> result = conn.createQuery(query)
                    .addParameter("pedido_id", pedido_id)
                    .executeAndFetchTable()
                    .asList();
            List<Long> ids = new ArrayList<>();
            for(Map<String, Object> id : result){
                ids.add(((Integer) id.get("zona_id")).longValue());
            }
            return ids;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PedidoYZonasQueCruzaDTO> getPedidosQueCruzanMasDe2Zonas() {
        try (Connection conn = sql2o.open()) {
            String query = "SELECT p.pedido_id, p.cliente_id, p.empresa_id, p.repartidor_id, COUNT(z.zona_id) AS zonas_que_cruza " +
                           "FROM pedidos p " +
                           "INNER JOIN zonas_cobertura z ON ST_Intersects(p.ruta_estimada, z.geom) " +
                           "GROUP BY p.pedido_id " +
                           "HAVING COUNT(z.zona_id) > 2";
            return conn.createQuery(query).executeAndFetch(PedidoYZonasQueCruzaDTO.class);

        } catch (Exception e) {
            System.err.println("Error al obtener los pedidos.");
            e.printStackTrace();
            return null;
        }
    }

    //Hacer update al pedido, agregando punto_inicio y punto_final
    public PedidosEntity updatePedidoPuntos(PedidosEntity pedido) {
        Point punto_inicio = empresaRepository.findById(pedido.getRepartidor_id()).getUbicacion();
        Point punto_final = clienteRepository.findById(pedido.getCliente_id()).getUbicacion();
        String sql = "UPDATE pedidos " +
                "SET punto_inicio = ST_GeomFromText(:punto_inicio, 4326), punto_final = ST_GeomFromText(:punto_final, 4326) " +
                "WHERE pedido_id = :pedido_id";
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery(sql)
                    .addParameter("punto_inicio", punto_inicio.toString())
                    .addParameter("punto_final", punto_final.toString())
                    .addParameter("pedido_id", pedido.getPedido_id())
                    .executeUpdate();
            conn.commit();
        }
        pedido.setPunto_inicio(punto_inicio);
        pedido.setPunto_final(punto_final);
        return pedido;
    }

    //Hacer update al pedido, agregando ruta
    public boolean updatePedidoRuta(PedidosEntity pedido) {
        String sql = "UPDATE pedidos AS p " +
                "SET ruta_estimada = (" +
                    "SELECT ST_LineMerge(ST_Collect(seg.geom)) " +
                    "FROM pgr_dijkstra(" +
                        "'SELECT cleaned_street_id AS id, source, target, cost FROM calles_cleaned', " +
                        "find_nearest_node(e.ubicacion), " +
                        "find_nearest_node(cl.ubicacion), " +
                        "directed := FALSE " +
                    ") AS route " +
                    "JOIN calles_cleaned AS seg ON route.edge = seg.cleaned_street_id) " +
                "FROM empresas AS e, clientes AS cl " +
                "WHERE p.empresa_id = e.empresa_id " +
                    "AND p.cliente_id = cl.cliente_id " +
                    "AND p.pedido_id = :pedido_id";
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery(sql)
                    .addParameter("pedido_id", pedido.getPedido_id())
                    .executeUpdate();
            conn.commit();
            return true;
        }
        catch (Exception e){
            System.err.println("Error al actualizar la ruta del pedido " + pedido.getPedido_id() + "\n" + e.getMessage());
            return false;
        }
    }

    // Query 4
    public List<RutaFrecuenciaDTO> findRutaFrecuenciaByRepartidorEnUltimos7Dias(long repartidorId) {
        String sql = "SELECT " +
                     "    ST_AsText(ruta_estimada) AS rutaEstimadaWkt, " +
                     "    COUNT(*) AS frecuencia " +
                     "FROM pedidos " +
                     "WHERE repartidor_id = :repartidorId " +
                     "  AND fecha >= NOW() - INTERVAL '7 days' " +
                     "GROUP BY ST_AsText(ruta_estimada) " +
                     "ORDER BY frecuencia DESC";

        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("repartidorId", repartidorId)
                    .executeAndFetch(RutaFrecuenciaDTO.class);
        } catch (Exception e) {
            System.err.println("Error al obtener la frecuencia de rutas para el repartidor " + repartidorId + ":\n" + e.getMessage());
            throw new RuntimeException("Error al consultar la frecuencia de rutas.", e);
        }
    }

    public PedidosEntity setPedidoRuta(PedidosEntity pedido){
        String sql = "SELECT ST_AsText(p.ruta_estimada) AS ruta_estimada " +
                "FROM pedidos AS p" +
                "WHERE p.pedido_id = :pedido_id";
        try (Connection conn = sql2o.beginTransaction()){
            LineString ruta = (LineString) conn.createQuery(sql)
                    .addParameter("pedido_id", pedido.getPedido_id())
                    .executeScalar();
            conn.commit();
            pedido.setRuta_estimada(ruta);
            return pedido;
        }
    }
    public PedidosEntity getByNombreProductoYCliente(String producto, long cliente_id){
        String sql = "SELECT p.pedido_id " +
                "FROM pedidos AS p " +
                "INNER JOIN detalle_pedidos dp ON p.pedido_id = dp.pedido_id " +
                "INNER JOIN productos pr ON dp.producto_id = pr.producto_id " +
                "WHERE pr.nombre = :producto AND p.cliente_id = :cliente_id";
        try (Connection conn = sql2o.beginTransaction()){
            return conn.createQuery(sql)
                    .addParameter("producto", producto)
                    .addParameter("cliente_id", cliente_id)
                    .executeAndFetchFirst(PedidosEntity.class);
        }
    }

}
