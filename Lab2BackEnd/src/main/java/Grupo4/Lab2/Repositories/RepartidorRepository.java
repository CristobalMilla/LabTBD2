package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.DTO.RepartidorEntregaDTO;
import Grupo4.Lab2.DTO.RepartidorVistaDTO;
import Grupo4.Lab2.Entities.RepartidorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RepartidorRepository {

    private final Sql2o sql2o;

    @Autowired
    public RepartidorRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<RepartidorEntity> findAll(){
        String sql = "SELECT * FROM repartidores";
        try(var con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(RepartidorEntity.class);
        }
    }

    public RepartidorEntity findById(long id){
        String sql = "SELECT * FROM repartidores WHERE repartidor_id = :id";
        try(var con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(RepartidorEntity.class);
        }
    }
    //AGREGAR COLUMA UBICACION
    public void save(RepartidorEntity repartidor){
        String sql = "INSERT INTO repartidores (repartidor_id, nombre, telefono, disponible) " +
                "VALUES (:repartidor_id, :nombre, :telefono, :disponible)";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("repartidor_id", repartidor.getRepartidor_id())
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("disponible", repartidor.getDisponible())
                    .executeUpdate();
        }
    }
    public void deleteById(long id){
        String sql = "DELETE FROM repartidores WHERE repartidor_id = :id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
    //AGREGAR COLUMA UBICACION
    public void update(RepartidorEntity repartidor){
        String sql = "UPDATE repartidores SET nombre = :nombre, telefono = :telefono, " +
                "disponible = :disponible WHERE repartidor_id = :repartidor_id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("repartidor_id", repartidor.getRepartidor_id())
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("disponible", repartidor.getDisponible())
                    .executeUpdate();
        }
    }

    public List<RepartidorEntity> mejoresRepartidores(){
        String sql = """
                SELECT r.repartidor_id as repartidor_id, r.nombre as nombre, r.telefono as telefono, r.disponible as disponible\s
                FROM (repartidores as r
                INNER JOIN pedidos as p ON r.repartidor_id = p.repartidor_id)\s
                FULL JOIN calificaciones as c ON p.pedido_id = c.pedido_id
                WHERE p.estado = 'entregado'
                GROUP BY r.repartidor_id
                ORDER BY COUNT(p.pedido_id) DESC, AVG(c.puntuacion) DESC
                LIMIT 3;""";
        try(var con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(RepartidorEntity.class);
        }
    }

    public List<RepartidorEntregaDTO> tiemposRepartidoresEntregaMinutos(){
        String sql = """
        SELECT r.nombre as repartidor, AVG(EXTRACT(EPOCH FROM p.fecha_entrega - p.fecha) / 86400) as avg_tiempo_entrega_min 
                                                               FROM repartidores as r
                                                               INNER JOIN pedidos as p ON r.repartidor_id = p.repartidor_id
                                                               WHERE p.estado = 'entregado'
                                                               GROUP BY r.repartidor_id
    """;
        try (var con = sql2o.open()) {
            return con.createQuery(sql)
                    .addColumnMapping("avg_tiempo_entrega_min", "avgTiempoEntregaMin")
                    .executeAndFetch(RepartidorEntregaDTO.class);
        }
    }

    public List<RepartidorVistaDTO> repartidoresVista(){
        String sql = """
        SELECT * FROM repartidores_desempenios
        """;
        try (var con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(RepartidorVistaDTO.class);
        }

    }

    public double calcularDistanciaMensual(long repartidorId) {
        String sql = ""
          + "SELECT COALESCE(SUM(ST_Length(ruta_estimada::geography)),0) "
          + "FROM pedidos "
          + "WHERE repartidor_id = :id "
          +   "AND fecha_entrega >= now() - INTERVAL '1 month'";
        try (var con = sql2o.open()) {
            return con.createQuery(sql)
                      .addParameter("id", repartidorId)
                      .executeScalar(Double.class);
        }
    }
}
