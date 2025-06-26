package Grupo4.Lab2.MongoDB.Repositories;

import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// Implementation of the OpinionesClientesRepository interface.
@Repository
public class OpinionesClientesRepositoryImpl implements OpinionesClientesRepository {

    // Autowire the MongoDatabase bean, which is configured in MongoDbConfig.
    @Autowired
    private MongoDatabase database;

    // The name of the MongoDB collection for OpinionesClientes.
    private static final String COLLECTION_NAME = "opiniones_clientes";

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
}
