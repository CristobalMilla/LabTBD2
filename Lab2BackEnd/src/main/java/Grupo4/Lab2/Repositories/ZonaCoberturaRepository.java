package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.ClienteEntity;
import Grupo4.Lab2.Entities.ZonaCoberturaEntity;
import Grupo4.Lab2.Repositories.ClienteRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class ZonaCoberturaRepository {
    @Autowired
    private final Sql2o sql2o;
    private final GeometryFactory geometryFactory;
    private final WKTReader wktReader;
    @Autowired
    public ZonaCoberturaRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.geometryFactory = new GeometryFactory();
        this.wktReader = new WKTReader();
    }

    private ZonaCoberturaEntity mapToZonaCoberturaEntity(Map<String, Object> row) {
        ZonaCoberturaEntity zona = new ZonaCoberturaEntity();
        zona.setZona_id((Long) row.get("zona_id"));
        zona.setNombre((String) row.get("nombre"));
        String wkt = (String) row.get("geom_wkt");
        if (wkt != null) {
            try {
                Polygon polygon = (Polygon) wktReader.read(wkt);
                polygon.setSRID(4326);
                zona.setGeom(polygon);
            } catch (ParseException e) {
                throw new RuntimeException("Error al parsear la geometría: " + e.getMessage(), e);
            }
        }
        return zona;
    }
    private String polygonToWKT(Polygon polygon) {
        if (polygon == null) {
            return null;
        }
        return polygon.toText();
    }

    public List<ZonaCoberturaEntity> findAll() {
        String query = "SELECT zona_id, nombre,  ST_AsText(geom) as geom_wkt FROM zonas_cobertura";
        try (Connection con = sql2o.open()) {
            List<Map<String, Object>> results = con.createQuery(query)
                    .executeAndFetchTable()
                    .asList();
            return results.stream().map(this::mapToZonaCoberturaEntity).toList();
        }
    }

    public ZonaCoberturaEntity findById(long id) {
        String query = "SELECT zona_id, nombre,  ST_AsText(geom) as geom_wkt FROM zonas_cobertura WHERE zona_id = :id";
        try (Connection con = sql2o.open()) {
            List<Map<String, Object>> results = con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchTable()
                    .asList();
            if(results.isEmpty()){
                return null;
            }
            else{
                return mapToZonaCoberturaEntity(results.get(0));
            }
        }
    }
    public ZonaCoberturaEntity save(ZonaCoberturaEntity zonaCobertura){
        String sql = "INSERT INTO zonas_cobertura (zona_id, nombre, geom) " +
                "VALUES (:zona_id, :nombre, ST_GeomFromText(:geom, 4326))" +
                "RETURNING zona_id";
        try (Connection con = sql2o.open()) {
            String wkt = polygonToWKT(zonaCobertura.getGeom());
            Integer generatedId = con.createQuery(sql)
                            .addParameter("geom", wkt)
                    .addParameter("nombre", zonaCobertura.getNombre())
                    .executeAndFetchFirst(Integer.class);
            zonaCobertura.setZona_id(Long.valueOf(generatedId));
            return zonaCobertura;
        }
    }
    public void update(ZonaCoberturaEntity zonaCobertura){
        String sql = "UPDATE zonas_cobertura SET nombre: nombre, geom = STGeomFromText(:geom, 4326) " +
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

    // Query 8
    // Detectar zonas con alta densidad de pedidos mediante agregación de puntos.
     // cambiar por un dto
    public List<ZonaCoberturaEntity> getZonasConAltaDensidad(){
        try (Connection conn = sql2o.open()) {
            String query = "WITH agrupacion_pedidos AS ( " +
                        "SELECT z.zona_id, ST_Collect(p.punto_final) AS puntos_agrupados_x_zona " +
                        "FROM zonas_cobertura z " +
                        "INNER JOIN pedidos p ON ST_Within(p.punto_final, z.geom) " +
                        "GROUP BY z.zona_id) " +
                    "SELECT ap.zona_id, (ST_NPoints(ap.puntos_agrupados_x_zona)/(ST_Area(geom::geography)/1000000)) AS densidad_pedidos_x_km2 " +
                    "FROM agrupacion_pedidos ap " +
                    "INNER JOIN zonas_cobertura z ON ap.zona_id = z.zona_id " +
                    "GROUP BY ap.zona_id, ap.puntos_agrupados_x_zona, z.geom " +
                    "HAVING (ST_NPoints(ap.puntos_agrupados_x_zona)/(ST_Area(geom::geography)/1000000)) > 1"; // cambiar por alguna densidad
            return conn.createQuery(query).executeAndFetch(ZonaCoberturaEntity.class);
        }
        catch (Exception e){
            System.out.println("Error al obtener las zonas con alta densidad");
            return null;
        }
    }

    //Consulta especial 2
    //Determinar si un cliente se encuentra dentro de una zona de cobertura
    //Se devolvera la lista zonas de cobertura en las que el cliente se encuentra
    public List<ZonaCoberturaEntity> findZonasCoberturaByClienteId(long cliente_id){
        String sql = "SELECT zc.zona_id, zc.nombre, ST_AsText(zc.geom) as geom_wkt " +
                "FROM zona_coberturas zc, cliente c" +
                "WHERE c.cliente_id = cliente_id = :cliente_id " +
                        "AND ST_Contains(zc.geom, c.ubicacion)";
        try (Connection con = sql2o.open()) {
            List<Map<String, Object>> results = con.createQuery(sql)
                    .addParameter("cliente_id", cliente_id)
                    .executeAndFetchTable()
                    .asList();
            return results.stream().map(this::mapToZonaCoberturaEntity).toList();
        }
    }
}
