package Grupo4.Lab2.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class DetallePedidosRepository {

    private final Sql2o sql2o;

    @Autowired
    public DetallePedidosRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
}
