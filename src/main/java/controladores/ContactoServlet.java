package controladores;

import dao.ContactoDAO;
import dto.ContactoDTO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/contacto")
public class ContactoServlet extends HttpServlet {
    
    private boolean esAdmin(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) return false;
        
        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        return usuario != null && "Admin".equals(usuario.getRol());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Solo admin puede VER la lista de contactos
        if (!esAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/?error=permission");
            return;
        }
        
        try {
            ContactoDAO dao = new ContactoDAO();
            List<ContactoDTO> contactos = dao.listarTodos();
            request.setAttribute("contactos", contactos);
            request.getRequestDispatcher("/vistas/admin/listarContactos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/dashboard.jsp?error=true");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // CUALQUIER usuario puede ENVIAR un contacto (no requiere ser admin)
        try {
            ContactoDTO contacto = new ContactoDTO();
            contacto.setNombre(request.getParameter("nombre"));
            contacto.setCorreo(request.getParameter("correo"));
            contacto.setTelefono(request.getParameter("telefono"));
            contacto.setAsunto(request.getParameter("asunto"));
            contacto.setMensaje(request.getParameter("mensaje"));
            
            ContactoDAO dao = new ContactoDAO();
            dao.insertar(contacto);
            
            // Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/vistas/contacto.jsp?success=1");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/contacto.jsp?error=1");
        }
    }
}