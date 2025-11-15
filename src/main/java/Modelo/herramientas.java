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
    
    public herramientas(int idHerramienta, String nombre, String tipo, String estado) {
        this.idHerramienta = idHerramienta;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }
    
    public herramientas() {
    }

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

}
