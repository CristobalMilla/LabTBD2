package Grupo4.Lab2.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDbConfig {

    private String mongoUri = "mongodb+srv://mongodb:mongodb@mongousach.chn5ins.mongodb.net/?retryWrites=true&w=majority&appName=mongousach ";

    @Bean
    public MongoDatabase MongoDB() {

        return MongoClients.create(mongoUri).getDatabase("tbd");
    }

}