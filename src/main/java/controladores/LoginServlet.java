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

        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO usuario = dao.validarLogin(correo, contraseña);

            if (usuario != null) {
                // ✅ Crear sesión
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);

                // ✅ Redirigir al dashboard si el login fue correcto
                response.sendRedirect(request.getContextPath() + "/vistas/dashboard.jsp");
            } else {
                // ❌ Usuario o contraseña incorrectos
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
        // Redirigir al login si acceden directamente al servlet por GET
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
    }
}
