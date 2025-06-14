package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.ProductoEntity;
import Grupo4.Lab2.Entities.PuntoInteresEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class PuntoInteresRepository {

    private final Sql2o sql2o;

    @Autowired
    public PuntoInteresRepository(Sql2o sql2o){ this.sql2o = sql2o;}

    public PuntoInteresEntity findById(long idInteres) {
        try (Connection conn = sql2o.open()) {
            PuntoInteresEntity interes;
            String query = "SELECT * FROM pedidos WHERE interes_id = :idInteres";
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
            List<PuntoInteresEntity> pedidos;
            String query = "SELECT * FROM puntos_interes";
            pedidos = conn.createQuery(query)
                    .executeAndFetch(PuntoInteresEntity.class);
            return pedidos;
        } catch (Exception e){
            System.err.println("Error al obtener los puntos de interes.\n " + e.getMessage());
            return null;
        }
    }

    public void save(PuntoInteresEntity interes) {
        String sql = "INSERT INTO puntos_interes (interes_id, nombre, ubicacion) " +
                "VALUES (:interes_id, :nombre, :ubicacion)";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
                    .addParameter("empresa_id", interes.getInteres_id())
                    .addParameter("nombre", interes.getNombre())
                    .addParameter("descripcion", interes.getUbicacion())
                    .executeUpdate();
            con.commit();
        } catch (Exception e){
            System.err.println("Error al guardar el punto de interes de id : "+ interes.getInteres_id() +".\n"+ e.getMessage());
        }
    }
    public void update(PuntoInteresEntity interes) {
        String sql = "UPDATE puntos_interes SET nombre =:nombre, ubicacion =:ubicacion " +
                "WHERE interes_id = :interes_id ";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql, true)
                    .addParameter("interes_id", interes.getInteres_id())
                    .addParameter("nombre", interes.getNombre())
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

}
