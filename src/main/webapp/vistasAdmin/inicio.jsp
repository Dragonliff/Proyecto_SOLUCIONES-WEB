<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../seguridad.jsp" %> 
<%@ include file="layout.jsp" %>
<%@page import="Modelo.solicitudes_reemplazo_herramienta"%>
<%@page import="Modelo.SolicitudReemplazo" %>

<%@page import="java.util.List"%>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Administración</title>

    <!-- BOOTSTRAP -->
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <style>
        body {
            background: #f5f7fa;
        }
        .card {
            border-radius: 12px;
        }
        .title {
            font-weight: bold;
            color: #333;
        }
    </style>
</head>

<body>



    <!-- CONTENIDO -->
    <div class="container mt-4">

        <h2 class="title mb-4">Dashboard General</h2>

        <!-- TARJETAS DE INDICADORES -->
        <div class="row g-3">

            <!-- TOTAL VEHÍCULOS -->
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Total Vehículos</h5>
                    <h2 class="text-primary">
                        <%= request.getAttribute("totalVehiculos") != null 
                            ? request.getAttribute("totalVehiculos") 
                            : "0" %>
                    </h2>
                </div>
            </div>

            <!-- SOLICITUDES PENDIENTES -->
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Solicitudes Pendientes</h5>
                    <h2 class="text-warning">
                        <%= request.getAttribute("solicitudesPendientes") != null 
                            ? request.getAttribute("solicitudesPendientes") 
                            : "0" %>
                    </h2>
                </div>
            </div>

            <!-- HERRAMIENTAS DISPONIBLES -->
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Herramientas Disponibles</h5>
                    <h2 class="text-success">
                        <%= request.getAttribute("herramientasDisponibles") != null 
                            ? request.getAttribute("herramientasDisponibles") 
                            : "0" %>
                    </h2>
                </div>
            </div>
                    
                    <!-- NUEVO KPI: TOTAL SOLICITUDES DEL CONDUCTOR -->
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Solicitudes Conductores</h5>
                    <h2 class="text-info">
                        <%= request.getAttribute("totalSolicitudesConductor") != null 
                            ? request.getAttribute("totalSolicitudesConductor")
                            : "0" %>
                    </h2>
                </div>
            </div>


        </div>

        <!-- SECCIÓN DE TABLAS -->
        <div class="row mt-5">

            <!-- ÚLTIMAS SOLICITUDES -->
            <div class="col-md-6">
                <div class="card p-3 shadow-sm">
                    <h5 class="title">Últimas Solicitudes</h5>

                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Conductor</th>
                                <th>Motivo</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            List<?> ultSolicitudes = (List<?>) request.getAttribute("ultimasSolicitudes");
                            if (ultSolicitudes != null) {
                                for (Object s : ultSolicitudes) {

                                    solicitudes_reemplazo_herramienta sol = (solicitudes_reemplazo_herramienta) s;
                        %>
                            <tr>
                                <td><%= sol.getIdSolicitud() %></td>
                                <td><%= sol.getIdMecanico() %></td>
                                <td><%= sol.getMotivo() %></td>
                                <td><%= sol.getEstado() %></td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="4" class="text-center">Sin registros</td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
                        
                <!-- TABLA CONDUCTORES -->
        <div class="col-md-6">
            <div class="card p-3 shadow-sm">
                <h5 class="title">Últimas Solicitudes (Conductores)</h5>

                <table class="table table-striped mt-3">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Conductor</th>
                            <th>Motivo</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        List<SolicitudReemplazo> ultCon = 
                            (List<SolicitudReemplazo>) request.getAttribute("ultimasSolicitudesConductor");

                        if (ultCon != null) {
                            for (SolicitudReemplazo sc : ultCon) {
                    %>
                        <tr>
                            <td><%= sc.getIdSolicitud() %></td>
                            <td><%= sc.getIdConductor() %></td>
                            <td><%= sc.getMotivo() %></td>
                            <td><%= sc.getEstado() %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr><td colspan="4" class="text-center">Sin registros</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <!-- JS Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
