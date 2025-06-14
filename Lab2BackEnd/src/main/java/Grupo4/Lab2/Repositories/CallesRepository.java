package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.CallesEntity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CallesRepository {
    private final Sql2o sql2o;

    @Autowired
    public CallesRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public CallesEntity findById(long cleanedStreetIdParam) {
        String query = "SELECT " +
                       "cleaned_street_id AS cleanedStreetId, " +
                       "street_id AS streetId, " +
                       "fid, " +
                       "shape_leng AS shapeLeng, " +
                       "st_length_ AS stLength, " +
                       "nom_ruta AS nomRuta, " +
                       "comuna, " +
                       "ST_AsText(geom) AS geomWkt, " + // Se convierte geom a WKT
                       "source, " +
                       "target, " +
                       "cost " +
                       "FROM calles_cleaned " +
                       "WHERE cleaned_street_id = :cleanedStreetIdParam";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("cleanedStreetIdParam", cleanedStreetIdParam)
                    .executeAndFetchFirst(CallesEntity.class);
        } catch (Exception e) {
            System.out.println("Error en findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<CallesEntity> findAll() {
        String query = "SELECT " +
                       "cleaned_street_id AS cleanedStreetId, " +
                       "street_id AS streetId, " +
                       "fid, " +
                       "shape_leng AS shapeLeng, " +
                       "st_length_ AS stLength, " +
                       "nom_ruta AS nomRuta, " +
                       "comuna, " +
                       "ST_AsText(geom) AS geomWkt, " +  // convierte geom a WKT
                       "source, " +
                       "target, " +
                       "cost " +
                       "FROM calles_cleaned";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .executeAndFetch(CallesEntity.class);
        } catch (Exception e) {
            System.out.println("Error en findAll: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void save(CallesEntity calle) {
        String query = "INSERT INTO calles_cleaned (street_id, fid, shape_leng, st_length_, nom_ruta, comuna, geom, source, target, cost) " +
                       "VALUES (:streetId, :fid, :shapeLeng, :stLength, :nomRuta, :comuna, ST_GeomFromText(:geomText, 4326), :source, :target, :cost)"; // Asumiendo que geom se pasa como WKT
        try (Connection conn = sql2o.open()) {
            // Si geom es un objeto LineString, necesitas convertirlo a un formato que PostgreSQL entienda (WKT o EWKB)
            // o asegurarte que tu convertidor de Sql2o para JTS esté funcionando.
            // Por simplicidad, si pasas geom como WKT String, puedes usar ST_GeomFromText.
            // Si el objeto LineString ya está siendo manejado por un convertidor JTS de Sql2o, el :geom directo podría funcionar.
            Integer id = conn.createQuery(query, true)
                    .bind(calle) // Puedes usar bind para mapear el objeto directamente si los nombres coinciden
                                 // o seguir con addParameter individualmente.
                    // .addParameter("streetId", calle.getStreetId())
                    // .addParameter("fid", calle.getFid())
                    // .addParameter("shapeLeng", calle.getShapeLeng())
                    // .addParameter("stLength", calle.getStLength())
                    // .addParameter("nomRuta", calle.getNomRuta())
                    // .addParameter("comuna", calle.getComuna())
                    // .addParameter("geom", calle.getGeom()) // Esto requiere un convertidor JTS
                    // .addParameter("source", calle.getSource())
                    // .addParameter("target", calle.getTarget())
                    // .addParameter("cost", calle.getCost())
                    .executeUpdate()
                    .getKey(Integer.class); // Cambiado a Integer.class
            calle.setCleanedStreetId(id);
        } catch (Exception e) {
            System.out.println("Error en save: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(CallesEntity calle) {
        String query = "UPDATE calles_cleaned SET street_id = :streetId, fid = :fid, shape_leng = :shapeLeng, " +
                       "st_length_ = :stLength, nom_ruta = :nomRuta, comuna = :comuna, geom = :geom, source = :source, " + // Asume convertidor JTS para :geom
                       "target = :target, cost = :cost WHERE cleaned_street_id = :cleanedStreetId";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .bind(calle) // Usar bind es más conciso
                    // .addParameter("streetId", calle.getStreetId())
                    // ...otros parámetros...
                    // .addParameter("geom", calle.getGeom())
                    // .addParameter("cleanedStreetId", calle.getCleanedStreetId())
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteById(long cleanedStreetIdParam) {
        String query = "DELETE FROM calles_cleaned WHERE cleaned_street_id = :cleanedStreetIdParam";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("cleanedStreetIdParam", cleanedStreetIdParam)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en deleteById: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
