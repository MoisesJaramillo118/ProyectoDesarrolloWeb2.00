package controladores;

import dao.SugerenciaDAO;
import dto.SugerenciaDTO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/sugerencia")
public class SugerenciaServlet extends HttpServlet {
    
    private boolean esAdmin(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) return false;
        
        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        return usuario != null && "Admin".equals(usuario.getRol());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Solo admin puede VER la lista
        if ("listar".equals(accion)) {
            if (!esAdmin(request)) {
                response.sendRedirect(request.getContextPath() + "/?error=permission");
                return;
            }
            
            try {
                SugerenciaDAO dao = new SugerenciaDAO();
                List<SugerenciaDTO> sugerencias = dao.listarTodos();
                request.setAttribute("sugerencias", sugerencias);
                request.getRequestDispatcher("/vistas/admin/listarSugerencias.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/vistas/dashboard.jsp?error=true");
            }
        } else {
            // Mostrar formulario (cualquier usuario puede acceder)
            response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // CUALQUIER usuario puede ENVIAR una sugerencia
        try {
            SugerenciaDTO sugerencia = new SugerenciaDTO();
            sugerencia.setNombre(request.getParameter("nombre"));
            sugerencia.setCorreo(request.getParameter("correo"));
            sugerencia.setAsunto(request.getParameter("asunto"));
            sugerencia.setMensaje(request.getParameter("mensaje"));
            
            SugerenciaDAO dao = new SugerenciaDAO();
            dao.insertar(sugerencia);
            
            response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp?success=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp?error=1");
        }
    }
}