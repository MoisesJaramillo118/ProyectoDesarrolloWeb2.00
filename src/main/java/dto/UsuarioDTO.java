package dto;

public class UsuarioDTO {
    private int id;
    private String correo;
    private String contraseña;
    private String nombre;
    private String rol;

    public UsuarioDTO() {}

    public UsuarioDTO(int id, String correo, String contraseña, String nombre, String rol) {
        this.id = id;
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    // Método auxiliar para verificar si es admin
    public boolean esAdmin() {
        return "Admin".equalsIgnoreCase(this.rol);
    }
    
    // Método auxiliar para verificar si es usuario
    public boolean esUsuario() {
        return "Usuario".equalsIgnoreCase(this.rol);
    }
}