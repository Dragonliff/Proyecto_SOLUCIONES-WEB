package Modelo;

import java.sql.Timestamp;

public class usuarios {
   private int idUsuario;
   private int idRol;
   private String nombreCompleto;
   private String usuario;
   private String contrasena;
   private String correo;
   private String telefono;
   private String estado;
   private Timestamp fechaRegistro;

    public usuarios(int idUsuario, int idRol, String nombreCompleto, String usuario, String contrasena, String correo, String telefono, String estado, Timestamp fechaRegistro) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

  
    
    
}
