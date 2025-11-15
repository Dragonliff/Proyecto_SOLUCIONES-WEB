/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

public class asignaciones_mecanico_herramientas {
    private int idAsignacion;
    private int idMecanico;
    private int idHerramienta;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    
    private herramientas herramienta;
    
    public asignaciones_mecanico_herramientas() {
    }

    public asignaciones_mecanico_herramientas(int idAsignacion, int idMecanico, int idHerramienta, Date fechaInicio, Date fechaFin, String estado) {
        this.idAsignacion = idAsignacion;
        this.idMecanico = idMecanico;
        this.idHerramienta = idHerramienta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public herramientas getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(herramientas herramienta) {
        this.herramienta = herramienta;
    }

}
