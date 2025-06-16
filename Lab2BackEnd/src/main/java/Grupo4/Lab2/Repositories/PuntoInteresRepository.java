package Grupo4.Lab2.Repositories;


import Grupo4.Lab2.Entities.PuntoInteresEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class PuntoInteresRepository {

    @Autowired
    private final Sql2o sql2o;

    @Autowired
    public PuntoInteresRepository(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public PuntoInteresEntity findById(long idInteres) {
        try (Connection conn = sql2o.open()) {
            PuntoInteresEntity interes;
            String query = "SELECT interes_id, nombre, ST_AsText(ubicacion) AS ubicacionWkt FROM puntos_interes WHERE interes_id = :idInteres";
            interes = conn.createQuery(query)
                    .addParameter("idInteres", idInteres)
                    .executeAndFetchFirst(PuntoInteresEntity.class);
            return interes;
        } catch (Exception e){
            System.err.println("Error al obtener el punto de interes de id : "+ idInteres +".\n"+ e.getMessage());
            return null;
        }
    }

    public List<PuntoInteresEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<PuntoInteresEntity> interes;
            String query = "SELECT interes_id, nombre, ST_AsText(ubicacion) FROM puntos_interes";
            interes = conn.createQuery(query)
                    .executeAndFetch(PuntoInteresEntity.class);
            return interes;
        } catch (Exception e){
            System.err.println("Error al obtener los puntos de interes.\n " + e.getMessage());
            return null;
        }
    }

    public void save(PuntoInteresEntity interes) {
        String sql = "INSERT INTO puntos_interes (interes_id, nombre, ubicacion) " +
                "VALUES (:interes_id, :nombre, ST_GeomFromText(:ubicacion, 4326))";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
                    .addParameter("empresa_id", interes.getInteres_id())
                    .addParameter("nombre", interes.getNombre())
                    .addParameter("ubicacion", interes.getUbicacionWkt())
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
                    .addParameter("ubicacion", interes.getUbicacionWkt())
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
        String sql = "SELECT interes_id, nombre, ST_AsText(ubicacion) AS ubicacionWkt FROM puntos_interes " +
                "WHERE ST_DWithin(ubicacion, ST_GeomFromText(:punto, 4326), 1000)";
        try (Connection con = sql2o.open()) {
            List<PuntoInteresEntity> interes;
            interes = con.createQuery(sql)
                    .addParameter("punto", punto.toText())
                    .executeAndFetch(PuntoInteresEntity.class);
            return interes;
        } catch (Exception e){
            System.err.println("Error al obtener los puntos de interes cercanos.\n " + e.getMessage());
            return null;
        }
    }

}
