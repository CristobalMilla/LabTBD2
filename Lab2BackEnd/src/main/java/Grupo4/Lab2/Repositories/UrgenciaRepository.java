package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.UrgenciaEntity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UrgenciaRepository {

    private final Sql2o sql2o;

    @Autowired
    public UrgenciaRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    // Busca una urgencia por su ID
    public Optional<UrgenciaEntity> findById(long id) {
        String sql = "SELECT * FROM urgencias WHERE urgencia_id = :id";
        try (Connection con = sql2o.open()) {
            UrgenciaEntity urgencia = con.createQuery(sql)
                                         .addParameter("id", id)
                                         .executeAndFetchFirst(UrgenciaEntity.class);
            return Optional.ofNullable(urgencia);
        }
    }

    // Guarda una nueva urgencia
    public void save(UrgenciaEntity urgencia) {
        String sql = "INSERT INTO urgencias (pedido_id, nivel) " +
                     "VALUES (:pedido_id, :nivel)";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
               .addParameter("pedido_id", urgencia.getPedido_id())
               .addParameter("nivel", urgencia.getNivel())
               .executeUpdate();
            con.commit();
        }
    }

    // Obtiene todas las urgencias
    public List<UrgenciaEntity> findAll() {
        String sql = "SELECT * FROM urgencias";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                      .executeAndFetch(UrgenciaEntity.class);
        }
    }

    // Elimina una urgencia por su ID
    public void deleteById(long id) {
        String sql = "DELETE FROM urgencias WHERE urgencia_id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
               .addParameter("id", id)
               .executeUpdate();
        }
    }
}