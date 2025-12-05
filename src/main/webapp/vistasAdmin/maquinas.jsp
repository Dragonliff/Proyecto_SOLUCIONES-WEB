<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.vehiculos" %>

<%
    request.setAttribute("titulo", "Vehículos");
%>

<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Vehículos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #e5e6e7 !important;
        }

        .card {
            border-radius: 12px;
        }

        table tbody tr:hover {
            background: #eef2f7 !important;
            transition: 0.2s;
        }

        /* Estilos de Estado */
        .estado-operativo { color: #0a7c1f; font-weight: bold; }
        .estado-mantenimiento { color: #ff9800; font-weight: bold; }
        .estado-inactivo { color: #d00000; font-weight: bold; }
        
        /* Estilos de Alerta */
        .alerta-baja { background-color: #d4edda; color: #155724; } /* Verde pálido */
        .alerta-media { background-color: #fff3cd; color: #856404; } /* Amarillo pálido */
        .alerta-alta { background-color: #f8d7da; color: #721c24; } /* Rojo pálido */
    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-truck-front me-2"></i>Gestión de Vehículos
    </h2>

    <div class="d-flex justify-content-end mb-3">
        <button class="btn btn-primary shadow-sm"
                data-bs-toggle="modal"
                data-bs-target="#modalVehiculo"
                onclick="prepararFormulario(null)">
            <i class="bi bi-plus-circle"></i> Nuevo Vehículo
        </button>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">

            <% List<vehiculos> lista = (List<vehiculos>) request.getAttribute("vehiculos");
                if (lista == null || lista.isEmpty()) { %>

                <div class="alert alert-info text-center">No hay vehículos registrados actualmente.</div>

            <% } else { %>

            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Placa</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Año</th>
                        <th>Tipo</th>
                        <th>Kilometraje (km)</th>
                        <th>**Km Desde Mantenimiento**</th>
                        <th>**Alerta Mantenimiento**</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody class="text-center">
                <%    
                    for (vehiculos v : lista) {

                        String estadoClass = "bg-secondary";
                        if ("Operativo".equalsIgnoreCase(v.getEstado())) estadoClass = "bg-success";
                        else if ("En Mantenimiento".equalsIgnoreCase(v.getEstado())) estadoClass = "bg-warning text-dark";
                        else if ("Fuera de Servicio".equalsIgnoreCase(v.getEstado())) estadoClass = "bg-danger";
                        
                        // Lógica para asignar la clase de alerta
                        String alertaClass = "";
                        // Usamos startsWith para manejar los emojis (🔴, 🟡, 🟢)
                        if (v.getEstadoAlerta() != null) {
                            if (v.getEstadoAlerta().startsWith("ALTA") || v.getEstadoAlerta().startsWith("URGENTE")) {
                                alertaClass = "alerta-alta";
                            } else if (v.getEstadoAlerta().startsWith("MEDIA")) {
                                alertaClass = "alerta-media";
                            } else {
                                alertaClass = "alerta-baja";
                            }
                        }

                %>
                    <tr>
                        <td><%= v.getIdVehiculo() %></td>
                        <td><%= v.getPlaca() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getModelo() %></td>
                        <td><%= v.getAnio() %></td>
                        <td><%= v.getTipoVehiculo() %></td>
                        <td><%= String.format("%,.2f", v.getKilometrajeActual()) %></td>
                        
                        <td>
                            <strong><%= String.format("%,.0f", v.getKmAcumulado()) %></strong> km
                        </td>
                        
                        <td class="<%= alertaClass %>">
                            <strong><%= v.getEstadoAlerta() != null ? v.getEstadoAlerta() : "N/D" %></strong>
                        </td>

                        <td>
                            <span class="badge <%= estadoClass %>">
                                <%= v.getEstado() %>
                            </span>
                        </td>

                        <td>
                            <button class="btn btn-warning btn-sm me-1"
                                data-bs-toggle="modal"
                                data-bs-target="#modalVehiculo"
                                onclick="prepararFormulario(
                                    '<%= v.getIdVehiculo() %>',
                                    '<%= v.getPlaca() %>',
                                    '<%= v.getMarca() %>',
                                    '<%= v.getModelo() %>',
                                    '<%= v.getAnio() %>',
                                    '<%= v.getTipoVehiculo() %>',
                                    '<%= v.getKilometrajeActual() %>',
                                    '<%= v.getEstado() %>'
                                )">
                                <i class="bi bi-pencil-square"></i>
                            </button>

                            <% if ("Operativo".equalsIgnoreCase(v.getEstado())) { %>
                                <a href="VehiculoServlet?accion=eliminar&id=<%= v.getIdVehiculo() %>"
                                    class="btn btn-danger btn-sm"
                                    onclick="return confirm('¿Eliminar vehículo <%= v.getPlaca() %>?')">
                                     <i class="bi bi-trash3"></i>
                                </a>
                            <% } else { %>
                                <a href="VehiculoServlet?accion=activar&id=<%= v.getIdVehiculo() %>"
                                    class="btn btn-success btn-sm"
                                    onclick="return confirm('¿Activar vehículo <%= v.getPlaca() %>?')">
                                     <i class="bi bi-check-circle"></i>
                                </a>
                            <% } %>
                        </td>
                    </tr>
                <% } %>
                </tbody>

            </table>
            <% } %>

        </div>
    </div>
</div>

<div class="modal fade" id="modalVehiculo" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <form action="VehiculoServlet" method="POST">

                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="modalTitle">
                        <i class="bi bi-truck-front"></i> Registrar Vehículo
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body row g-3">

                    <input type="hidden" name="accion" value="guardar">
                    <input type="hidden" name="idVehiculo" id="idVehiculo">

                    <div class="col-md-6">
                        <label class="form-label">Placa:</label>
                        <input type="text" class="form-control" id="placaModal" name="placa" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Marca:</label>
                        <input type="text" class="form-control" id="marcaModal" name="marca" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Modelo:</label>
                        <input type="text" class="form-control" id="modeloModal" name="modelo" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Año:</label>
                        <input type="number" class="form-control" id="anioModal" name="anio" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Tipo:</label>
                        <select class="form-select" id="tipoVehiculoModal" name="tipoVehiculo" required>
                            <option value="Camioneta">Camioneta</option>
                            <option value="Camión">Camión</option>
                            <option value="Auto">Auto</option>
                            <option value="Motocicleta">Motocicleta</option>
                            <option value="Otro">Otro</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Kilometraje Actual (km):</label>
                        <input type="number" step="0.1" class="form-control" id="kilometrajeActualModal" name="kilometrajeActual" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Estado:</label>
                        <select class="form-select" id="estadoModal" name="estado">
                            <option value="Operativo">Operativo</option>
                            <option value="En Mantenimiento">En Mantenimiento</option>
                            <option value="Fuera de Servicio">Fuera de Servicio</option>
                        </select>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-save2"></i> Guardar
                    </button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Cancelar
                    </button>
                </div>

            </form>

        </div>
    </div>
</div>

<script>
function prepararFormulario(id, placa, marca, modelo, anio, tipo, km, estado) {

    document.getElementById("modalTitle").innerText = id ? "Editar Vehículo" : "Registrar Vehículo";

    document.getElementById("idVehiculo").value = id || "";
    document.getElementById("placaModal").value = placa || "";
    document.getElementById("marcaModal").value = marca || "";
    document.getElementById("modeloModal").value = modelo || "";
    document.getElementById("anioModal").value = anio || "";
    document.getElementById("kilometrajeActualModal").value = km || "";

    document.getElementById("tipoVehiculoModal").value = tipo || "Auto";
    document.getElementById("estadoModal").value = estado || "Operativo";

    document.getElementById("placaModal").readOnly = !!id;
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>