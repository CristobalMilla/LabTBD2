package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.DetallePedidosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DetallePedidosRepository {

    private final Sql2o sql2o;

    @Autowired
    public DetallePedidosRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public DetallePedidosEntity findById(long idDetalle) {
        try (Connection conn = sql2o.open()) {
            DetallePedidosEntity pedido;
            String query = "SELECT * FROM detalle_pedidos WHERE detalle_id = :idDetalle";
            pedido = conn.createQuery(query)
                    .addParameter("idDetalle", idDetalle)
                    .executeAndFetchFirst(DetallePedidosEntity.class);
            return pedido;
        } catch (Exception e){
            System.err.println("Error al obtener el DetallePedido de id : "+ idDetalle +".\n"+ e.getMessage());
            return null;
        }
    }

    public List<DetallePedidosEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<DetallePedidosEntity> detalles;
            String query = "SELECT * FROM detalle_pedidos ORDER BY detalle_id";
            detalles = conn.createQuery(query)
                    .executeAndFetch(DetallePedidosEntity.class);
            return detalles;
        } catch (Exception e){
            System.err.println("Error al obtener los DetallesPedidos.\n " + e.getMessage());
            return null;
        }
    }

    public void save(DetallePedidosEntity detalle) {
        try (Connection conn = sql2o.open()) {
            String query = "INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad) " +
                    "VALUES (:pedido_id, :producto_id, :cantidad)";
            long detalle_id = conn.createQuery(query, true)
                    .addParameter("pedido_id", detalle.getPedido_id())
                    .addParameter("producto_id", detalle.getProducto_id())
                    .addParameter("cantidad", detalle.getCantidad())
                    .executeUpdate()
                    .getKey(Long.class);

            detalle.setDetalle_id(detalle_id);
        }
        catch (Exception e){
            System.out.println("Error al guardar el detalle"+ e.getMessage());
        }
    }

    public void update(DetallePedidosEntity detalle) {
        try (Connection conn = sql2o.open()) {
            String query = "UPDATE detalle_pedidos SET pedido_id = :pedido_id, producto_id = :producto_id, " +
                    "cantidad = :cantidad WHERE detalle_id = :detalle_id";

            conn.createQuery(query)
                    .addParameter("pedido_id", detalle.getPedido_id())
                    .addParameter("producto_id", detalle.getProducto_id())
                    .addParameter("cantidad", detalle.getCantidad())
                    .addParameter("detalle_id", detalle.getDetalle_id())
                    .executeUpdate();
        }
        catch (Exception e){
            System.out.println("Error al actualizar el detalle"+ e.getMessage());
        }
    }

    public void deleteById(long idDetalle) {
        try (Connection conn = sql2o.open()) {
            String query = "DELETE FROM detalle_pedidos WHERE detalle_id = :idDetallePedido";
            conn.createQuery(query)
                    .addParameter("idDetallePedido", idDetalle)
                    .executeUpdate();
        } catch (Exception e){
            System.err.println("Error al borrar el Detallepedido de id : "+ idDetalle +".\n"+ e.getMessage());
        }
    }
}
