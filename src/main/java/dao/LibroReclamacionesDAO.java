package dao;

import dto.LibroReclamacionesDTO;
import servicios.ConectaDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroReclamacionesDAO {

    // ✅ INSERTAR un nuevo reclamo
    public boolean insertar(LibroReclamacionesDTO l) {
        String sql = "INSERT INTO libro_reclamaciones (nombre, correo, telefono, direccion, documento, tipo, producto, descripcion, pedido) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    // ✅ LISTAR todos los reclamos para mostrarlos en el dashboard
    public List<LibroReclamacionesDTO> listar() {
        List<LibroReclamacionesDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM libro_reclamaciones ORDER BY id DESC";

        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LibroReclamacionesDTO l = new LibroReclamacionesDTO();
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setTelefono(rs.getString("telefono"));
                l.setDireccion(rs.getString("direccion"));
                l.setDocumento(rs.getString("documento"));
                l.setTipo(rs.getString("tipo"));
                l.setProducto(rs.getString("producto"));
                l.setDescripcion(rs.getString("descripcion"));
                l.setPedido(rs.getString("pedido"));

                lista.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
