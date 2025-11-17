package controladores;

import dao.UsuarioDAO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");
        String confirmarContraseña = request.getParameter("confirmarContraseña");

        System.out.println("=== REGISTRO DE USUARIO ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Contraseña recibida: " + (contraseña != null ? "Sí" : "No"));

        // Validación 1: Campos vacíos
        if (nombre == null || nombre.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            contraseña == null || contraseña.trim().isEmpty()) {
            
            System.err.println("❌ Error: Campos vacíos");
            response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp?error=1");
            return;
        }

        // Validación 2: Contraseñas coinciden
        if (!contraseña.equals(confirmarContraseña)) {
            System.err.println("❌ Error: Las contraseñas no coinciden");
            response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp?error=2");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            
            // Validación 3: Verificar si el correo ya existe
            UsuarioDTO usuarioExistente = dao.buscarPorCorreo(correo.trim());
            if (usuarioExistente != null) {
                System.err.println("❌ Error: El correo ya está registrado");
                response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp?error=3");
                return;
            }

            // Crear nuevo usuario con rol "Usuario" por defecto
            UsuarioDTO nuevoUsuario = new UsuarioDTO();
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(correo.trim());
            nuevoUsuario.setContraseña(contraseña); // En producción: hashear
            nuevoUsuario.setRol("Usuario"); // ← IMPORTANTE: Rol por defecto

            System.out.println("Intentando registrar usuario...");
            
            // Registrar usuario en la BD
            boolean registrado = dao.registrarUsuario(nuevoUsuario);
            
            if (registrado) {
                System.out.println("✅ Usuario registrado exitosamente");
                System.out.println("   Correo: " + correo);
                System.out.println("   Rol: Usuario");
                
                // Redirigir al login con mensaje de éxito
                response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?registro=1");
            } else {
                System.err.println("❌ Error: No se pudo registrar el usuario");
                response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp?error=4");
            }

        } catch (Exception e) {
            System.err.println("❌ Excepción en RegistroServlet:");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp?error=5");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si acceden por GET, redirigir a la página de registro
        response.sendRedirect(request.getContextPath() + "/vistas/registro.jsp");
    }
}