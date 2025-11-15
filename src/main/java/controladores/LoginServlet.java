package controladores;

import dao.UsuarioDAO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Establecer encoding para caracteres especiales
        request.setCharacterEncoding("UTF-8");
        
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contraseña");

        System.out.println("=== INTENTO DE LOGIN ===");
        System.out.println("Correo recibido: [" + correo + "]");
        System.out.println("Contraseña recibida: [" + contrasena + "]");

        // Validar que los campos no estén vacíos
        if (correo == null || correo.trim().isEmpty() || 
            contrasena == null || contrasena.trim().isEmpty()) {
            System.err.println("❌ Campos vacíos");
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=3");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO usuario = dao.validarLogin(correo.trim(), contrasena);

            if (usuario != null) {
                // ✅ Crear sesión segura
                HttpSession sesion = request.getSession(false);
                
                // Invalidar sesión anterior si existe (prevenir session fixation)
                if (sesion != null) {
                    sesion.invalidate();
                }
                
                sesion = request.getSession(true);
                
                // Configurar timeout de sesión (30 minutos)
                sesion.setMaxInactiveInterval(1800);
                
                // Guardar información del usuario
                sesion.setAttribute("usuario", usuario);
                sesion.setAttribute("rol", usuario.getRol());
                sesion.setAttribute("nombre", usuario.getNombre());
                sesion.setAttribute("correo", usuario.getCorreo());
                sesion.setAttribute("loginTime", System.currentTimeMillis());

                // ✅ Redirigir según el rol
               if (usuario.esAdmin()) {
    response.sendRedirect(request.getContextPath() + "/vistas/dashboard.jsp");
} else {
    // Usuarios regulares van al catálogo de carros
    response.sendRedirect(request.getContextPath() + "/verCarros");
}
                
            } else {
                // ❌ Credenciales incorrectas
                response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=2");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al login si acceden directamente
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
    }
}