package utils;

import dto.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Clase de utilidad para manejar operaciones comunes de sesión
 */
public class SessionUtil {
    
    /**
     * Obtiene el usuario de la sesión actual
     */
    public static UsuarioDTO getUsuarioSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return null;
        }
        return (UsuarioDTO) sesion.getAttribute("usuario");
    }
    
    /**
     * Verifica si hay un usuario autenticado
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUsuarioSesion(request) != null;
    }
    
    /**
     * Verifica si el usuario autenticado es administrador
     */
    public static boolean isAdmin(HttpServletRequest request) {
        UsuarioDTO usuario = getUsuarioSesion(request);
        return usuario != null && usuario.esAdmin();
    }
    
    /**
     * Verifica si el usuario autenticado es usuario regular
     */
    public static boolean isUsuario(HttpServletRequest request) {
        UsuarioDTO usuario = getUsuarioSesion(request);
        return usuario != null && usuario.esUsuario();
    }
    
    /**
     * Invalida la sesión actual
     */
    public static void invalidarSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
    }
    
    /**
     * Crea una nueva sesión y almacena el usuario
     */
    public static void crearSesion(HttpServletRequest request, UsuarioDTO usuario) {
        // Invalidar sesión anterior por seguridad
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
        // Crear nueva sesión
        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(1800); // 30 minutos
        
        // Almacenar información del usuario
        newSession.setAttribute("usuario", usuario);
        newSession.setAttribute("rol", usuario.getRol());
        newSession.setAttribute("nombre", usuario.getNombre());
        newSession.setAttribute("correo", usuario.getCorreo());
        newSession.setAttribute("loginTime", System.currentTimeMillis());
    }
    
    /**
     * Actualiza el timestamp de última actividad
     */
    public static void actualizarActividad(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion != null) {
            sesion.setAttribute("lastActivity", System.currentTimeMillis());
        }
    }
    
    /**
     * Obtiene el tiempo transcurrido desde el login (en minutos)
     */
    public static long getTiempoSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return 0;
        }
        
        Long loginTime = (Long) sesion.getAttribute("loginTime");
        if (loginTime == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        return (currentTime - loginTime) / 60000; // Convertir a minutos
    }
}