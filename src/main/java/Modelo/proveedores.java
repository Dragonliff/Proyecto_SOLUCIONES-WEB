package Modelo;

public class proveedores {
    private int id_proveedor;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private double cotizacion;
    private String estado;
    private String fechaRegistro;

    public proveedores() {}

    public int getId_proveedor() { return id_proveedor; }
    public void setId_proveedor(int id_proveedor) { this.id_proveedor = id_proveedor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getCotizacion() { return cotizacion; }
    public void setCotizacion(double cotizacion) { this.cotizacion = cotizacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public void setId(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
