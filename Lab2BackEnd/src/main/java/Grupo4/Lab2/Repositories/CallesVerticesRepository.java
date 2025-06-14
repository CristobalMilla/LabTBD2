package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.CallesVerticesEntity; 
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CallesVerticesRepository {

    private final Sql2o sql2o;

    @Autowired
    public CallesVerticesRepository(Sql2o sql2o) { 
        this.sql2o = sql2o;
    }

    public CallesVerticesEntity findById(long idParam) {
        String query = "SELECT " +
                       "id, cnt, chk, ein, eout, " +
                       "ST_AsText(the_geom) AS theGeomWkt " +
                       "FROM calles_cleaned_vertices_pgr " + 
                       "WHERE id = :idParam";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("idParam", idParam)
                    .executeAndFetchFirst(CallesVerticesEntity.class); 
        } catch (Exception e) {
            System.out.println("Error en CallesVerticesRepository.findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<CallesVerticesEntity> findAll() { 
        String query = "SELECT " +
                       "id, cnt, chk, ein, eout, " +
                       "ST_AsText(the_geom) AS theGeomWkt " +
                       "FROM calles_cleaned_vertices_pgr"; 
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .executeAndFetch(CallesVerticesEntity.class); 
        } catch (Exception e) {
            System.out.println("Error en CallesVerticesRepository.findAll: " + e.getMessage()); 
            e.printStackTrace();
            return null;
        }
    }
}
