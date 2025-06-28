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
}