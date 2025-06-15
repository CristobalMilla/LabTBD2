package Grupo4.Lab2.Repositories;


import Grupo4.Lab2.Entities.EmpresaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.awt.*;
import java.util.List;
@Repository
public class EmpresaRepository {

    private final Sql2o sql2o;

    @Autowired
    public EmpresaRepository(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public List<EmpresaEntity> findAll(){
        String sql =
                "SELECT " +
                "  empresa_id, " +
                "  nombre, " +
                "  direccion, " +
                "  tipo_servicio, " +
                "  ST_AsText(ubicacion) AS ubicacionWkt " +   // <-- convertimos a WKT
                "FROM empresas";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(EmpresaEntity.class);
        }
    }

    public EmpresaEntity findById(long id){
        String sql =
                "SELECT " +
                "  empresa_id, " +
                "  nombre, " +
                "  direccion, " +
                "  tipo_servicio, " +
                "  ST_AsText(ubicacion) AS ubicacionWkt " +   // <-- idem
                "FROM empresas WHERE empresa_id = :id";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(EmpresaEntity.class);
        }
    }

    public void save(EmpresaEntity e){
        String sql =
                "INSERT INTO empresas (nombre, direccion, tipo_servicio, ubicacion) " +
                "VALUES (:nombre, :direccion, :tipo_servicio, ST_GeomFromText(:ubicacionWkt,4326))";
        try (Connection con = sql2o.beginTransaction()){
            Long key = con.createQuery(sql, true)
                    .addParameter("nombre", e.getNombre())
                    .addParameter("direccion", e.getDireccion())
                    .addParameter("tipo_servicio", e.getTipoServicio())
                    .addParameter("ubicacionWkt", e.getUbicacionWkt())
                    .executeUpdate()
                    .getKey(Long.class);
            e.setEmpresaId(key);
            con.commit();
        }
    }

    public void update(EmpresaEntity e){
        String sql =
                "UPDATE empresas SET " +
                "  nombre = :nombre, " +
                "  direccion = :direccion, " +
                "  tipo_servicio = :tipo_servicio, " +
                "  ubicacion = ST_GeomFromText(:ubicacionWkt,4326) " +
                "WHERE empresa_id = :empresa_id";
        try (Connection con = sql2o.beginTransaction()){
            con.createQuery(sql)
                    .addParameter("nombre", e.getNombre())
                    .addParameter("direccion", e.getDireccion())
                    .addParameter("tipo_servicio", e.getTipoServicio())
                    .addParameter("ubicacionWkt", e.getUbicacionWkt())
                    .addParameter("empresa_id", e.getEmpresaId())
                    .executeUpdate();
            con.commit();
        }
    }

    public void deleteById(long empresa_id){
        String sql = "DELETE FROM empresas WHERE empresa_id = :empresa_id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("empresa_id", empresa_id)
                    .executeUpdate();
        }
    }

    /*
    1. Encontrar los 5 puntos de entrega más
    cercanos a una farmacia o empresa asociada
     */
    public List<Point> findNearby(long empresa_id){
        String sql = "SELECT p.punto_final AS punto_cercano \n" +
                "FROM pedidos p\n" +
                "JOIN empresas e on e.empresa_id = p.empresa_id\n" +
                "WHERE e.empresa_id =:empresa_id\n" +
                "ORDER BY ST_Distance(p.punto_final::geography, e.ubicacion::geography) ASC\n" +
                "LIMIT 5";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("empresa_id", empresa_id)
                    .executeAndFetch(Point.class);
        }
    }

}
