package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.EventoPedido;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import Grupo4.Lab2.MongoDB.Repositories.LogsPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
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

    public LogsPedidos createLog(Long id_pedido, Instant timestamp){
        if (repo.findById(id_pedido) != null) {
            System.out.println("El pedido con id " + id_pedido + " ya existe.");
            return null;
        }
        String mensaje = "creado";
        EventoPedido evento = new EventoPedido(mensaje, timestamp);
        List<EventoPedido> eventos = new ArrayList<>();
        eventos.add(evento);
        LogsPedidos log = new LogsPedidos(id_pedido, eventos);
        return repo.save(log);}

    public LogsPedidos updateLog(long id, LogsPedidos log){ return repo.update(id, log);}

    public LogsPedidos cambiarEstado(long id, String estado, Instant timestamp){
        LogsPedidos log = repo.findById(id);
        if (log == null) {
            System.out.println("El pedido con id " + id + " no existe.");
            return null;
        }
        EventoPedido evento = new EventoPedido(estado, timestamp);
        List<EventoPedido> eventos = log.getEventos();
        eventos.add(evento);
        log.setEventos(eventos);
        return repo.update(id, log);
    }

    public void deleteLog(long id){ repo.delete(id);}

    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.

    public int countPedidosLog10Minutos() {
        return repo.countPedidosLog10Minutos();
    }
}