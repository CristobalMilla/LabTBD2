package Grupo4.Lab2.Config;

import org.sql2o.Sql2o;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sql2oConfig {

    @Bean
    public Sql2o sql2o() {
        String url = "jdbc:postgresql://localhost:5432/lab2tbd";
        String username = "postgres";   // Cambia por nombre de usuario 
        String password = "admin";       // Cambia por contraseña de SQL
        return new Sql2o(url, username, password);
    }
}