<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Modelo.asignaciones_conductor_vehiculo" %>

<%
    request.setAttribute("titulo", "Mis Máquinas");
    
    // Obtener nombre desde sesión
    String nombreUsuario = (String) session.getAttribute("nombreCompleto"); 
    if (nombreUsuario == null || nombreUsuario.isEmpty()) {
        nombreUsuario = (String) session.getAttribute("nombreUsuario"); 
    }
    if (nombreUsuario == null || nombreUsuario.isEmpty()) {
        nombreUsuario = "Conductor";
    }

    // Lista de máquinas asignadas
    List<asignaciones_conductor_vehiculo> listaAsignaciones = 
        (List<asignaciones_conductor_vehiculo>) request.getAttribute("listaAsignaciones");
%>

<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>

<style>
    .contenedor {
        width: 150%;
    }
</style>

<div class="contenedor pt-4 pb-5">

    <h1 class="mb-4">Bienvenido, <%= nombreUsuario %> 🧑‍✈️</h1>
    <h2 class="text-muted mb-4">Aquí puedes ver tus máquinas asignadas</h2>

    <div class="container mt-4">
        <h2 class="text-center mb-4">Mis Máquinas Asignadas</h2>

        <%
            if (listaAsignaciones == null || listaAsignaciones.isEmpty()) {
        %>
            <div class="alert alert-warning text-center">
                No tienes máquinas asignadas actualmente.
            </div>
        <%
            } else {
                for (asignaciones_conductor_vehiculo a : listaAsignaciones) {

                    String estado = a.getEstado();
                    String estadoClase = (estado != null && estado.equalsIgnoreCase("Activa"))
                                        ? "success" : "secondary";
        %>

            <div class="card mb-3 shadow-sm">
                <div class="card-body">

                    <h5 class="card-title text-primary">
                        Placa: <%= a.getPlaca() != null ? a.getPlaca() : "N/D" %>
                        <span class="text-secondary">
                            (<%= a.getTipoVehiculo() != null ? a.getTipoVehiculo() : "Tipo N/D" %>)
                        </span>
                    </h5>

                    <p class="card-text mb-1"><strong>Marca:</strong> 
                        <%= a.getMarca() != null ? a.getMarca() : "N/D" %>
                    </p>

                    <p class="card-text mb-1"><strong>Modelo:</strong> 
                        <%= a.getModelo() != null ? a.getModelo() : "N/D" %>
                    </p>

                    <p class="card-text mb-1"><strong>Año:</strong> <%= a.getAnio() %></p>

                    <hr>

                    <p class="card-text">
                        <strong>Fecha Inicio:</strong> 
                        <%= a.getFechaInicio() != null ? a.getFechaInicio() : "-" %> 
                        <br>

                        <strong>Fecha Fin:</strong> 
                        <%= a.getFechaFin() != null ? a.getFechaFin() : "-" %> 
                        <br>

                        <strong>Estado:</strong> 
                        <span class="badge bg-<%= estadoClase %>">
                            <%= estado != null ? estado : "N/D" %>
                        </span>
                    </p>

                </div>
            </div>

        <%
                }
            }
        %>

    </div>
</div>
