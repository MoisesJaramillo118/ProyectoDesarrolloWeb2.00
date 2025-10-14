package dao;

import dto.UsuarioDTO;
import servicios.ConectaDB;
import java.sql.*;

public class UsuarioDAO {
    public UsuarioDTO validarLogin(String correo, String contraseña) throws Exception {
        Connection cnx = ConectaDB.getConexion();
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setString(2, contraseña);
        ResultSet rs = ps.executeQuery();

        UsuarioDTO usuario = null;
        if (rs.next()) {
            usuario = new UsuarioDTO();
            usuario.setCorreo(rs.getString("correo"));
            usuario.setContraseña(rs.getString("contraseña"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setRol(rs.getString("rol"));
        }

        rs.close();
        ps.close();
        cnx.close();
        return usuario;
    }
}