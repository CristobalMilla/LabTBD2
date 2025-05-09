package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.CalificacionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class CalificacionRepository {

    private final Sql2o sql2o;

    @Autowired
    public CalificacionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public CalificacionEntity findById(long idCalificacion) {
        try (Connection conn = sql2o.open()) {
            CalificacionEntity calificacion;
            String query = "SELECT * FROM calificaciones WHERE calificacion_id = :idCalificacion";
            calificacion = conn.createQuery(query)
                    .addParameter("idCalificacion", idCalificacion)
                    .executeAndFetchFirst(CalificacionEntity.class);
            return calificacion;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<CalificacionEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<CalificacionEntity> calificaciones;
            String query = "SELECT * FROM calificaciones";
            calificaciones = conn.createQuery(query)
                    .executeAndFetch(CalificacionEntity.class);
            return calificaciones;
        }
        catch (Exception e){
            return null;
        }
    }

    public void save(CalificacionEntity calificacion) {
        try (Connection conn = sql2o.open()) {
            String query = "INSERT INTO clientes (pedido_id, puntuacion, comentario) " +
                    "VALUES (:pedido_id, :puntuacion, :comentario)";
            long calificacion_id = conn.createQuery(query)
                    .addParameter("pedido_id", calificacion.getPedido_id())
                    .addParameter("puntuacion", calificacion.getPuntuacion())
                    .addParameter("comentario", calificacion.getComentario())
                    .executeUpdate()
                    .getKey(Long.class);
            calificacion.setCalificacion_id(calificacion_id);
        }
        catch (Exception e){
            System.out.println("Error al guardar la calificacion");
        }
    }

    public void update(CalificacionEntity calificacion) {
        try (Connection conn = sql2o.open()) {
            String query = "UPDATE calificaciones SET pedido_id = :pedido_id, puntuacion = :puntuacion, " +
                           "comentario = :comentario WHERE calificacion_id = :idCalificacion";
            conn.createQuery(query)
                    .addParameter("pedido_id", calificacion.getPedido_id())
                    .addParameter("puntuacion", calificacion.getPuntuacion())
                    .addParameter("comentario", calificacion.getComentario())
                    .addParameter("idCalificacion", calificacion.getCalificacion_id())
                    .executeUpdate();
        }
        catch (Exception e){
            System.out.println("Error al actualizar la calificacion");
        }
    }

    public void deleteById(long idCalificacion) {
        try (Connection conn = sql2o.open()) {
            String query = "DELETE FROM calificaciones WHERE calificacion_id = :idCalificacion";
            conn.createQuery(query)
                    .addParameter("idCalificacion", idCalificacion)
                    .executeUpdate();
        }
        catch (Exception e){
            System.out.println("Error al eliminar la calificacion");
        }
    }
}
