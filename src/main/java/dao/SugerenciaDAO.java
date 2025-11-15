package dao;

import dto.SugerenciaDTO;
import servicios.ConectaDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SugerenciaDAO {
    
    public boolean insertar(SugerenciaDTO s) {
        String sql = "INSERT INTO sugerencias (nombre, correo, asunto, mensaje) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getCorreo());
            ps.setString(3, s.getAsunto());
            ps.setString(4, s.getMensaje());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<SugerenciaDTO> listarTodos() throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SugerenciaDTO> lista = new ArrayList<>();
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM sugerencias ORDER BY fecha DESC";
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                SugerenciaDTO sugerencia = new SugerenciaDTO();
                sugerencia.setId(rs.getInt("id"));
                sugerencia.setNombre(rs.getString("nombre"));
                sugerencia.setCorreo(rs.getString("correo"));
                sugerencia.setAsunto(rs.getString("asunto"));
                sugerencia.setMensaje(rs.getString("mensaje"));
                sugerencia.setFecha(rs.getTimestamp("fecha"));
                lista.add(sugerencia);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return lista;
    }
    
    public SugerenciaDTO buscarPorId(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SugerenciaDTO sugerencia = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM sugerencias WHERE id = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                sugerencia = new SugerenciaDTO();
                sugerencia.setId(rs.getInt("id"));
                sugerencia.setNombre(rs.getString("nombre"));
                sugerencia.setCorreo(rs.getString("correo"));
                sugerencia.setAsunto(rs.getString("asunto"));
                sugerencia.setMensaje(rs.getString("mensaje"));
                sugerencia.setFecha(rs.getTimestamp("fecha"));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return sugerencia;
    }
    
    public boolean eliminar(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "DELETE FROM sugerencias WHERE id = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
    }
    
    public int contarTodos() throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT COUNT(*) as total FROM sugerencias";
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