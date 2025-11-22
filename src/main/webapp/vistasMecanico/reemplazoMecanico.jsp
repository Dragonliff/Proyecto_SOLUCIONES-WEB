<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.herramientas" %>
<%@ page import="Modelo.solicitudes_reemplazo_herramienta" %>
<%@ include file="layoutMecanicos.jsp" %>
<%@ include file="../seguridad.jsp" %>

<div class="container mt-4">

    <h2 class="mb-4 text-center">Solicitud de Reemplazo de Herramienta</h2>

    <!-- FORMULARIO -->
    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            Registrar nueva solicitud
        </div>
        <div class="card-body">

            <form action="${pageContext.request.contextPath}/ReemplazoHerramientaServlet" 
                  method="POST" enctype="multipart/form-data">

                <input type="hidden" name="accion" value="crear">

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label>Herramienta</label>
                        <select class="form-control" name="idHerramienta" required>
                            <option value="">Seleccione una herramienta</option>

                            <%
                                List<Modelo.asignaciones_mecanico_herramientas> asignadas =
                                    (List<Modelo.asignaciones_mecanico_herramientas>) request.getAttribute("herramientasAsignadas");

                                if (asignadas != null) {
                                    for (Modelo.asignaciones_mecanico_herramientas asg : asignadas) {
                                        Modelo.herramientas h = asg.getHerramienta();
                            %>

                            <option value="<%= h.getIdHerramienta() %>">
                                <%= h.getNombre() %> - <%= h.getTipo() %> (Asignada)
                            </option>

                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label>Motivo</label>
                        <input type="text" name="motivo" class="form-control" placeholder="Ej: Herramienta dañada" required>
                    </div>
                </div>


                <div class="mb-3">
                    <label>Detalle</label>
                    <textarea class="form-control" name="detalle" rows="3" placeholder="Explique el problema..."></textarea>
                </div>

                <div class="mb-3">
                    <label>Imagen (opcional)</label>
                    <input type="file" name="imagen" class="form-control">
                </div>

                <button class="btn btn-success">Enviar solicitud</button>

            </form>

        </div>
    </div>

    <!-- HISTORIAL -->
    <h3 class="mt-4 mb-3">Historial de solicitudes</h3>

    <div class="card shadow">
        <div class="card-body">

            <table class="table table-bordered table-striped">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Herramienta</th>
                        <th>Motivo</th>
                        <th>Imagen</th>
                        <th>Estado</th>
                        <th>Fecha</th>
                    </tr>
                </thead>

                <tbody>
                    <%
                        List<Modelo.solicitudes_reemplazo_herramienta> lista =
                                (List<Modelo.solicitudes_reemplazo_herramienta>) request.getAttribute("listaSolicitudes");

                        if (lista != null && !lista.isEmpty()) {

                            for (Modelo.solicitudes_reemplazo_herramienta s : lista) {
                    %>

                    <tr class="text-center">
                        <td><%= s.getIdSolicitud() %></td>

                        <td><%= s.getIdHerramienta() %></td> <!-- Puedes reemplazar por nombre -->

                        <td><%= s.getMotivo() %></td>

                        <td>
                            <% if (s.getImagen() != null) { %>
                                <img src="<%=request.getContextPath() + "/" + s.getImagen()%>" 
                                     width="70" class="img-thumbnail">
                            <% } else { %>
                                <span class="text-muted">Sin imagen</span>
                            <% } %>
                        </td>

                        <td>
                            <% String est = s.getEstado(); %>

                            <% if ("Pendiente".equals(est)) { %>
                                <span class="badge bg-warning text-dark">Pendiente</span>
                            <% } else if ("Aprobada".equals(est)) { %>
                                <span class="badge bg-success">Aprobada</span>
                            <% } else { %>
                                <span class="badge bg-danger">Rechazada</span>
                            <% } %>
                        </td>

                        <td><%= s.getFechaSolicitud() %></td>
                    </tr>

                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">
                            No hay solicitudes registradas.
                        </td>
                    </tr>
                    <% } %>

                </tbody>
            </table>

        </div>
    </div>

</div>
