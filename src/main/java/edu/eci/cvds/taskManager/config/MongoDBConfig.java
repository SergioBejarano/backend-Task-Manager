package edu.eci.cvds.taskManager.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "edu.eci.cvds.taskManager.repositories.mongodb")
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "taskManager"; // Cambia esto por el nombre de tu base de datos MongoDB
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:8081"); // Cambia la URL según tu configuración
    }
}
