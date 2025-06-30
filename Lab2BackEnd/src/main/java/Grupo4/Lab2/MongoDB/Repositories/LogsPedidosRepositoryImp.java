package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.EventoPedido;
import Grupo4.Lab2.MongoDB.Entities.LogsPedidos;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Field;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public int countPedidosLog10Minutos() {
        List<Bson> pipeline = Arrays.asList(
                // Deconstruye la lista de eventos, creando un nuevo documento por cada elemento
                Aggregates.unwind("$eventos"),
                // Ordena cada evento por el timestamp en orden ascendente
                Aggregates.sort(Sorts.ascending("eventos.timestamp")),
                // Agrupa los eventos por id_pedido y crea array de timestamps
                Aggregates.group("$id_pedido",
                        Arrays.asList(
                                Accumulators.push("timestamps", "$eventos.timestamp")
                        )
                ),
                // Crea nuevo documento con los calculos
                Aggregates.project(
                        Document.parse("{" +
                                // Mantiene el arreglo de timestamps
                                "timestamps: 1," +
                                // Crea nnuevo campo llamado rapid_changes
                                "rapid_changes: {" +
                                    // Cuenta el tamaño del arreglo (cantidad de eventos por pedido)
                                    "$size: {" +
                                        // Filtra para checkear timestamps consecutivos
                                        "$filter: {" +
                                            // Crea nuevo array con indices desde 0 a tamaño del arreglo -1
                                            "input: { $range: [0, { $subtract: [{ $size: '$timestamps' }, 1] }] }," +
                                            // Llama la variable para cada indice
                                            "as: 'i'," +
                                            // Condicion para el filtro
                                            "cond: {" +
                                                // Checkea si la diferencia entre timestamps es 10 minutos o menos
                                                "$lte: [" +
                                                    // Calcula la diferencia entre timestamps
                                                    "{" +
                                                    "$subtract: [" +
                                                        // Obtiene el timestamp i+1
                                                        "{ $arrayElemAt: ['$timestamps', { $add: ['$$i', 1] }] }," +
                                                        // Obtiene el timestamp i
                                                        "{ $arrayElemAt: ['$timestamps', '$$i'] }" +
                                                    "]" +
                                                    "}," +
                                                "600000" +  // 10 minutos convertidos a milisegundos (10 * 60 * 1000)
                                                "]" +
                                            "}" +
                                        "}" +
                                    "}" +
                                "}" +
                        "}")
                ),
                // Filtra los documentos por los pedidos con mas de 3 rapid_changes
                Aggregates.match(Document.parse("{ rapid_changes: { $gte: 3 } }")),
                // Cuenta el total de documentos
                Aggregates.count("count")
        );
        // Ejecuta la gregacion y obtiene el primer resultado
        Document result = database.getCollection(COLLECTION_NAME)
                .aggregate(pipeline, Document.class)
                .first();
        // Retorna 0 si no se obtuvieron resultados
        return result != null ? result.getInteger("count") : 0;
    }

}