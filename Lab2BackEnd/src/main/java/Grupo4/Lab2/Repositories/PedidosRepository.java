package Grupo4.Lab2.Repositories;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Entities.PedidosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Array;
import java.util.List;
import java.util.Map;

@Repository
public class PedidosRepository {

    @Autowired
    private final Sql2o sql2o;
    private final GeometryFactory geometryFactory;
    private final WKTReader wktReader;

    @Autowired
    public PedidosRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.geometryFactory = new GeometryFactory();
        this.wktReader = new WKTReader(this.geometryFactory);
    }

    public PedidosEntity findById(long idPedido) {
        try (Connection conn = sql2o.open()) {
            List<Map<String, Object>> pedido;
            String query = "SELECT pedido_id, cliente_id, empresa_id, repartidor_id, ST_AsText(punto_inicio), ST_AsText(punto_final), ST_AsText(ruta_estimada) " +
                    "FROM pedidos WHERE pedido_id = :idPedido";
            pedido = conn.createQuery(query)
                    .addParameter("idPedido", idPedido)
                    .executeAndFetchTable()
                    .asList();
            return mapToPedidosEntity(pedido.get(0));
        } catch (Exception e){
            System.err.println("Error al obtener el pedido de id : "+ idPedido +".\n"+ e.getMessage());
            return null;
        }
    }

    public List<PedidosEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<Map<String, Object>> pedidos;
            String query = "SELECT pedido_id, cliente_id, empresa_id, repartidor_id, ST_AsText(punto_inicio), ST_AsText(punto_final), ST_AsText(ruta_estimada)" +
                    " FROM pedidos ORDER BY pedido_id";
            pedidos = conn.createQuery(query)
                    .executeAndFetchTable()
                    .asList();
            return pedidos.stream().map(this::mapToPedidosEntity).toList();
        } catch (Exception e){
            System.err.println("Error al obtener los pedidos.\n " + e.getMessage());
            return null;
        }
    }

    private PedidosEntity mapToPedidosEntity(Map<String, Object> row){
        PedidosEntity pedido = new PedidosEntity();
        pedido.setPedido_id((Long) row.get("pedido_id"));
        pedido.setCliente_id((Long) row.get("cliente_id"));
        pedido.setEmpresa_id((Long) row.get("empresa_id"));
        pedido.setRepartidor_id((Long) row.get("repartidor_id"));
        pedido.setEstado((String) row.get("estado"));

        String punto_inicio = (String) row.get("punto_inicio");
        String punto_final = (String) row.get("punto_final");
        String ruta_estimada = (String) row.get("ruta_estimada");
        if (punto_inicio != null && punto_final != null && ruta_estimada != null) {
            try {
                Point punto1 = (Point) wktReader.read(punto_inicio);
                punto1.setSRID(4326);
                pedido.setPunto_inicio(punto1);
                Point punto2 = (Point) wktReader.read(punto_final);
                punto2.setSRID(4326);
                pedido.setPunto_final(punto2);
                LineString linea = (LineString) wktReader.read(ruta_estimada);
                linea.setSRID(4326);
                pedido.setRuta_estimada(linea);
            } catch (ParseException e) {
                System.err.println("Error al leer el punto de inicio o final o la ruta estimada del pedido " + pedido.getPedido_id() + "\n" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return pedido;
    }

    // 7.
    public boolean registrarPedido(RegistrarPedidoDTO dto) {
        String sql = "SELECT registrar_pedido(" +
                    ":clienteId," +
                    ":empresaId," +
                    ":repartidorId, " +
                    ":estado, " +
                    ":productos, " +
                    ":cantidades, " +
                    ":medioPagoId::INTEGER)";

        try (Connection conn = sql2o.open()) {
            // Acceder a la conexión JDBC para usar el createArray
            java.sql.Connection jdbcConn = conn.getJdbcConnection();

            // Crea un sql array de tipo INTEGER[]
            // basicamente convierte el arrreglo Integer[] a SQL INTEGER[]
            Array productosSql = jdbcConn.createArrayOf("INTEGER", dto.getProductos());
            Array cantidadesSql = jdbcConn.createArrayOf("INTEGER", dto.getCantidades());

            Object result = conn.createQuery(sql)
                    .addParameter("clienteId", dto.getCliente_id())
                    .addParameter("empresaId", dto.getEmpresa_id())
                    .addParameter("repartidorId", dto.getRepartidor_id())
                    .addParameter("estado", dto.getEstado())
                    .addParameter("productos", productosSql) // el objeto productos
                    .addParameter("cantidades", cantidadesSql)
                    .addParameter("medioPagoId", dto.getMedio_pago_id())
                    .executeScalar();

            long id_pedido = ((Number) result).longValue();
            System.out.println("Pedido registrado con ID: " + id_pedido);
            return true;
        } catch (Exception e) {
            Throwable cause = e.getCause(); // toma la excepcion original y revisa si fue causada x una excepción interna
            String mensaje = cause != null ? cause.getMessage() : e.getMessage(); // si la causa interna es null se muestra la excepcion principal, si no la especifica
            System.err.println("Error al registrar el pedido: \n" + mensaje);// se imprime el mensaje de error
            throw new RuntimeException(mensaje, e);// permite q el controlador devuelva el msj al frontend como postman x ej
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
}
