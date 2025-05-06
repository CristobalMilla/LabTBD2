package Grupo4.Lab2.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** * CorsConfig es una clase de configuración que define la configuración de CORS (Cross-Origin Resource Sharing) para la aplicación. */
@Configuration
public class CorsConfig {

    /**
     * Define la fuente de configuración de CORS.
     * 
     * @return Una instancia de UrlBasedCorsConfigurationSource con la configuración de CORS.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Permitir todas las URL
        configuration.addAllowedMethod("*");       // Permitir todos los métodos (GET, POST, etc.)
        configuration.addAllowedHeader("*");       // Permitir todos los encabezados
        configuration.setAllowCredentials(true);   // Permitir credenciales como cookies o cabeceras de autenticación

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}