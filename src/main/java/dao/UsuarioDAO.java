package dao;

import dto.UsuarioDTO;
import servicios.ConectaDB;
import java.sql.*;

public class UsuarioDAO {
    
    /**
     * Valida las credenciales del usuario
     */
    public UsuarioDTO validarLogin(String correo, String contrasena) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO usuario = null;
        
        try {
            // Obtener conexión
            cnx = ConectaDB.getConexion();
            
            // Log para debug
            System.out.println("=== DEBUG UsuarioDAO.validarLogin ===");
            System.out.println("Correo buscado: [" + correo + "]");
            System.out.println("Contraseña buscada: [" + contrasena + "]");
            
            // Primero: verificar si el correo existe
            String sqlCheck = "SELECT id, correo, contraseña, nombre, rol FROM usuarios WHERE correo = ?";
            ps = cnx.prepareStatement(sqlCheck);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String passwordDB = rs.getString("contraseña");
                System.out.println("✅ Correo encontrado en BD");
                System.out.println("   Contraseña en BD: [" + passwordDB + "]");
                System.out.println("   Contraseña recibida: [" + contrasena + "]");
                System.out.println("   ¿Coinciden? " + passwordDB.equals(contrasena));
                
                // Verificar si las contraseñas coinciden
                if (passwordDB.equals(contrasena)) {
                    usuario = new UsuarioDTO();
                    usuario.setId(rs.getInt("id"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(rs.getString("rol"));
                    
                    System.out.println("✅ LOGIN EXITOSO");
                    System.out.println("   Usuario: " + usuario.getNombre());
                    System.out.println("   Rol: " + usuario.getRol());
                } else {
                    System.err.println("❌ Contraseña incorrecta");
                }
            } else {
                System.err.println("❌ Correo no encontrado en la base de datos");
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL en validarLogin: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al validar login: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usuario;
    }
    
    /**
     * Busca un usuario por correo
     */
    public UsuarioDTO buscarPorCorreo(String correo) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO usuario = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT id, correo, nombre, rol FROM usuarios WHERE correo = ?";
            ps = cnx.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new UsuarioDTO();
                usuario.setId(rs.getInt("id"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.err.println("Error en buscarPorCorreo: " + e.getMessage());
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usuario;
    }
    
    /**
     * Registra un nuevo usuario
     */
    public boolean registrarUsuario(UsuarioDTO usuario) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "INSERT INTO usuarios (correo, contraseña, nombre, rol) VALUES (?, ?, ?, ?)";
            ps = cnx.prepareStatement(sql);
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getRol());
            
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Usuario registrado: " + usuario.getCorreo());
            
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error en registrarUsuario: " + e.getMessage());
            throw new Exception("Error al registrar usuario: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Actualiza la contraseña de un usuario
     */
    public boolean actualizarContraseña(String correo, String nuevaContrasena) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "UPDATE usuarios SET contraseña = ? WHERE correo = ?";
            ps = cnx.prepareStatement(sql);
            ps.setString(1, nuevaContrasena);
            ps.setString(2, correo);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en actualizarContraseña: " + e.getMessage());
            throw new Exception("Error al actualizar contraseña: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}