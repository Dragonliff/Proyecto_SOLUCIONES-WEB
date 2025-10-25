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
public class asignaciones_conductor_vehiculo {
    private int idAsignacion;
    private int idConductor;
    private int idVehiculo;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    
    public asignaciones_conductor_vehiculo() {}

    public asignaciones_conductor_vehiculo(int idAsignacion, int idConductor, int idVehiculo, Date fechaInicio, Date fechaFin, String estado) {
        this.idAsignacion = idAsignacion;
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
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

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
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

}
