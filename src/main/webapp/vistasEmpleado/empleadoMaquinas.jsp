<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Modelo.asignaciones_conductor_vehiculo" %>

<%
    request.setAttribute("titulo", "Mis Máquinas");
    
    String nombreUsuario = (String) session.getAttribute("nombreCompleto"); 
    if (nombreUsuario == null || nombreUsuario.isEmpty()) {
        nombreUsuario = (String) session.getAttribute("nombreUsuario"); 
    }
    if (nombreUsuario == null || nombreUsuario.isEmpty()) {
        nombreUsuario = "Conductor";
    }

    List<asignaciones_conductor_vehiculo> listaAsignaciones = 
        (List<asignaciones_conductor_vehiculo>) request.getAttribute("listaAsignaciones");
%>

<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>

<style>
    .contenedor {
        width: 150%;
    }
    .vehicle-card {
        width: 350px;          
        background: #fff;
        border-radius: 14px;
        padding: 26px 30px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    }

    .vehicle-top {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .vehicle-left {
        display: flex;
        align-items: center;
    }

    .vehicle-icon {
        width: 52px;
        height: 52px;
        background: #eef4ff;
        border-radius: 12px;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 30px;
        color: #4f6df5;
    }

    .vehicle-title {
        margin-left: 15px;
    }

    .vehicle-title h5 {
        margin: 0;
        font-weight: 700;
        font-size: 20px;
        color: #000;
    }

    .vehicle-sub {
        font-size: 14px;
        color: #6c757d;
    }

    .vehicle-status {
        font-size: 14px;
        padding: 5px 14px;
        border-radius: 20px;
        font-weight: 600;
    }

    .field-label {
        font-size: 13px;
        color: #7a7a7a;
        margin-bottom: 2px;
    }

    .field-value {
        font-weight: 700;
        font-size: 15px;
        color: #000;
        margin-bottom: 14px;
    }

    .vehicle-info {
        margin-top: 22px;
        display: flex;
        justify-content: space-between;
    }

    .vehicle-info-col {
        width: 48%;
    }
    
    .vehicle-list {
        display: flex;
        flex-wrap: wrap;    
        gap: 20px;            
        justify-content: center; 
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
        %>

        <div class="vehicle-list">
        <%
                for (asignaciones_conductor_vehiculo a : listaAsignaciones) {

                    String estado = a.getEstado();
                    String estadoClase = (estado != null && estado.equalsIgnoreCase("Activa"))
                                        ? "success" : "secondary";
                        
                    String icono = "🚚"; 
                if (a.getTipoVehiculo() != null) {
                    switch (a.getTipoVehiculo().toLowerCase()) {
                        case "camioneta":
                            icono = "🚙";
                            break;
                        case "camión":
                        case "camion":
                            icono = "🚛";
                            break;
                        case "auto":
                            icono = "🚗";
                            break;
                        case "moto":
                            icono = "🏍️";
                            break;
                        case "maquinaria":
                            icono = "🚜";
                            break;
                    }
                }
            %>

            <div class="vehicle-card">

                <div class="vehicle-top">

                    <div class="vehicle-left">
                        <div class="vehicle-icon"><%= icono %></div>

                        <div class="vehicle-title">
                            <h5><%= a.getPlaca() %></h5>
                            <div class="vehicle-sub">
                                <%= a.getTipoVehiculo() %>
                            </div>
                        </div>
                    </div>

                    <div>
                        <span class="vehicle-status badge bg-<%= estadoClase %>">
                            <%= a.getEstado() %>
                        </span>
                    </div>

                </div>

                <div class="vehicle-info">

                    <div class="vehicle-info-col">
                        <div class="field-label">Marca</div>
                        <div class="field-value"><%= a.getMarca() %></div>

                        <div class="field-label">Modelo</div>
                        <div class="field-value"><%= a.getModelo() %></div>

                        <div class="field-label">Año</div>
                        <div class="field-value"><%= a.getAnio() %></div>
                    </div>

                    <div class="vehicle-info-col">
                        <div class="field-label">Fecha Inicio</div>
                        <div class="field-value"><%= a.getFechaInicio() %></div>

                        <div class="field-label">Fecha Fin</div>
                        <div class="field-value">
                            <%= a.getFechaFin() != null ? a.getFechaFin() : "-" %>
                        </div>
                    </div>

                </div>

            </div>


        <%
                }
            }
        %>

    </div>
</div>
