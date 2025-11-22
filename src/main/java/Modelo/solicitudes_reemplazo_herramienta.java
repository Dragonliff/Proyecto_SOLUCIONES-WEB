/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author kristhor
 */
public class solicitudes_reemplazo_herramienta {
    private int idSolicitud;
    private int idMecanico;
    private int idHerramienta;
    private String motivo;
    private String detalle;
    private String imagen;
    private String estado;
    private Timestamp fechaSolicitud;
    
    public solicitudes_reemplazo_herramienta() {}

    public solicitudes_reemplazo_herramienta(int idSolicitud, int idMecanico, int idHerramienta, String motivo, String detalle, String imagen, String estado, Timestamp fechaSolicitud) {
        this.idSolicitud = idSolicitud;
        this.idMecanico = idMecanico;
        this.idHerramienta = idHerramienta;
        this.motivo = motivo;
        this.detalle = detalle;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }

    public int getIdHerramienta() {
        return idHerramienta;
    }

    public void setIdHerramienta(int idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

}
