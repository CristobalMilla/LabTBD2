package Grupo4.Lab2.Config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomAccessDeniedHandler es un componente que maneja excepciones de acceso denegado en la aplicación.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Maneja las solicitudes HTTP cuando se deniega el acceso.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param accessDeniedException La excepción de acceso denegado.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Establece el estado de la respuesta a 403 (Prohibido)
        response.getWriter().write("No tienes permisos para acceder a este recurso."); // Escribe un mensaje de error en la respuesta
    }
}
