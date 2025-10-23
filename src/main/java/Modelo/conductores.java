/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Raidth
 */
public class conductores {
    private int idConductor;
    private int idUsuario;
    private String licenciaConducir;
    private String categoriaLicencia;
    private Date fechaInicio;
    private String telefono;
    private String estado;

    public conductores(int idConductor, int idUsuario, String licenciaConducir, String categoriaLicencia, Date fechaInicio, String telefono, String estado) {
        this.idConductor = idConductor;
        this.idUsuario = idUsuario;
        this.licenciaConducir = licenciaConducir;
        this.categoriaLicencia = categoriaLicencia;
        this.fechaInicio = fechaInicio;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public String getCategoriaLicencia() {
        return categoriaLicencia;
    }

    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

}
