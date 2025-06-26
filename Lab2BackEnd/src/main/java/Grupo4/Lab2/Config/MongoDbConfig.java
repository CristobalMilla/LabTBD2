package Grupo4.Lab2.Config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class configures the MongoDB connection for your Spring application.
@Configuration
public class MongoDbConfig {

    // Injects the MongoDB database name from application.properties
    @Value("${spring.data.mongodb.database}")
    private String mongoDbName;

    @Value("${spring.data.mongodb.uri}") // Eliminar este atributo si se trabaja con la bd local
    private String mongoUri;
    /**
     * Configures and provides a MongoClient bean.
     * This client is used to connect to the MongoDB instance.
     *
     * @return A MongoClient instance.
     */
    @Bean
    public MongoClient mongoClient() {
        // Create a new MongoClient instance. By default, it connects to localhost:27017.
        // You can customize connection settings here if needed (e.g., server address, credentials).
        return MongoClients.create(mongoUri); // Eliminar mongoUri si se tiene la bd en local
    }

    /**
     * Configures and provides a MongoDatabase bean.
     * This database instance is pre-configured with a POJO (Plain Old Java Object) codec registry
     * to automatically map Java objects (like OpinionesClientes) to MongoDB documents and vice-versa.
     *
     * @param mongoClient The MongoClient bean provided by the mongoClient() method.
     * @return A MongoDatabase instance configured for POJO mapping.
     */
    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        // Get the default codec registry from MongoClient settings.
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();

        // Create a PojoCodecProvider that automatically maps POJOs.
        // The .automatic(true) tells it to find and map classes automatically.
        CodecRegistry fromProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());

        // Combine the default codec registry with the POJO codec registry.
        // This ensures that both standard MongoDB types and your custom POJOs can be handled.
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);

        // Get the specified database from the MongoClient and apply the combined codec registry.
        return mongoClient.getDatabase(mongoDbName).withCodecRegistry(pojoCodecRegistry);
    }
}
