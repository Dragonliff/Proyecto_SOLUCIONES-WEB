<%@page import="Modelo.SolicitudReemplazo"%>
<%@page import="Modelo.solicitudes_reemplazo_herramienta"%>
<%@page import="Modelo.asignaciones_conductor_vehiculo" %>
<%@page import="java.util.List"%>

<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Solicitudes de Reemplazo</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body { background: #e6e7ea; }

        .section-wrapper {
            background: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            margin-bottom: 40px;
        }

        h2, h1 {
            font-weight: 700;
            color: #155aac;
            margin-bottom: 25px;
        }

        .table thead {
            background: #155aac;
            color: white;
        }

        img {
            border-radius: 8px;
        }
    </style>
</head>

<body>

<div class="container py-5">

    <div class="section-wrapper">
        <h1 class="text-center">Solicitudes de Reemplazo</h1>

        <!-- ========================================= -->
        <!--        SOLICITUDES DE CONDUCTORES         -->
        <!-- ========================================= -->
        <h2>Solicitudes de Conductores</h2>

        <table class="table table-bordered table-hover align-middle">
            <thead class="text-center">
                <tr>
                    <th>ID</th>
                    <th>Conductor</th>
                    <th>Vehículo</th>
                    <th>Motivo</th>
                    <th>Detalle</th>
                    <th>Estado</th>
                    <th>Fecha</th>
                    <th>Imagen</th>
                </tr>
            </thead>

            <tbody>
            <%
                List<SolicitudReemplazo> listaC =
                    (List<SolicitudReemplazo>) request.getAttribute("solicitudesConductores");

                if (listaC != null && !listaC.isEmpty()) {
                    for (SolicitudReemplazo s : listaC) {
            %>
                <tr>
                    <td><%= s.getIdSolicitud() %></td>
                    <td><%= s.getIdConductor() %></td>
                    <td><%= s.getIdVehiculoActual() %></td>
                    <td><%= s.getMotivo() %></td>
                    <td><%= s.getDetalle() %></td>
                    <td>
                        <span class="badge bg-<%= s.getEstado().equals("Pendiente") ? "warning" : "success" %>">
                            <%= s.getEstado() %>
                        </span>
                    </td>
                    <td><%= s.getFechaSolicitud() %></td>
                    <td>
                        <% if (s.getImagen() != null) { %>
                            <img src="uploads/<%= s.getImagen() %>" width="80">
                        <% } else { %>
                            <span class="text-muted">Sin imagen</span>
                        <% } %>
                    </td>
                </tr>
            <%      }
                } else { %>
                <tr><td colspan="8" class="text-center text-muted">No hay solicitudes</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>


    <div class="section-wrapper">

        <!-- ========================================= -->
        <!--        SOLICITUDES DE MECÁNICOS           -->
        <!-- ========================================= -->
        <h2>Solicitudes de Mecánicos</h2>

        <table class="table table-bordered table-hover align-middle">
            <thead class="text-center">
                <tr>
                    <th>ID</th>
                    <th>Mecánico</th>
                    <th>Herramienta</th>
                    <th>Motivo</th>
                    <th>Detalle</th>
                    <th>Estado</th>
                    <th>Fecha</th>
                    <th>Imagen</th>
                </tr>
            </thead>

            <tbody>
            <%
                List<solicitudes_reemplazo_herramienta> listaM =
                    (List<solicitudes_reemplazo_herramienta>) request.getAttribute("solicitudesMecanicos");

                if (listaM != null && !listaM.isEmpty()) {
                    for (solicitudes_reemplazo_herramienta s : listaM) {
            %>
                <tr>
                    <td><%= s.getIdSolicitud() %></td>
                    <td><%= s.getIdMecanico() %></td>
                    <td><%= s.getIdHerramienta() %></td>
                    <td><%= s.getMotivo() %></td>
                    <td><%= s.getDetalle() %></td>
                    <td>
                        <span class="badge bg-<%= s.getEstado().equals("Pendiente") ? "warning" : "success" %>">
                            <%= s.getEstado() %>
                        </span>
                    </td>
                    <td><%= s.getFechaSolicitud() %></td>
                    <td>
                        <% if (s.getImagen() != null) { %>
                            <img src="<%= s.getImagen() %>" width="80">
                        <% } else { %>
                            <span class="text-muted">Sin imagen</span>
                        <% } %>
                    </td>
                </tr>
            <%      }
                } else { %>
                <tr><td colspan="8" class="text-center text-muted">No hay solicitudes</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>