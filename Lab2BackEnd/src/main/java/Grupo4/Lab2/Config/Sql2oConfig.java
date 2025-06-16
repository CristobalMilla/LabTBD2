package Grupo4.Lab2.Config;

import org.springframework.beans.factory.annotation.Value;
import org.sql2o.Sql2o;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sql2oConfig {

    @Value("${DB_NAME}")
    private String dbName;

    @Value("${DB_USERNAME}")
    String username;   // Cambia por nombre de usuario

    @Value("${DB_PASSWORD}")
    String password;       // Cambia por contraseña de SQL
    String url = "jdbc:postgresql://localhost:5432/lab2TBD";

    @Bean
    public Sql2o sql2o() {
        String url = "jdbc:postgresql://localhost:5432/" + dbName;
        return new Sql2o(url, username, password);
    }
}