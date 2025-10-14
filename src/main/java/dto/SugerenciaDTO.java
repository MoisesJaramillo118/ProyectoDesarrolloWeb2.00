package dto;

import java.sql.Timestamp;

public class SugerenciaDTO {
    private int id;
    private String nombre;
    private String correo;
    private String asunto;
    private String mensaje;
    private Timestamp fecha;  // ⏰ Nuevo campo para la fecha

    public SugerenciaDTO() {
    }

    public SugerenciaDTO(int id, String nombre, String correo, String asunto, String mensaje, Timestamp fecha) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
