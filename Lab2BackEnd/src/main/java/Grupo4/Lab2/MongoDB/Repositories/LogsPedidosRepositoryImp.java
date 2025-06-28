package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.EventoPedido;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class LogsPedidosRepositoryImp implements LogsPedidosRepository {

    @Autowired
    private MongoDatabase database;

    private static final String COLLECTION_NAME = "logs_pedidos";

    private MongoCollection<LogsPedidos> getCollection() {
        return database.getCollection(COLLECTION_NAME, LogsPedidos.class);
    }

    @Override
    public List<LogsPedidos> findAll() {
        return getCollection().find().into(new java.util.ArrayList<>());
    }

    @Override
    public LogsPedidos findById(long id) {
        return getCollection().find(eq("id_pedido", id)).first();
    }

    @Override
    public LogsPedidos save(LogsPedidos log) {
        getCollection().insertOne(log);
        return log;
    }

    @Override
    public LogsPedidos update(long id, LogsPedidos log) {
        getCollection().replaceOne(eq("id_pedido", id), log);
        return log;
    }

    @Override
    public void delete(long id) {
        getCollection().deleteOne(eq("id_pedido", id));
    }

    @Override
    public long count(){
        return getCollection().countDocuments();
    }
    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    public int countPedidosLog10Minutos() {
        List<LogsPedidos> logsPedidos = getCollection().find().into(new ArrayList<>());
        int count = 0;
        for (LogsPedidos log : logsPedidos) {
            boolean pedidoTiene3Cambios = false;
            List<EventoPedido> eventos = log.getEventos();
            int totalEventos = eventos.size();
            for (int i=0; (i<totalEventos - 2); i++){
                Instant instante1 = eventos.get(i).getTimestamp();
                Instant instante2 = eventos.get(i+1).getTimestamp();
                long minutesDiference = Duration.between(instante1, instante2).toMinutes();
                if (minutesDiference<=10){
                    pedidoTiene3Cambios = true;
                    break;
                }
            }
            if(pedidoTiene3Cambios){
                count++;
                break;
            }
        }
        return count;
    }
}