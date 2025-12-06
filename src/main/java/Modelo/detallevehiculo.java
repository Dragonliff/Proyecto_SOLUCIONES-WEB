package Modelo;

import java.security.Timestamp;

public class detallevehiculo {
    private int idDetalle;
    private int idVehiculo;
    private int idConductor;
    private Timestamp fecha;
    private String tipoCombustible;
    private double litros;
    private double precioLitro;
    private double kilometraje;
    private String observaciones;
    private String estadoGeneral;

    public detallevehiculo(int idDetalle, int idVehiculo, int idConductor, Timestamp fecha, String tipoCombustible, double litros, double precioLitro, double kilometraje, String observaciones, String estadoGeneral) {
        this.idDetalle = idDetalle;
        this.idVehiculo = idVehiculo;
        this.idConductor = idConductor;
        this.fecha = fecha;
        this.tipoCombustible = tipoCombustible;
        this.litros = litros;
        this.precioLitro = precioLitro;
        this.kilometraje = kilometraje;
        this.observaciones = observaciones;
        this.estadoGeneral = estadoGeneral;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
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

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public double getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(double precioLitro) {
        this.precioLitro = precioLitro;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

}
