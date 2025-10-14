package dao;

import dto.CarroDTO;
import java.sql.*;
import java.util.*;
import servicios.ConectaDB;

public class CarroDAO {
    private Connection conn;

    public CarroDAO() throws Exception {
        conn = ConectaDB.getConexion();
    }

    public List<CarroDTO> listarCarros() throws Exception {
        List<CarroDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM carros";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CarroDTO c = new CarroDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("imagen"),
                rs.getDouble("precio")
            );
            lista.add(c);
        }
        rs.close();
        ps.close();
        return lista;
    }

    public void insertar(CarroDTO c) throws Exception {
        String sql = "INSERT INTO carros (nombre, descripcion, imagen, precio) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getDescripcion());
        ps.setString(3, c.getImagen());
        ps.setDouble(4, c.getPrecio());
        ps.executeUpdate();
        ps.close();
    }

    public CarroDTO obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM carros WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        CarroDTO c = null;
        if (rs.next()) {
            c = new CarroDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("imagen"),
                rs.getDouble("precio")
            );
        }
        rs.close();
        ps.close();
        return c;
    }

    public void actualizar(CarroDTO c) throws Exception {
        String sql = "UPDATE carros SET nombre=?, descripcion=?, imagen=?, precio=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getDescripcion());
        ps.setString(3, c.getImagen());
        ps.setDouble(4, c.getPrecio());
        ps.setInt(5, c.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM carros WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
