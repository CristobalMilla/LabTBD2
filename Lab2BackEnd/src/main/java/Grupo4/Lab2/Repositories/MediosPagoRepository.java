package Grupo4.Lab2.Repositories;


import Grupo4.Lab2.Entities.MediosPagoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class MediosPagoRepository {
    private final Sql2o sql2o;

    @Autowired
    public MediosPagoRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    public List<MediosPagoEntity> findAll(){
        String sql = "SELECT * FROM medios_pago";
        try (Connection con = sql2o.open()){
            List<MediosPagoEntity> medios_pago = con.createQuery(sql)
                    .executeAndFetch(MediosPagoEntity.class);
            return medios_pago;
        }
    }
    public MediosPagoEntity findById(long medio_id){
        String sql = "SELECT * FROM medios_pago WHERE medio_id = :medio_id";
        try (Connection con = sql2o.open()){
            MediosPagoEntity medio_pago = con.createQuery(sql)
                    .executeAndFetchFirst(MediosPagoEntity.class);
            return medio_pago;
        }
    }
    public void save(MediosPagoEntity medio_pago) {
        String sql = "INSERT INTO medios_pago (tipo) VALUES (:tipo)";
        try (Connection con = sql2o.beginTransaction()){
            Long generatedId = con.createQuery(sql, true)
                    .addParameter("tipo", medio_pago.getTipo())
                    .executeUpdate()
                    .getKey(Long.class);
            medio_pago.setMediosPagoId(generatedId);
            con.commit();
        }
    }
    public void update(MediosPagoEntity medio_pago) {
        String sql = "UPDATE medios_pago SET tipo = :tipo WHERE id = :medio_id";
        try (Connection con = sql2o.beginTransaction()){
            con.createQuery(sql)
                    .addParameter("tipo", medio_pago.getTipo())
                    .addParameter("medio_id", medio_pago.getMediosPagoId())
                    .executeUpdate();
            con.commit();
        }
    }
    public void deleteById(long medio_id) {
        String sql = "DELETE FROM medios_pago WHERE id = :medio_id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("medio_id", medio_id)
                    .executeUpdate();
        }
    }
}
