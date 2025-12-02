package filtros;

import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro que verifica que solo administradores accedan a:
 * - dashboard.jsp
 * - Servlets de administración (carro, empresa, libro, sugerencia)
 * PERO permite que usuarios normales envíen formularios vía POST
 */
@WebFilter(urlPatterns = {
    "/vistas/dashboard.jsp",
    "/carro",
    "/empresa",
    "/libro",
    "/sugerencia"
})
public class FiltroRolAdmin implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        String metodo = request.getMethod(); // Detectar si es POST

        HttpSession sesion = request.getSession(false);
        UsuarioDTO usuario = (sesion != null) ? (UsuarioDTO) sesion.getAttribute("usuario") : null;

        // Permitir acceso si es POST a /sugerencia, /libro o /contacto (envío de formularios)
        if (("POST".equalsIgnoreCase(metodo)) &&
            (uri.endsWith("/sugerencia") || uri.endsWith("/libro") || uri.endsWith("/contacto"))) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar autenticación
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=session");
            return;
        }

        // Verificar rol
        if (!usuario.esAdmin()) {
            response.sendRedirect(request.getContextPath() + "/vistas/usuario/home.jsp?error=permission");
            return;
        }

        // Usuario es admin, permitir acceso
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}