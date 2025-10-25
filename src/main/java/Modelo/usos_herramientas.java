/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.Timestamp;

public class usos_herramientas {
   private int idUso;
   private int idHerramienta;
   private int idMecanico;
   private Timestamp fecha;
   private double horasUso;
   private String observaciones;

    public usos_herramientas(int idUso, int idHerramienta, int idMecanico, Timestamp fecha, double horasUso, String observaciones) {
        this.idUso = idUso;
        this.idHerramienta = idHerramienta;
        this.idMecanico = idMecanico;
        this.fecha = fecha;
        this.horasUso = horasUso;
        this.observaciones = observaciones;
    }

    public int getIdUso() {
        return idUso;
    }

    public void setIdUso(int idUso) {
        this.idUso = idUso;
    }

    public int getIdHerramienta() {
        return idHerramienta;
    }

    public void setIdHerramienta(int idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
 
}
