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
        
        HttpSession sesion = request.getSession(false);
        UsuarioDTO usuario = (sesion != null) ? (UsuarioDTO) sesion.getAttribute("usuario") : null;
        
        // Verificar que el usuario esté autenticado
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=session");
            return;
        }
        
        // Verificar que sea administrador
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