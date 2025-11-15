package dao;

import dto.ContactoDTO;
import servicios.ConectaDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {
    
    /**
     * Inserta un nuevo contacto
     */
    public boolean insertar(ContactoDTO c) {
        String sql = "INSERT INTO contacto (nombre, correo, telefono, asunto, mensaje) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getAsunto());
            ps.setString(5, c.getMensaje());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lista todos los contactos
     */
    public List<ContactoDTO> listarTodos() throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContactoDTO> lista = new ArrayList<>();
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM contacto ORDER BY fecha DESC";
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ContactoDTO contacto = new ContactoDTO();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setCorreo(rs.getString("correo"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setAsunto(rs.getString("asunto"));
                contacto.setMensaje(rs.getString("mensaje"));
                contacto.setFecha(rs.getTimestamp("fecha"));
                lista.add(contacto);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return lista;
    }
    
    /**
     * Busca un contacto por ID
     */
    public ContactoDTO buscarPorId(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ContactoDTO contacto = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM contacto WHERE id = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                contacto = new ContactoDTO();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setCorreo(rs.getString("correo"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setAsunto(rs.getString("asunto"));
                contacto.setMensaje(rs.getString("mensaje"));
                contacto.setFecha(rs.getTimestamp("fecha"));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return contacto;
    }
    
    /**
     * Elimina un contacto
     */
    public boolean eliminar(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "DELETE FROM contacto WHERE id = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
    }
    
    /**
     * Cuenta el total de contactos
     */
    public int contarTodos() throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT COUNT(*) as total FROM contacto";
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
    }
}