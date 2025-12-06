<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../seguridad.jsp" %> 
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

    <!-- NAVBAR SUPERIOR -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <span class="navbar-brand">Panel de Administración</span>

            <div class="d-flex">
                <span class="text-white me-3">
                    Usuario: <b><%= session.getAttribute("nombreCompleto") %></b>
                </span>

                <a href="../ControladorLogout" class="btn btn-danger btn-sm">Salir</a>
            </div>
        </div>
    </nav>

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

            <!-- ALERTAS ACTIVAS -->
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Alertas Activas</h5>
                    <h2 class="text-danger">
                        <%= request.getAttribute("alertasActivas") != null 
                            ? request.getAttribute("alertasActivas") 
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
                                    // Cambia por tu clase "solicitudes_reemplazo"
                                    Modelo.solicitudes_reemplazo sol = (Modelo.solicitudes_reemplazo) s;
                        %>
                            <tr>
                                <td><%= sol.getIdSolicitud() %></td>
                                <td><%= sol.getIdConductor() %></td>
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

            <!-- ALERTAS RECIENTES -->
            <div class="col-md-6">
                <div class="card p-3 shadow-sm">
                    <h5 class="title">Alertas Recientes</h5>

                    <table class="table table-hover mt-3">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Herramienta</th>
                                <th>Mensaje</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            List<?> ultAlertas = (List<?>) request.getAttribute("ultimasAlertas");
                            if (ultAlertas != null) {
                                for (Object a : ultAlertas) {
                                    Modelo.alertas al = (Modelo.alertas) a;
                        %>
                            <tr>
                                <td><%= al.getIdAlerta() %></td>
                                <td><%= al.getIdHerramienta() %></td>
                                <td><%= al.getMensaje() %></td>
                            </tr>
                        <%       }
                            } else { %>
                            <tr>
                                <td colspan="3" class="text-center">No hay alertas</td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

    </div>

    <!-- JS Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
