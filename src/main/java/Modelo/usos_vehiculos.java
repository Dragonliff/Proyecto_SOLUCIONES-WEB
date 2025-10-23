/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.Timestamp;

/**
 *
 * @author Raidth
 */
public class usos_vehiculos {
    private int idUso;
    private int idVehiculo;
    private int idConductor;
    private Timestamp fecha;
    private double horasUso;
    private double kmRecorridos;
    private String observaciones;

    public usos_vehiculos(int idUso, int idVehiculo, int idConductor, Timestamp fecha, double horasUso, double kmRecorridos, String observaciones) {
        this.idUso = idUso;
        this.idVehiculo = idVehiculo;
        this.idConductor = idConductor;
        this.fecha = fecha;
        this.horasUso = horasUso;
        this.kmRecorridos = kmRecorridos;
        this.observaciones = observaciones;
    }

    public int getIdUso() {
        return idUso;
    }

    public void setIdUso(int idUso) {
        this.idUso = idUso;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getHorasUso() {
        return horasUso;
    }

    public void setHorasUso(double horasUso) {
        this.horasUso = horasUso;
    }

    public double getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(double kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
