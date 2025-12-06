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
                        <th>Km Desde Mantenimiento</th>
                        <th>Alerta Mantenimiento</th>
                        <th>Estado</th>
                        <th>Accion</th>
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
                            <form action="VehiculoServlet" method="GET" style="display:inline;">
                                <input type="hidden" name="accion" value="realizarMantenimiento">
                                <input type="hidden" name="id" value="<%= v.getIdVehiculo() %>">
                                
                                <input type="hidden" name="kmAcumulado" value="<%= v.getKmAcumulado() %>">
                                <input type="hidden" name="kmActual" value="<%= v.getKilometrajeActual() %>">

                                <button type="submit" 
                                        class="btn btn-primary btn-sm"
                                        <% 
                                            // Deshabilitar si está Fuera de Servicio
                                            if (!"Operativo".equalsIgnoreCase(v.getEstado())) { 
                                                out.print("disabled");
                                            }
                                        %>
                                        onclick="return confirm('¿Confirmar Mantenimiento para <%= v.getPlaca() %>? Esto sumará <%= String.format("%,.0f", v.getKmAcumulado()) %> km al total y reiniciará el contador.')">
                                    <i class="bi bi-wrench"></i> Realizar Mantenimiento
                                </button>
                            </form>
                        </td>

                    </tr>
                <% } %>
                </tbody>

            </table>
            <% } %>

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