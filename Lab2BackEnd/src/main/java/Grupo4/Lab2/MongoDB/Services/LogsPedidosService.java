package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.EventoPedido;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import Grupo4.Lab2.MongoDB.Repositories.LogsPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogsPedidosService {

    private final LogsPedidosRepository repo;

    @Autowired
    public LogsPedidosService(LogsPedidosRepository repo) {
        this.repo = repo;
    }

    public List<LogsPedidos> getAllLogsPedidos(){ return repo.findAll();}

    public LogsPedidos getLogById(long id){ return repo.findById(id);}

    public LogsPedidos createLog(Long id_pedido){
        LocalDateTime timestamp = LocalDateTime.now();
        String mensaje = "creado";
        EventoPedido evento = new EventoPedido(mensaje, timestamp);
        List<EventoPedido> eventos = new ArrayList<>();
        eventos.add(evento);
        LogsPedidos log = new LogsPedidos(id_pedido, eventos);
        return repo.save(log);}

    public LogsPedidos updateLog(long id, LogsPedidos log){ return repo.update(id, log);}

    public void deleteLog(long id){ repo.delete(id);}
}
