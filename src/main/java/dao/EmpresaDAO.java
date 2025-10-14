package dao;

import dto.EmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import servicios.ConectaDB;

public class EmpresaDAO {

    // Obtener la empresa registrada (asumiendo que solo hay una)
    public EmpresaDTO obtenerEmpresa() throws SQLException {
        EmpresaDTO empresa = null;
        String sql = "SELECT * FROM empresa LIMIT 1";

        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                empresa = new EmpresaDTO();
                empresa.setId(rs.getInt("id"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setRazonSocial(rs.getString("razon_social"));
                empresa.setNombreComercial(rs.getString("nombre_comercial"));
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCorreo(rs.getString("correo"));

            }
        }
        return empresa;
    }

    // Insertar nueva empresa
    public boolean insertar(EmpresaDTO empresa) throws SQLException {
        String sql = "INSERT INTO empresa (ruc, razon_social, nombre_comercial, direccion, telefono, correo) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, empresa.getRuc());
            ps.setString(2, empresa.getRazonSocial());
            ps.setString(3, empresa.getNombreComercial());
            ps.setString(4, empresa.getDireccion());
            ps.setString(5, empresa.getTelefono());
            ps.setString(6, empresa.getCorreo());


            return ps.executeUpdate() > 0;
        }
    }

    // Actualizar empresa existente
    public boolean actualizar(EmpresaDTO empresa) throws SQLException {
        String sql = "UPDATE empresa SET ruc=?, razon_social=?, nombre_comercial=?, direccion=?, telefono=?, correo=? "
                   + "WHERE id=?";

        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, empresa.getRuc());
            ps.setString(2, empresa.getRazonSocial());
            ps.setString(3, empresa.getNombreComercial());
            ps.setString(4, empresa.getDireccion());
            ps.setString(5, empresa.getTelefono());
            ps.setString(6, empresa.getCorreo());
            ps.setInt(7, empresa.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // Verificar si existe empresa registrada
    public boolean existeEmpresa() throws SQLException {
        String sql = "SELECT COUNT(*) FROM empresa";
        try (Connection cn = ConectaDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
