/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class herramientas {
    private int idHerramienta;
    private String nombre;
    private String tipo;
    private String estado;
    private int idProveedor; 
    // --- NUEVAS PROPIEDADES PERMANENTES ---
    private double horasTotales; 

    // --- NUEVAS PROPIEDADES TRANSIENTES (solo para la vista) ---
    private double horasAcumuladas; 
    private String estadoAlerta;
   
    public herramientas(int idHerramienta, String nombre, String tipo, String estado, int idProveedor, double horasTotales) {
        this.idHerramienta = idHerramienta;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.idProveedor = idProveedor;
        this.horasTotales = horasTotales; // ¡AÑADIDO!
    }

  
    public herramientas(String nombre, String tipo, String estado, int idProveedor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.idProveedor = idProveedor;
        this.horasTotales = 0.0; // Se inicializa en 0.0 al crear
    }

    
    public herramientas() {}

    
    public int getIdHerramienta() {
        return idHerramienta;
    }

    public void setIdHerramienta(int idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    // --- Nuevos Getters y Setters ---

    public double getHorasTotales() {
        return horasTotales;
    }

    public void setHorasTotales(double horasTotales) {
        this.horasTotales = horasTotales;
    }

    public double getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(double horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public String getEstadoAlerta() {
        return estadoAlerta;
    }

    public void setEstadoAlerta(String estadoAlerta) {
        this.estadoAlerta = estadoAlerta;
    }
}
