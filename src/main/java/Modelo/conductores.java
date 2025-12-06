package Modelo;

import java.util.Date;

public class conductores {
    private int idConductor;
    private int idUsuario;
    private String licenciaConducir;
    private String categoriaLicencia;
    private Date fechaInicio;
    private String estado;

    public conductores(int idConductor, int idUsuario, String licenciaConducir, String categoriaLicencia, Date fechaInicio, String estado) {
        this.idConductor = idConductor;
        this.idUsuario = idUsuario;
        this.licenciaConducir = licenciaConducir;
        this.categoriaLicencia = categoriaLicencia;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }
    
    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public String getCategoriaLicencia() {
        return categoriaLicencia;
    }

    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
