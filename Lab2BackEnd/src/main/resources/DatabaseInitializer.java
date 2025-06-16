import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DatabaseInitializer {

    @Autowired
    private Sql2o sql2o;

    @PostConstruct
    public void init() {
        try {
            Path scriptPath = new ClassPathResource("schema.sql").getFile().toPath();
            String sql = Files.readString(scriptPath);

            try (Connection con = sql2o.open()) {
                con.createQuery(sql).executeUpdate();
                System.out.println("Esquema de base de datos ejecutado correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar base de datos: " + e.getMessage());
        }
    }
}
