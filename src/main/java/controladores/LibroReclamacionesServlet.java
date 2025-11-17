package controladores;

import dao.LibroReclamacionesDAO;
import dto.LibroReclamacionesDTO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/libro")
public class LibroReclamacionesServlet extends HttpServlet {
    
    private boolean esAdmin(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) return false;
        
        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        return usuario != null && "Admin".equals(usuario.getRol());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Solo admin puede VER la lista de reclamos
        if (!esAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/?error=permission");
            return;
        }
        
        try {
            LibroReclamacionesDAO dao = new LibroReclamacionesDAO();
            List<LibroReclamacionesDTO> reclamos = dao.listarTodos();
            request.setAttribute("reclamos", reclamos);
            request.getRequestDispatcher("/vistas/admin/listarReclamos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/dashboard.jsp?error=true");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // CUALQUIER usuario puede ENVIAR un reclamo
        try {
            LibroReclamacionesDTO reclamo = new LibroReclamacionesDTO();
            reclamo.setNombre(request.getParameter("nombre"));
            reclamo.setCorreo(request.getParameter("correo"));
            reclamo.setTelefono(request.getParameter("telefono"));
            reclamo.setDireccion(request.getParameter("direccion"));
            reclamo.setDocumento(request.getParameter("documento"));
            reclamo.setTipo(request.getParameter("tipo")); // Reclamo o Queja
            reclamo.setProducto(request.getParameter("producto"));
            reclamo.setDescripcion(request.getParameter("descripcion"));
            reclamo.setPedido(request.getParameter("pedido"));
            
            LibroReclamacionesDAO dao = new LibroReclamacionesDAO();
            dao.insertar(reclamo);
            
            response.sendRedirect(request.getContextPath() + "/vistas/libroReclamaciones.jsp?success=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/libroReclamaciones.jsp?error=1");
        }
    }
}