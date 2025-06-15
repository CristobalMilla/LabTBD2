package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.DTO.RepartidorEntregaDTO;
import Grupo4.Lab2.DTO.RepartidorVistaDTO;
import Grupo4.Lab2.Entities.RepartidorEntity;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RepartidorRepository {

    private final Sql2o sql2o;
    private final GeometryFactory geometryFactory;

    @Autowired
    public RepartidorRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    }

    private RepartidorEntity mapRowToRepartidorEntity(Map<String, Object> row) {
        if (row == null) {
            return null;
        }
        RepartidorEntity repartidor = new RepartidorEntity();
        repartidor.setRepartidor_id((Long) row.get("repartidor_id"));
        repartidor.setNombre((String) row.get("nombre"));
        repartidor.setTelefono((String) row.get("telefono"));
        repartidor.setDisponible((Boolean) row.get("disponible"));
        String ubicacionWkt = (String) row.get("ubicacion_wkt");

        if (ubicacionWkt != null && !ubicacionWkt.isEmpty()) {
            try {
                WKTReader reader = new WKTReader(this.geometryFactory);
                repartidor.setUbicacion_actual((Point) reader.read(ubicacionWkt));
            } catch (ParseException e) {
                System.err.println("Error al parsear WKT de ubicación: " + e.getMessage());
                repartidor.setUbicacion_actual(null);
            }
        } else {
            repartidor.setUbicacion_actual(null);
        }
        return repartidor;
    }

    public List<RepartidorEntity> findAll(){
        String sql = "SELECT repartidor_id, nombre, telefono, disponible, ST_AsText(ubicacion_actual) as ubicacion_wkt FROM repartidores";
        try(var con = sql2o.open()){
            List<Map<String, Object>> rows = con.createQuery(sql).executeAndFetchTable().asList();
            return rows.stream()
                    .map(this::mapRowToRepartidorEntity)
                    .collect(Collectors.toList());
        }
    }

    public RepartidorEntity findById(long id){
        String sql = "SELECT repartidor_id, nombre, telefono, disponible, ST_AsText(ubicacion_actual) as ubicacion_wkt FROM repartidores WHERE repartidor_id = :id";
        try(var con = sql2o.open()){
            List<Map<String, Object>> rows = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable().asList();
            return mapRowToRepartidorEntity(rows.get(0));
        }
    }

    public void save(RepartidorEntity repartidor){
        String ubicacionWkt = (repartidor.getUbicacion_actual() != null) ? repartidor.getUbicacion_actual().toText() : null;
        String sql = "INSERT INTO repartidores (repartidor_id, nombre, telefono, disponible, ubicacion_actual) " +
                "VALUES (:repartidor_id, :nombre, :telefono, :disponible, ST_GeomFromText(:ubicacionWkt, 4326))";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("repartidor_id", repartidor.getRepartidor_id())
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("disponible", repartidor.getDisponible())
                    .addParameter("ubicacionWkt", ubicacionWkt)
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

    public void update(RepartidorEntity repartidor){
        String ubicacionWkt = (repartidor.getUbicacion_actual() != null) ? repartidor.getUbicacion_actual().toText() : null;
        String sql = "UPDATE repartidores SET nombre = :nombre, telefono = :telefono, " +
                "disponible = :disponible, ubicacion_actual = ST_GeomFromText(:ubicacionWkt, 4326) WHERE repartidor_id = :repartidor_id";
        try (var con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("repartidor_id", repartidor.getRepartidor_id())
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("disponible", repartidor.getDisponible())
                    .addParameter("ubicacionWkt", ubicacionWkt)
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
