package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.DTO.RegistrarPedidoDTO;
import Grupo4.Lab2.Entities.PedidosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Array;
import java.util.List;

@Repository
public class PedidosRepository {

    private final Sql2o sql2o;

    @Autowired
    public PedidosRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public PedidosEntity findById(long idPedido) {
        try (Connection conn = sql2o.open()) {
            PedidosEntity pedido;
            String query = "SELECT * FROM pedidos WHERE pedido_id = :idPedido";
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
            String query = "SELECT * FROM pedidos ORDER BY pedido_id";
            pedidos = conn.createQuery(query)
                    .executeAndFetch(PedidosEntity.class);
            return pedidos;
        } catch (Exception e){
            System.err.println("Error al obtener los pedidos.\n " + e.getMessage());
            return null;
        }
    }

    public void update(PedidosEntity pedido) {
        try (Connection conn = sql2o.open()) {
            String query = "UPDATE pedidos SET " +
                    "cliente_id = :clienteId, " +
                    "empresa_id = :empresaId, " +
                    "repartidor_id = :repartidorId, " +
                    "fecha = :fecha, " +
                    "fecha_entrega = :fechaEntrega, " +
                    "estado = :estado " +

                    "WHERE pedido_id = :pedidoId";

            conn.createQuery(query)
                    .addParameter("clienteId", pedido.getCliente_id())
                    .addParameter("empresaId", pedido.getEmpresa_id())
                    .addParameter("repartidorId", pedido.getRepartidor_id())
                    .addParameter("fecha", pedido.getFecha())
                    .addParameter("fechaEntrega", pedido.getFecha_entrega())
                    .addParameter("estado", pedido.getEstado())
                    .addParameter("pedidoId", pedido.getPedido_id())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al actualizar el pedido con ID " + pedido.getPedido_id() + ": " + e.getMessage());
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
                // Acceder a la conexión JDBC subyacente
                java.sql.Connection jdbcConn = conn.getJdbcConnection();

                // Crear los arreglos SQL
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
                System.err.println("Error al registrar el pedido: " + e.getMessage());
                return false;
            }
        }
}
