package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.DTO.PromedioPuntuacionXEmpresaDTO;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.DTO.OpinionStatsPorHoraDTO;
import Grupo4.Lab2.Repositories.EmpresaRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.BsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

// Implementation of the OpinionesClientesRepository interface.
@Repository
public class OpinionesClientesRepositoryImpl implements OpinionesClientesRepository {

    // Autowire the MongoDatabase bean, which is configured in MongoDbConfig.
    @Autowired
    private MongoDatabase database;

    @Autowired
    private EmpresaRepository empresaRepo;

    // The name of the MongoDB collection for OpinionesClientes.
    private static final String COLLECTION_NAME = "opiniones_clientes";
    private static final String COUNTERS_COLLECTION = "counters"; // Colección para secuencias

    /**
     * Helper method to get the MongoDB collection for OpinionesClientes,
     * ensuring it's mapped to the Java class.
     *
     * @return MongoCollection of OpinionesClientes.
     */
    private MongoCollection<OpinionesClientes> getCollection() {
        return database.getCollection(COLLECTION_NAME, OpinionesClientes.class);
    }

    @Override
    public List<OpinionesClientes> findAll() {
        // Retrieve all documents from the collection and convert them into a List of OpinionesClientes.
        return getCollection().find().into(new ArrayList<>());
    }

    @Override
    public OpinionesClientes findById(long id) {
        // Find a single document by its _id field.
        // The eq() static import from Filters helps create the query condition.
        return getCollection().find(eq("_id", id)).first();
    }

    @Override
    public OpinionesClientes save(OpinionesClientes opinion) {
        // Insert a new document into the collection.
        // The POJO codec automatically handles mapping the Java object to a MongoDB document.
        getCollection().insertOne(opinion);
        return opinion;
    }

    @Override
    public OpinionesClientes update(long id, OpinionesClientes opinion) {
        // Replace an existing document identified by its _id.
        // This will entirely replace the document. For partial updates, use updateOne and Set.
        getCollection().replaceOne(eq("_id", id), opinion);
        return opinion;
    }

    @Override
    public void delete(long id) {
        // Delete a document by its _id.
        DeleteResult result = getCollection().deleteOne(eq("_id", id));
        // You might want to add logging or throw an exception if result.getDeletedCount() is 0
        // indicating that no document was found with the given ID.
        if (result.getDeletedCount() == 0) {
            System.out.println("No document found with id: " + id + " for deletion.");
        }
    }

    @Override
    public long count() {
        // Count the total number of documents in the collection.
        return getCollection().countDocuments();
    }

    @Override
    public long getNextSequenceId(String sequenceName) {
        MongoCollection<Document> countersCollection = database.getCollection(COUNTERS_COLLECTION);
        Document find = new Document("_id", sequenceName);
        // Incrementa el campo 'seq' en 1 y devuelve el nuevo documento.
        // upsert(true) crea el documento contador si no existe.
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER)
                .upsert(true);
        Document result = countersCollection.findOneAndUpdate(find, inc("seq", 1L), options);

        // Si el resultado no es nulo, devuelve el valor de la secuencia.
        return (result != null) ? result.getLong("seq") : 1;
    }


    // Query 1
    public List<PromedioPuntuacionXEmpresaDTO> getPromedioDePuntuacionXEmpresa(){
        MongoCollection<OpinionesClientes> collection = getCollection();
        AggregateIterable<PromedioPuntuacionXEmpresaDTO> promedios = collection.aggregate(Arrays.asList(
                Aggregates.group("$empresa_id", Accumulators.avg("promedio", "$puntuacion")),
                Aggregates.project(
                        Projections.fields(
                                Projections.computed("empresa_id", "$_id"),
                                Projections.include("promedio")
                        )
                ), Aggregates.sort(Sorts.ascending("empresa_id"))
        ), PromedioPuntuacionXEmpresaDTO.class);

        List<PromedioPuntuacionXEmpresaDTO> promediosList = new ArrayList<>();

        for (PromedioPuntuacionXEmpresaDTO promedio : promedios) {
            String nombreEmpresa = empresaRepo.findById(promedio.getEmpresa_id()).getNombre();
            promedio.setNombre_empresa(nombreEmpresa);
            promediosList.add(promedio);
        }

        return promediosList;
    }

    @Override
    public List<OpinionStatsPorHoraDTO> getStatsPorHora() {
        MongoCollection<OpinionesClientes> collection = getCollection();
        List<OpinionStatsPorHoraDTO> results = new ArrayList<>();

        collection.aggregate(Arrays.asList(
            // Etapa 1: Agrupar por la hora extraída del campo 'fecha'
            Aggregates.group(
                // Extrae la hora del día (0-23) del campo 'fecha'. Se asume UTC.
                new Document("$hour", new Document("date", "$fecha")), 
                // Define los acumuladores para el grupo
                new BsonField("averageScore", new Document("$avg", "$puntuacion")),
                new BsonField("count", new Document("$sum", 1))
            ),
            // Etapa 2: Proyectar los campos para que coincidan con el DTO
            Aggregates.project(
                Projections.fields(
                    Projections.excludeId(),
                    Projections.computed("hour", "$_id"), // Renombrar _id a hour
                    Projections.include("averageScore", "count")
                )
            ),
            // Etapa 3: Ordenar los resultados por hora
            Aggregates.sort(Sorts.ascending("hour"))
        ), Document.class).forEach(doc -> {
            OpinionStatsPorHoraDTO dto = new OpinionStatsPorHoraDTO();
            dto.setHour(doc.getInteger("hour"));
            dto.setAverageScore(doc.getDouble("averageScore"));
            dto.setCount(doc.getInteger("count").longValue());
            results.add(dto);
        });

        return results;
    }
}
