package Modelo;

public class proveedorvehiculo {

    private int id_proveedorVehiculo;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private double cotizacion;
    private String estado;

    public proveedorvehiculo() {
    }

    public proveedorvehiculo(int id_proveedorVehiculo, String nombre, String contacto, String telefono, String email, String direccion, double cotizacion, String estado) {
        this.id_proveedorVehiculo = id_proveedorVehiculo;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.cotizacion = cotizacion;
        this.estado = estado;
    }

    public int getId_proveedorVehiculo() {
        return id_proveedorVehiculo;
    }

    public void setId_proveedorVehiculo(int id_proveedorVehiculo) {
        this.id_proveedorVehiculo = id_proveedorVehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
