package Grupo4.Lab2.Config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig es una clase de configuración que define la configuración de seguridad para la aplicación.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfig corsConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    public SecurityConfig(JwtFilter jwtFilter, CorsConfig corsConfig, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.corsConfig = corsConfig;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param http El objeto HttpSecurity.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configurando seguridad...");
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers("/api/usuarios/login", "/api/usuarios/register").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll() //Cambiar a hasAnyRole("ADMIN", "TRABAJADOR") si se desea restringir el acceso a los endpoints de la API
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // Mostrar el motivo del acceso denegado
                            String requiredRoles = request.getRequestURI().contains("/api/reserva/") ? "ADMIN, TRABAJADOR, CLIENTE" : "UNKNOWN";
                            String userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

                            System.out.println("Acceso denegado. Rol requerido: " + requiredRoles + ", Rol actual del usuario: " + userRoles);

                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("Acceso denegado. Rol requerido: " + requiredRoles + "\nRol actual del usuario: " + userRoles + "\nError: " + accessDeniedException.getMessage());
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Define el codificador de contraseñas.
     *
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define el gestor de autenticación.
     *
     * @param authenticationConfiguration La configuración de autenticación.
     * @return El gestor de autenticación.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
