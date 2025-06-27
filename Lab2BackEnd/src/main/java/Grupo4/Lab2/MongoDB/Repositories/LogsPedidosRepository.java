package Grupo4.Lab2.MongoDB.Repositories;

public interface LogsPedidosRepository {



    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    int countPedidosLog10Minutos();
}
