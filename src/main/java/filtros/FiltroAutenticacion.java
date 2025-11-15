package filtros;

import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro que protege todas las páginas dentro de /vistas/
 * excepto login.jsp y páginas públicas
 */
@WebFilter(urlPatterns = {"/vistas/*"})
public class FiltroAutenticacion implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String uri = request.getRequestURI();
        
        // Páginas públicas que no requieren autenticación
        if (uri.endsWith("/login.jsp") || 
            uri.endsWith("/registro.jsp") ||
            uri.contains("/css/") || 
            uri.contains("/js/") || 
            uri.contains("/img/")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Verificar si el usuario está autenticado
        HttpSession sesion = request.getSession(false);
        UsuarioDTO usuario = (sesion != null) ? (UsuarioDTO) sesion.getAttribute("usuario") : null;
        
        if (usuario == null) {
            // Usuario no autenticado, redirigir al login
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=session");
            return;
        }
        
        // Usuario autenticado, permitir acceso
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
