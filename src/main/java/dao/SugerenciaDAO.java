package dao;

import dto.SugerenciaDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import servicios.ConectaDB;

public class SugerenciaDAO {

    // 🟢 INSERTAR sugerencia
    public boolean insertar(SugerenciaDTO s) {
        String sql = "INSERT INTO sugerencias (nombre, correo, asunto, mensaje) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, s.getNombre());
            ps.setString(2, s.getCorreo());
            ps.setString(3, s.getAsunto());
            ps.setString(4, s.getMensaje());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🟡 LISTAR todas las sugerencias
    public List<SugerenciaDTO> listar() {
        List<SugerenciaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM sugerencias ORDER BY id DESC";

        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SugerenciaDTO s = new SugerenciaDTO();
                s.setId(rs.getInt("id"));
                s.setNombre(rs.getString("nombre"));
                s.setCorreo(rs.getString("correo"));
                s.setAsunto(rs.getString("asunto"));
                s.setMensaje(rs.getString("mensaje"));
                s.setFecha(rs.getTimestamp("fecha")); // si tienes columna fecha en la BD
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔴 (Opcional) ELIMINAR una sugerencia por ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM sugerencias WHERE id = ?";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
