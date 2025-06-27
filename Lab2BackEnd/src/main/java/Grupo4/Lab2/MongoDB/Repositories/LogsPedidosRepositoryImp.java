package Grupo4.Lab2.MongoDB.Repositories;


import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogsPedidosRepositoryImp implements LogsPedidosRepository{
    @Autowired
    private MongoDatabase database;
    private static final String COLLECTION_NAME = "logs_pedidos";

    private MongoCollection<LogsPedidos> getCollection() {
        return database.getCollection(COLLECTION_NAME, LogsPedidos.class);
    }


    //Query 3:Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos.
    public int countPedidosLog10Minutos() {
        List<LogsPedidos> logsPedidos = getCollection().find().into(new ArrayList<>());
        int count = 0;
        for (LogsPedidos log : logsPedidos) {
            boolean pedidoTiene3Cambios = false;
            List<evento> eventos = log.getEvento();
            int totalEventos = eventos.size();
            for (int i=0; (i<totalEventos - 2); i++){
                Instant instante1 = eventos.get(i).getTiempo().toInstant();
                Instant instante2 = eventos.get(i+1).getTiempo().toInstant();
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
