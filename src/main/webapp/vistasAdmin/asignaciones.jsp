<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.*, Modelo.asignaciones_conductor_vehiculo, Modelo.conductores, Modelo.vehiculos" %>
<%
    List<asignaciones_conductor_vehiculo> listaAsignaciones = 
        (List<asignaciones_conductor_vehiculo>) request.getAttribute("listaAsignaciones");
    List<conductores> listaConductores = 
        (List<conductores>) request.getAttribute("listaConductores");
    List<vehiculos> listaVehiculos = 
        (List<vehiculos>) request.getAttribute("listaVehiculos");
%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Asignación de Conductores y Vehículos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #e6e7ea; 
        }
    </style>
</head>

<body>

<div class="container mt-4">
    <h2 class="text-center mb-4">Asignación de Vehículos</h2>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white fw-bold">
            Nueva Asignación
        </div>
        <div class="card-body">
            <form action="AsignacionConductorVehiculoServlet" method="post" class="row g-3">
                <input type="hidden" name="accion" value="crear">

                <div class="col-md-5">
                    <label class="form-label">Conductor:</label>
                    <select name="idConductor" class="form-select" required>
                        <option value="">-- Seleccionar --</option>
                        <% for (conductores c : listaConductores) { %>
                            <option value="<%= c.getIdConductor() %>">
                                ID <%= c.getIdConductor() %> - Usuario <%= c.getIdUsuario() %>
                            </option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-5">
                    <label class="form-label">Vehículo:</label>
                    <select name="idVehiculo" class="form-select" required>
                        <option value="">-- Seleccionar --</option>
                        <% for (vehiculos v : listaVehiculos) { %>
                            <option value="<%= v.getIdVehiculo() %>">
                                <%= v.getPlaca() %> - <%= v.getMarca() %> <%= v.getModelo() %>
                                (Estado: <%= v.getEstado() %>)
                            </option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-success w-100">
                        ➕ Asignar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-dark text-white fw-bold">
            Asignaciones Actuales
        </div>
        <div class="card-body">
            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Conductor</th>
                        <th>Vehículo</th>
                        <th>Fecha Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        if (listaAsignaciones != null && !listaAsignaciones.isEmpty()) {
                            for (asignaciones_conductor_vehiculo a : listaAsignaciones) {
                    %>
                        <tr>
                            <td><%= a.getIdAsignacion() %></td>
                            <td><%= a.getIdConductor() %></td>
                            <td><%= a.getIdVehiculo() %></td>
                            <td><%= a.getFechaInicio() != null ? a.getFechaInicio() : "-" %></td>
                            <td><%= a.getFechaFin() != null ? a.getFechaFin() : "-" %></td>
                            <td>
                                <% if ("Activa".equalsIgnoreCase(a.getEstado())) { %>
                                    <span class="badge bg-success">Activa</span>
                                <% } else { %>
                                    <span class="badge bg-secondary">Finalizada</span>
                                <% } %>
                            </td>
                            <td>
                                <% if ("Activa".equalsIgnoreCase(a.getEstado())) { %>
                                    <a href="AsignacionConductorVehiculoServlet?accion=finalizar&id=<%= a.getIdAsignacion() %>" 
                                       class="btn btn-warning btn-sm">
                                        🟡 Finalizar
                                    </a>
                                <% } %>
                                <a href="AsignacionConductorVehiculoServlet?accion=eliminar&id=<%= a.getIdAsignacion() %>" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('¿Seguro de eliminar esta asignación?')">
                                    🗑 Eliminar
                                </a>
                            </td>
                        </tr>
                    <% 
                            }
                        } else { 
                    %>
                        <tr>
                            <td colspan="7" class="text-center text-muted">
                                No hay asignaciones registradas.
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
