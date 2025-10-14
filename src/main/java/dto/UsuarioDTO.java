package dto;

public class UsuarioDTO {
    private String correo;
    private String contraseña;
    private String nombre;
    private String rol;

    public UsuarioDTO() {}

    public UsuarioDTO(String correo, String contraseña, String nombre, String rol) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}