package Grupo4.Lab2.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JwtUtil es una clase de configuración que se encarga de generar, validar y extraer información del token JWT.
 */
@Configuration
public class JwtUtil {

    private static final String SECRET_KEY = "alvolante";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Método para crear un JWT.
     *
     * @param email El correo electrónico del usuario.
     * @return El token JWT generado.
     */
    public String createToken(String email) {
        System.out.println("En la función createToken para crear el token.");
        try {
            return JWT.create()
                    .withSubject(email)
                    .withIssuer("tbd") // Emisor del token (por definir)
                    .withIssuedAt(new Date()) // Fecha de emisión
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(666))) // Duración del token
                    .sign(ALGORITHM);
        } catch (Exception e) {
            System.out.println("Error al generar el token JWT: " + e.getMessage());
            throw new RuntimeException("Error al generar el token JWT.", e);
        }
    }

    /**
     * Método para validar un JWT.
     *
     * @param jwt El token JWT a validar.
     * @return Verdadero si el token es válido, falso en caso contrario.
     */
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true; // Si no lanza excepción, el token es válido
        } catch (JWTVerificationException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para extraer el correo electrónico del JWT.
     *
     * @param jwt El token JWT.
     * @return El correo electrónico contenido en el "subject" del token.
     */
    public String getEmail(String jwt) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM)
                .build()
                .verify(jwt);
        return decodedJWT.getSubject(); // Retorna el "subject" del token
    }

}
