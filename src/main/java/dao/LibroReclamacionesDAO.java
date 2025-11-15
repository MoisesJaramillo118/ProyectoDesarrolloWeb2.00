package dao;

import dto.LibroReclamacionesDTO;
import servicios.ConectaDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroReclamacionesDAO {
    
    public boolean insertar(LibroReclamacionesDTO l) {
        String sql = "INSERT INTO libro_reclamaciones (nombre, correo, telefono, direccion, documento, tipo, producto, descripcion, pedido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, l.getNombre());
            ps.setString(2, l.getCorreo());
            ps.setString(3, l.getTelefono());
            ps.setString(4, l.getDireccion());
            ps.setString(5, l.getDocumento());
            ps.setString(6, l.getTipo());
            ps.setString(7, l.getProducto());
            ps.setString(8, l.getDescripcion());
            ps.setString(9, l.getPedido());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<LibroReclamacionesDTO> listarTodos() throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LibroReclamacionesDTO> lista = new ArrayList<>();
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM libro_reclamaciones ORDER BY fecha DESC";
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                LibroReclamacionesDTO reclamo = new LibroReclamacionesDTO();
                reclamo.setId(rs.getInt("id"));
                reclamo.setNombre(rs.getString("nombre"));
                reclamo.setCorreo(rs.getString("correo"));
                reclamo.setTelefono(rs.getString("telefono"));
                reclamo.setDireccion(rs.getString("direccion"));
                reclamo.setDocumento(rs.getString("documento"));
                reclamo.setTipo(rs.getString("tipo"));
                reclamo.setProducto(rs.getString("producto"));
                reclamo.setDescripcion(rs.getString("descripcion"));
                reclamo.setPedido(rs.getString("pedido"));
                reclamo.setFecha(rs.getTimestamp("fecha"));
                lista.add(reclamo);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return lista;
    }
    
    public LibroReclamacionesDTO buscarPorId(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LibroReclamacionesDTO reclamo = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "SELECT * FROM libro_reclamaciones WHERE id = ?";
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                reclamo = new LibroReclamacionesDTO();
                reclamo.setId(rs.getInt("id"));
                reclamo.setNombre(rs.getString("nombre"));
                reclamo.setCorreo(rs.getString("correo"));
                reclamo.setTelefono(rs.getString("telefono"));
                reclamo.setDireccion(rs.getString("direccion"));
                reclamo.setDocumento(rs.getString("documento"));
                reclamo.setTipo(rs.getString("tipo"));
                reclamo.setProducto(rs.getString("producto"));
                reclamo.setDescripcion(rs.getString("descripcion"));
                reclamo.setPedido(rs.getString("pedido"));
                reclamo.setFecha(rs.getTimestamp("fecha"));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cnx != null) cnx.close();
        }
        
        return reclamo;
    }
    
    public boolean eliminar(int id) throws Exception {
        Connection cnx = null;
        PreparedStatement ps = null;
        
        try {
            cnx = ConectaDB.getConexion();
            String sql = "DELETE FROM libro_reclamaciones WHERE id = ?";
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
            String sql = "SELECT COUNT(*) as total FROM libro_reclamaciones";
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