package Grupo4.Lab2.Repositories;


import Grupo4.Lab2.Entities.PuntoInteresEntity;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class PuntoInteresRepository {

    @Autowired
    private final Sql2o sql2o;
    private final GeometryFactory geometryFactory;
    private final WKTReader wktReader;

    @Autowired
    public PuntoInteresRepository(Sql2o sql2o){
        this.sql2o = sql2o;
        this.geometryFactory = new GeometryFactory();
        this.wktReader = new WKTReader(this.geometryFactory);
    }

    private PuntoInteresEntity mapToPuntoInteresEntity(Map<String, Object> row) {
        PuntoInteresEntity interes = new PuntoInteresEntity();
        interes.setInteres_id((Long) row.get("interes_id"));
        interes.setNombre((String) row.get("nombre"));
        String wkt = (String) row.get("ubicacion");
        if (wkt != null) {
            try {
                Point punto = (Point) wktReader.read(wkt);
                punto.setSRID(4326);
                interes.setUbicacion(punto);
            } catch (ParseException e) {
                throw new RuntimeException("Error al parsear la geometría: " + e.getMessage(), e);
            }
        }

        return interes;
    }

    private String pointToWKT(Point punto) {
        if (punto == null) {
            return null;
        } else {
            return punto.toText();
        }
    }

    public PuntoInteresEntity findById(long idInteres) {
        try (Connection conn = sql2o.open()) {
            List<Map<String, Object>> interes;
            String query = "SELECT interes_id, nombre, ST_AsText(ubicacion) FROM puntos_interes WHERE interes_id = :idInteres";
            interes = conn.createQuery(query)
                    .addParameter("idInteres", idInteres)
                    .executeAndFetchTable()
                    .asList();
            return mapToPuntoInteresEntity(interes.get(0));
        } catch (Exception e){
            System.err.println("Error al obtener el punto de interes de id : "+ idInteres +".\n"+ e.getMessage());
            return null;
        }
    }

    public List<PuntoInteresEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<Map<String, Object>> interes;
            String query = "SELECT interes_id, nombre, ST_AsText(ubicacion) FROM puntos_interes";
            interes = conn.createQuery(query)
                    .executeAndFetchTable()
                    .asList();
            return interes.stream().map(this::mapToPuntoInteresEntity).toList();
        } catch (Exception e){
            System.err.println("Error al obtener los puntos de interes.\n " + e.getMessage());
            return null;
        }
    }

    public void save(PuntoInteresEntity interes) {
        String sql = "INSERT INTO puntos_interes (interes_id, nombre, ubicacion) " +
                "VALUES (:interes_id, :nombre, ST_GeomFromText(:ubicacion, 4326))";
        try (Connection con = sql2o.beginTransaction()) {
            String ubi = pointToWKT(interes.getUbicacion());
            con.createQuery(sql, true)
                    .addParameter("empresa_id", interes.getInteres_id())
                    .addParameter("nombre", interes.getNombre())
                    .addParameter("descripcion", interes.getUbicacion())
                    .addParameter("ubicacion", ubi)
                    .executeUpdate();
            con.commit();
        } catch (Exception e){
            System.err.println("Error al guardar el punto de interes de id : "+ interes.getInteres_id() +".\n"+ e.getMessage());
        }
    }
    public void update(PuntoInteresEntity interes) {
        String sql = "UPDATE puntos_interes SET nombre =:nombre, ubicacion = ST_GeomFromText(:ubicacion, 4326) " +
                "WHERE interes_id = :interes_id ";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
                    .addParameter("interes_id", interes.getInteres_id())
                    .addParameter("nombre", interes.getNombre())
                    .addParameter("ubicacion", pointToWKT(interes.getUbicacion()))
                    .addParameter("descripcion", interes.getUbicacion())
                    .executeUpdate();
            con.commit();
        } catch (Exception e){
            System.err.println("Error al actualizar el punto de interes de id : "+ interes.getInteres_id() +".\n"+ e.getMessage());
        }
    }

    public void deleteById(long interes_id) {
        try (Connection conn = sql2o.open()) {
            String query = "DELETE FROM puntos_interes WHERE interes_id = :interes_id";
            conn.createQuery(query)
                    .addParameter("interes_id", interes_id)
                    .executeUpdate();
        } catch (Exception e){
            System.err.println("Error al borrar el punto de interes de id : "+ interes_id +".\n"+ e.getMessage());
        }
    }

    /*
    Actividad opcional 3: Crear una tabla de puntos de interés
    cercanos (hospitales, centros logísticos, etc.) y consultarlos con ST_DWithin.
     */
    public List<PuntoInteresEntity> findNearby(Point punto) {
        String sql = "SELECT interes_id, nombre, ST_AsText(ubicacion) FROM puntos_interes " +
                "WHERE ST_DWithin(ubicacion, ST_GeomFromText(:punto, 4326), 1000)";
        try (Connection con = sql2o.open()) {
            List<Map<String, Object>> interes;
            interes = con.createQuery(sql)
                    .addParameter("punto", pointToWKT(punto))
                    .executeAndFetchTable()
                    .asList();
            return interes.stream().map(this::mapToPuntoInteresEntity).toList();
        } catch (Exception e){
            System.err.println("Error al obtener los puntos de interes cercanos.\n " + e.getMessage());
            return null;
        }
    }

}
