package dao;

import dto.ContactoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import servicios.ConectaDB;

public class ContactoDAO {

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
}
