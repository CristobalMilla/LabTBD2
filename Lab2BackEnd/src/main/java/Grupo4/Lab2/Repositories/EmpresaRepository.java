package Grupo4.Lab2.Repositories;


import Grupo4.Lab2.Entities.EmpresaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
@Repository
public class EmpresaRepository {

    private final Sql2o sql2o;

    @Autowired
    public EmpresaRepository(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public List<EmpresaEntity> findAll(){
        String sql = "SELECT * FROM empresas";
        try (Connection con = sql2o.open()){
            List<EmpresaEntity> empresas = con.createQuery(sql)
                    .executeAndFetch(EmpresaEntity.class);
            return empresas;
        }
    }

    public EmpresaEntity findById(long empresa_id){
        String sql = "SELECT * FROM empresas WHERE empresa_id = :id";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("empresa_id", empresa_id)
                    .executeAndFetchFirst(EmpresaEntity.class);
        }
    }
    //AGREGAR COLUMA UBICACION
    public void save(EmpresaEntity empresa){
        String sql = "INSERT INTO empresas (nombre, direccion, tipo_servicio) VALUES (:nombre, :direccion, :tipo_servicio)";
        try (Connection con = sql2o.beginTransaction()){
            Long generatedId = con.createQuery(sql, true)
                    .addParameter("nombre", empresa.getNombre())
                    .addParameter("direccion", empresa.getDireccion())
                    .addParameter("tipo_servicio", empresa.getTipoServicio())
                    .executeUpdate()
                    .getKey(Long.class);
            empresa.setEmpresaId(generatedId);
            con.commit();
        }
    }
    //AGREGAR COLUMA UBICACION
    public void update(EmpresaEntity empresa) {
        String sql = "UPDATE empresas SET nombre = :nombre, direccion = :direccion, tipo_servicio = :tipo_servicio WHERE empresa_id = :empresa_id";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql)
                    .addParameter("nombre", empresa.getNombre())
                    .addParameter("direccion", empresa.getDireccion())
                    .addParameter("tipo_servicio", empresa.getTipoServicio())
                    .addParameter("empresa_id", empresa.getEmpresaId())
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

}
