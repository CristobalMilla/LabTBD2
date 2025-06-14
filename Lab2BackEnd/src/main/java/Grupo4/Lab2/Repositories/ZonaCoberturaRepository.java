package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.ZonaCoberturaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ZonaCoberturaRepository {

    private final Sql2o sql2o;

    @Autowired
    public ZonaCoberturaRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<ZonaCoberturaEntity> findAll() {
        String query = "SELECT * FROM zonas_cobertura";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .executeAndFetch(ZonaCoberturaEntity.class);
        }
    }

    public ZonaCoberturaEntity findById(long id) {
        String query = "SELECT * FROM zonas_cobertura WHERE zona_id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(ZonaCoberturaEntity.class);
        }
    }
    public void save(ZonaCoberturaEntity zonaCobertura){
        String sql = "INSERT INTO zonas_cobertura (zona_id, nombre, geom) " +
                "VALUES (:zona_id, :nombre, :geom)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql, true)
                    .addParameter("zona_id", zonaCobertura.getZona_id())
                    .addParameter("nombre", zonaCobertura.getNombre())
                    .addParameter("geom", zonaCobertura.getGeom())
                    .executeUpdate();
            con.commit();
        }
    }
    public void update(ZonaCoberturaEntity zonaCobertura){
        String sql = "UPDATE zonas_cobertura SET nombre: nombre, geom: geom " +
                "WHERE zona_id = :zona_id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("zona_id", zonaCobertura.getZona_id())
                    .addParameter("nombre", zonaCobertura.getNombre())
                    .addParameter("geom", zonaCobertura.getGeom())
                    .executeUpdate();
        }
    }
    public void deleteById(long zona_id){
        String sql ="DELETE FROM zonas_cobertura WHERE zona_id = :zona_id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("zona_id", zona_id)
                    .executeUpdate();
        }
    }
}
