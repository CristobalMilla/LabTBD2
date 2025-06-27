package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Repositories.LogsPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsPedidosService {
    private final LogsPedidosRepository logsPedidosRepository;

    @Autowired
    public LogsPedidosService(LogsPedidosRepository logsPedidosRepository) {
        this.logsPedidosRepository = logsPedidosRepository;
    }


    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    public int countHistoriales10Minutos() {
        return logsPedidosRepository.countPedidosLog10Minutos();
    }
}
