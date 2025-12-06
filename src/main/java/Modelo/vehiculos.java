package Modelo;

public class vehiculos {
    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String tipoVehiculo;
    private double kilometrajeActual;
    private String estado;
    private double kmAcumulado;
    private String estadoAlerta;
   
    private int idProveedorVehiculo;
    private String proveedorNombre;
    
    public vehiculos(){
        
    }

    public vehiculos(int idVehiculo, String placa, String marca, String modelo, int anio, String tipoVehiculo, double kilometrajeActual, String estado) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.tipoVehiculo = tipoVehiculo;
        this.kilometrajeActual = kilometrajeActual;
        this.estado = estado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public double getKmAcumulado() {
    return kmAcumulado;
    }
    public void setKmAcumulado(double kmAcumulado) {
        this.kmAcumulado = kmAcumulado;
    }

    public String getEstadoAlerta() {
        return estadoAlerta;
    }
    public void setEstadoAlerta(String estadoAlerta) {
        this.estadoAlerta = estadoAlerta;
    }

    public int getIdProveedorVehiculo() {
        return idProveedorVehiculo;
    }

    public void setIdProveedorVehiculo(int idProveedorVehiculo) {
        this.idProveedorVehiculo = idProveedorVehiculo;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

}
