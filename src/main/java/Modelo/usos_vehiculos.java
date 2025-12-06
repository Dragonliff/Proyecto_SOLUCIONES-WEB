package Modelo;

import java.sql.Timestamp;

public class usos_vehiculos {
    private int idUso;
    private int idVehiculo;
    private int idConductor;
    private Timestamp fecha;
    private double horasUso;
    private double kmRecorridos;
    private String descripcion;
    
    private String tipoCombustible;
    private Double litros;
    private Double precioLitro;
    private Double costoTotal;

    public usos_vehiculos(int idUso, int idVehiculo, int idConductor, Timestamp fecha, double horasUso, double kmRecorridos, String descripcion) {
        this.idUso = idUso;
        this.idVehiculo = idVehiculo;
        this.idConductor = idConductor;
        this.fecha = fecha;
        this.horasUso = horasUso;
        this.kmRecorridos = kmRecorridos;
        this.descripcion = descripcion;
    }

    public usos_vehiculos(int idUso, int idVehiculo, int idConductor, Timestamp fecha, double horasUso, double kmRecorridos, String descripcion, String tipoCombustible, Double litros, Double precioLitro, Double costoTotal) {
        this.idUso = idUso;
        this.idVehiculo = idVehiculo;
        this.idConductor = idConductor;
        this.fecha = fecha;
        this.horasUso = horasUso;
        this.kmRecorridos = kmRecorridos;
        this.descripcion = descripcion;
        this.tipoCombustible = tipoCombustible;
        this.litros = litros;
        this.precioLitro = precioLitro;
        this.costoTotal = costoTotal;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public Double getLitros() {
        return litros;
    }

    public void setLitros(Double litros) {
        this.litros = litros;
    }

    public Double getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(Double precioLitro) {
        this.precioLitro = precioLitro;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    
}
