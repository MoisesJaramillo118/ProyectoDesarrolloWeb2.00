package dto;

import java.sql.Timestamp;

public class EmpresaDTO {

    private int id;
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String correo;


    // 🔸 Constructor vacío (obligatorio para usar con DAO)
    public EmpresaDTO() {
    }

    // 🔸 Constructor opcional (útil si quieres crear objetos rápido)
    public EmpresaDTO(int id, String ruc, String razonSocial, String nombreComercial, String direccion,
                      String telefono, String correo) {
        this.id = id;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;

    }

    // 🔸 Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
