<%@page pageEncoding="UTF-8"%>
<%@page import="Modelo.SolicitudReemplazo"%>
<%@page import="Modelo.solicitudes_reemplazo_herramienta"%>
<%@page import="java.util.List"%>

<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    // Obtener listas y mensajes
    List<SolicitudReemplazo> listaC = (List<SolicitudReemplazo>) request.getAttribute("solicitudesConductores");
    List<solicitudes_reemplazo_herramienta> listaM = (List<solicitudes_reemplazo_herramienta>) request.getAttribute("solicitudesMecanicos");
    
    // Recuperar mensajes (se manejan en el Servlet y se ponen en el request)
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Gestión de Solicitudes de Reemplazo</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Replicando el estilo de Proveedores */
        body { background: #e6e7ea; }
        .section-wrapper {
            background: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            margin-bottom: 40px; /* Para separar las secciones si hay varias */
        }
        h2, h1 {
            font-weight: 700;
            color: #155aac;
        }
        .table thead {
            background: #155aac;
            color: white;
        }
        .btn-action-custom {
            padding: 0.2rem 0.5rem; /* Pequeño para que se parezca al btn-sm */
            font-size: 0.8rem;
            line-height: 1.5;
            border-radius: 0.2rem;
            margin-right: 3px; /* Separación entre botones */
        }
        img {
            max-width: 80px;
            height: auto;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<div class="container py-5">
    
    <% if (mensaje != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= mensaje %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% } %>
    
    <% if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% } %>

    <div class="section-wrapper">
        <h2 class="text-center mb-4">Solicitudes de Reemplazo</h2>

        <h3 class="mb-3">Solicitudes de Conductores</h3>

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
                    <th>Acciones</th> 
                </tr>
            </thead>

            <tbody>
            <% if (listaC != null && !listaC.isEmpty()) { 
                for (SolicitudReemplazo s : listaC) {
            %>
                <tr>
                    <td><%= s.getIdSolicitud() %></td>
                    <td><%= s.getIdConductor() %></td>
                    <td><%= s.getIdVehiculoActual() %></td>
                    <td><%= s.getMotivo() %></td>
                    <td><%= s.getDetalle() %></td>
                    <td>
                        <span class="badge bg-<%= s.getEstado().equals("Pendiente") ? "warning" : s.getEstado().equals("Aprobado") ? "success" : "danger" %>">
                            <%= s.getEstado() %>
                        </span>
                    </td>
                    <td><%= s.getFechaSolicitud() %></td>
                    <td class="text-center">
                        <% if (s.getImagen() != null && !s.getImagen().isEmpty()) { %>
                            <img src="uploads/<%= s.getImagen() %>" alt="Imagen" class="img-fluid">
                        <% } else { %>
                            <span class="text-muted small">Sin imagen</span>
                        <% } %>
                    </td>
                    
                    <td class="text-center">
                        <% if (s.getEstado().equals("Pendiente")) { %>
                            
                            <form action="AdminSolicitudesReemplazoServlet" method="POST" class="d-inline">
                                <input type="hidden" name="accion" value="cambiarEstado">
                                <input type="hidden" name="tipo" value="conductor">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <input type="hidden" name="nuevoEstado" value="Aprobado">
                                <button type="submit" class="btn btn-warning btn-sm btn-action-custom"
                                        title="Aprobar Solicitud"
                                        onclick="return confirm('¿Aprobar solicitud de Conductor ID <%= s.getIdSolicitud() %>?');">
                                    Aprobar
                                </button>
                            </form>
                            
                            <form action="AdminSolicitudesReemplazoServlet" method="POST" class="d-inline">
                                <input type="hidden" name="accion" value="cambiarEstado">
                                <input type="hidden" name="tipo" value="conductor">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <input type="hidden" name="nuevoEstado" value="Rechazado">
                                <button type="submit" class="btn btn-danger btn-sm btn-action-custom"
                                        title="Rechazar Solicitud"
                                        onclick="return confirm('¿Rechazar solicitud de Conductor ID <%= s.getIdSolicitud() %>?');">
                                    Rechazar
                                </button>
                            </form>

                        <% } else { %>
                            <span class="text-muted small">Finalizada</span>
                        <% } %>
                    </td>
                </tr>
            <%      }
                } else { %>
                <tr><td colspan="9" class="text-center text-muted">No hay solicitudes de conductores</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="section-wrapper mt-4">

        <h3 class="mb-3">Solicitudes de Mecánicos</h3>

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
                    <th>Acciones</th> 
                </tr>
            </thead>

            <tbody>
            <% if (listaM != null && !listaM.isEmpty()) { 
                for (solicitudes_reemplazo_herramienta s : listaM) {
            %>
                <tr>
                    <td><%= s.getIdSolicitud() %></td>
                    <td><%= s.getIdMecanico() %></td>
                    <td><%= s.getIdHerramienta() %></td>
                    <td><%= s.getMotivo() %></td>
                    <td><%= s.getDetalle() %></td>
                    <td>
                        <span class="badge bg-<%= s.getEstado().equals("Pendiente") ? "warning" : s.getEstado().equals("Aprobado") ? "success" : "danger" %>">
                            <%= s.getEstado() %>
                        </span>
                    </td>
                    <td><%= s.getFechaSolicitud() %></td>
                    <td class="text-center">
                        <% if (s.getImagen() != null && !s.getImagen().isEmpty()) { %>
                            <img src="uploads/<%= s.getImagen() %>" alt="Imagen" class="img-fluid">
                        <% } else { %>
                            <span class="text-muted small">Sin imagen</span>
                        <% } %>
                    </td>

                    <td class="text-center">
                         <% if (s.getEstado().equals("Pendiente")) { %>
                            <form action="AdminSolicitudesReemplazoServlet" method="POST" class="d-inline">
                                <input type="hidden" name="accion" value="cambiarEstado">
                                <input type="hidden" name="tipo" value="mecanico">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <input type="hidden" name="nuevoEstado" value="Aprobado">
                                <button type="submit" class="btn btn-warning btn-sm btn-action-custom"
                                        title="Aprobar Solicitud"
                                        onclick="return confirm('¿Aprobar solicitud de Mecánico ID <%= s.getIdSolicitud() %>?');">
                                    Aprobar
                                </button>
                            </form>
                            
                            <form action="AdminSolicitudesReemplazoServlet" method="POST" class="d-inline">
                                <input type="hidden" name="accion" value="cambiarEstado">
                                <input type="hidden" name="tipo" value="mecanico">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <input type="hidden" name="nuevoEstado" value="Rechazado">
                                <button type="submit" class="btn btn-danger btn-sm btn-action-custom"
                                        title="Rechazar Solicitud"
                                        onclick="return confirm('¿Rechazar solicitud de Mecánico ID <%= s.getIdSolicitud() %>?');">
                                    Rechazar
                                </button>
                            </form>
                        <% } else { %>
                            <span class="text-muted small">Finalizada</span>
                        <% } %>
                    </td>
                </tr>
            <%      }
                } else { %>
                <tr><td colspan="9" class="text-center text-muted">No hay solicitudes de mecánicos</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>