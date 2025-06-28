package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;

import java.util.List;

public interface LogsPedidosRepository {
    List<LogsPedidos> findAll();
    LogsPedidos findById(long id);
    LogsPedidos save(LogsPedidos log);
    LogsPedidos update(long id, LogsPedidos log);
    void delete(long id);
    long count();
    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    int countPedidosLog10Minutos();
}