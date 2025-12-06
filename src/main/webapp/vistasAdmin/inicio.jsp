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

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <style>
        body {
            background: #eef1f6;
            font-family: 'Segoe UI', sans-serif;
        }

        h2.title {
            font-weight: 700;
            color: #2d3748;
        }

        .card {
            border: none;
            border-radius: 16px;
            background: white;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: transform .2s ease-in-out;
        }

        .card:hover {
            transform: translateY(-3px);
        }

        /* Indicadores */
        .card h5 {
            font-size: 15px;
            color: #4a5568;
            margin-bottom: 6px;
        }

        .card h2 {
            font-size: 32px;
            font-weight: bold;
        }

        /* Tablas */
        table thead tr {
            background: #2b6cb0;
            color: white;
        }

        table tbody tr:hover {
            background: #f1f5fb;
        }

        .chart-container {
            padding: 20px;
            background: white;
            border-radius: 16px;
            box-shadow: 0 4px 14px rgba(0,0,0,0.08);
        }

        .title {
            color: #2d3748;
            font-weight: 600;
        }
    </style>
</head>

<body>

    <div class="container mt-4">

        <h2 class="title mb-4">Dashboard General</h2>
        
        <form action="DashboardServlet" method="GET" class="row g-2 mb-4">
            <div class="col-md-3">
                <label>Desde:</label>
                <input type="date" name="inicio" value="<%= request.getAttribute("fechaInicio") %>" class="form-control">
            </div>

            <div class="col-md-3">
                <label>Hasta:</label>
                <input type="date" name="fin" value="<%= request.getAttribute("fechaFin") %>" class="form-control">
            </div>

            <div class="col-md-2 d-flex align-items-end">
                <button class="btn btn-primary w-100">Filtrar</button>
            </div>
        </form>

        <div class="row g-3">
            <div class="col-md-3">
                <div class="card p-4">
                    <h5>Total Vehículos</h5>
                    <h2 class="text-primary">
                        <%= request.getAttribute("totalVehiculos") != null 
                            ? request.getAttribute("totalVehiculos") 
                            : "0" %>
                    </h2>
                </div>
            </div>

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
                    
            <div class="col-md-3">
                <div class="card shadow-sm p-3">
                    <h5>Gasto de Combustible</h5>
                    <h2 class="text-danger">
                        S/. 
                        <%= request.getAttribute("gastoCombustible") != null 
                            ? request.getAttribute("gastoCombustible") 
                            : "0.00" %>
                    </h2>
                </div>
            </div>
        </div>

        <div class="row mt-4 g-3"> 

            <div class="col-md-6">
                <div class="card p-3 shadow-sm h-100">
                    <h5 class="title">Últimas Solicitudes</h5>

                    <table class="table table-hover mt-1 align-middle">
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
                            if (ultSolicitudes != null && !ultSolicitudes.isEmpty()) {
                                for (Object s : ultSolicitudes) {
                                    solicitudes_reemplazo_herramienta sol = (solicitudes_reemplazo_herramienta) s;
                        %>
                            <tr>
                                <td><%= sol.getIdSolicitud() %></td>
                                <td><%= sol.getIdMecanico() %></td>
                                <td><%= sol.getMotivo() %></td>
                                <td>
                                    <span class="badge bg-primary"><%= sol.getEstado() %></span>
                                </td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="4" class="text-center text-muted">Sin registros recientes</td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card p-3 shadow-sm h-100">
                    <h5 class="title">Últimas Solicitudes (Conductores)</h5>

                    <table class="table table-hover mt-1 align-middle">
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

                            if (ultCon != null && !ultCon.isEmpty()) {
                                for (SolicitudReemplazo sc : ultCon) {
                        %>
                            <tr>
                                <td><%= sc.getIdSolicitud() %></td>
                                <td><%= sc.getIdConductor() %></td>
                                <td><%= sc.getMotivo() %></td>
                                <td>
                                    <span class="badge bg-secondary"><%= sc.getEstado() %></span>
                                </td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr><td colspan="4" class="text-center text-muted">Sin registros recientes</td></tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
                    
        <div class="row mt-4 mb-5">
            <div class="col-md-12">
                <div class="chart-container">
                    <h4 class="title mb-3">Gasto de Combustible por Mes</h4>

                    <form method="GET" action="DashboardServlet" class="row g-2 mb-3">
                        <div class="col-md-3">
                            <label>Año:</label>
                            <select name="anio" class="form-control">
                                <%
                                    int anio = (request.getAttribute("anio") != null) ? (int) request.getAttribute("anio") : 2024;
                                    for (int a = 2023; a <= 2026; a++) {
                                %>
                                    <option value="<%=a%>" <%= (a == anio ? "selected" : "") %>>
                                        <%=a%>
                                    </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="col-md-2 d-flex align-items-end">
                            <button class="btn btn-success w-100">Ver</button>
                        </div>
                    </form>

                    <canvas id="graficoMeses" height="100"></canvas>
                </div>
            </div>
        </div>            

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
        // Validación de datos para evitar errores de JS si es null
        const gastosMes = <%= request.getAttribute("gastosMes") != null ? request.getAttribute("gastosMes") : "[0,0,0,0,0,0,0,0,0,0,0,0]" %>;

        const ctx = document.getElementById('graficoMeses').getContext('2d');

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 
                         'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                datasets: [{
                    label: 'Gasto de Combustible (S/.)',
                    data: gastosMes,
                    backgroundColor: 'rgba(99, 179, 237, 0.65)',
                    borderColor: 'rgba(30, 136, 229, 1)',
                    borderWidth: 2,
                    borderRadius: 6
                }]
            },
            options: {
                responsive: true,
                animation: {
                    duration: 1200,
                    easing: 'easeOutQuart'
                },
                scales: {
                    y: { beginAtZero: true },
                    x: { grid: { display: false } }
                }
            }
        });
    </script>
</body>
</html>