<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    List<Map<String, Object>> listaAsignaciones = (List<Map<String, Object>>) request.getAttribute("listaAsignaciones");
    List<Map<String, Object>> listaMecanicos = (List<Map<String, Object>>) request.getAttribute("listaMecanicos");
    List<Map<String, Object>> listaHerramientas = (List<Map<String, Object>>) request.getAttribute("listaHerramientas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Asignación de Herramientas a Mecánicos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container mt-4">
    <h2 class="text-center mb-4">🔧 Asignación de Herramientas a Mecánicos</h2>

    <!-- FORMULARIO -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white fw-bold">Nueva Asignación</div>
        <div class="card-body">
            <form action="AsignacionHerramientaServlet" method="post" class="row g-3">

                <div class="col-md-5">
                    <label class="form-label">Mecánico:</label>
                    <select name="idMecanico" class="form-select" required>
                        <option value="">-- Seleccionar mecánico --</option>
                        <% for (Map<String, Object> m : listaMecanicos) { %>
                            <option value="<%= m.get("idMecanico") %>">
                                <%= m.get("nombreCompleto") %> - <%= m.get("especialidad") %>
                            </option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-5">
                    <label class="form-label">Herramienta:</label>
                    <select name="idHerramienta" class="form-select" required>
                        <option value="">-- Seleccionar herramienta --</option>
                        <% for (Map<String, Object> h : listaHerramientas) { %>
                            <option value="<%= h.get("idHerramienta") %>">
                                <%= h.get("nombre") %> (<%= h.get("tipo") %>)
                            </option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-success w-100">➕ Asignar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- TABLA -->
    <div class="card shadow-sm">
        <div class="card-header bg-dark text-white fw-bold">Asignaciones Actuales</div>
        <div class="card-body">
            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Mecánico</th>
                    <th>Herramienta</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>Estado</th>
                    <th>Acciones</th> 
                </tr>
                </thead>
                <tbody>
                <% if (listaAsignaciones != null && !listaAsignaciones.isEmpty()) {
                       for (Map<String, Object> a : listaAsignaciones) { %>
                <tr>
                    <td><%= a.get("idAsignacion") %></td>
                    <td><%= a.get("mecanico") %></td>
                    <td><%= a.get("herramienta") %></td>
                    <td><%= a.get("fechaInicio") %></td>
                    <td><%= a.get("fechaFin") != null ? a.get("fechaFin") : "-" %></td>
                    <td>
                        <% if ("Activa".equalsIgnoreCase((String)a.get("estado"))) { %>
                            <span class="badge bg-success">Activa</span>
                        <% } else { %>
                            <span class="badge bg-secondary"><%= a.get("estado") %></span>
                        <% } %>
                    </td>
                    <td>
                        <% if ("Activa".equalsIgnoreCase((String)a.get("estado"))) { %>
                            <form action="AsignacionHerramientaServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="accion" value="finalizar">
                                <input type="hidden" name="idAsignacion" value="<%= a.get("idAsignacion") %>">
                                <button type="submit" class="btn btn-danger btn-sm"
                                        onclick="return confirm('¿Deseas finalizar esta asignación?');">
                                    Finalizar
                                </button>
                            </form>
                        <% } else { %>
                            <span class="text-muted">-</span>
                        <% } %>
                    </td>
                </tr>
                <% } } else { %>
                    <tr><td colspan="7" class="text-center text-muted">No hay asignaciones registradas</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
