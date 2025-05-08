package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.PagoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class PagoRepository {
    private final Sql2o sql2o;
    @Autowired
    public PagoRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    public List<PagoEntity> findAll() {
        String sql = "SELECT * FROM pagos";
        try (Connection con = sql2o.open()) {
            List<PagoEntity> pagos = con.createQuery(sql)
                    .executeAndFetch(PagoEntity.class);
            return pagos;
        }
    }
    public PagoEntity findById(long pago_id) {
        String sql = "SELECT * FROM pagos WHERE pago_id = :pago_id";
        try (Connection con = sql2o.open()) {
            PagoEntity pago = con.createQuery(sql)
                    .addParameter("pago_id", pago_id)
                    .executeAndFetchFirst(PagoEntity.class);
            return pago;
        }
    }
    public List<PagoEntity> findByPedidoId(long pedido_id) {
        String sql = "SELECT * FROM pagos WHERE pedido_id = :pedido_id";
        try (Connection con = sql2o.open()) {
            List<PagoEntity> pagos = con.createQuery(sql)
                    .addParameter("pedido_id", pedido_id)
                    .executeAndFetch(PagoEntity.class);
            return pagos;
        }
    }
    public List<PagoEntity> findByMedioId(long medio_id) {
        String sql = "SELECT * FROM pagos WHERE medio_id = :medio_id";
        try (Connection con = sql2o.open()) {
            List<PagoEntity> pagos = con.createQuery(sql)
                    .addParameter("medio_id", medio_id)
                    .executeAndFetch(PagoEntity.class);
            return pagos;
        }
    }
    public BigDecimal findMontoById(long pago_id){
        String sql = "SELECT pagos.monto FROM pago WHERE pago_id = :pago_id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("pago_id", pago_id)
                    .executeAndFetchFirst(BigDecimal.class);
        }
    }
    public void save(PagoEntity pago) {
        String sql = "INSERT INTO pagos (pedido_id, medio_id, monto) VALUES (:pedido_id, :medio_id, :monto)";
        try (Connection con = sql2o.beginTransaction()){
            Long generatedId = con.createQuery(sql, true)
                    .addParameter("pedido_id", pago.getPedidoId())
                    .addParameter("medio_id", pago.getMedioId())
                    .addParameter("monto", pago.getMonto())
                    .executeUpdate()
                    .getKey(Long.class);
            pago.setPagoId(generatedId);
            con.commit();
        }
    }
    public void update(PagoEntity pago) {
        String sql = "UPDATE pagos SET pedido_id = :pedido_id, medio_id = :medio_id, monto = :monto WHERE pago_id = :pago_id";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("pedido_id", pago.getPedidoId())
                    .addParameter("medio_id", pago.getMedioId())
                    .addParameter("monto", pago.getMonto())
                    .addParameter("pago_id", pago.getPagoId())
                    .executeUpdate();
            con.commit();
        }
    }
    public void deleteById(long pago_id) {
        String sql = "DELETE FROM pagos WHERE pago_id = :pago_id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("pago_id", pago_id)
                    .executeUpdate();
        }
    }
}
