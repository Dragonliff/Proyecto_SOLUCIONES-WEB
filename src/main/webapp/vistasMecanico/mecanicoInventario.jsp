<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.herramientas" %>
<%@ page import="Modelo.usos_herramientas" %>
<%@ page import="java.util.List" %>
<%@ include file="../seguridad.jsp" %>
<%@ include file="layoutMecanicos.jsp" %>
<%

    request.setAttribute("titulo", "Inventario de Herramientas");


    List<usos_herramientas> listaUsos = (List<usos_herramientas>) request.getAttribute("registrosUso");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
    
    List<herramientas> herramientasAsignadas = (List<herramientas>) request.getAttribute("herramientasAsignadas");
%>


<div id="contenido-centrado" class="py-2 px-4"> 
    
    <div> 
        
        <h1 class="display-6 fw-bold mb-0 text-primary text-center">
            <i class="bi bi-tools me-2"></i>Registrar Reporte Uso
        </h1>
        <div class="border-bottom pb-4 mb-4"></div>


        <div class="card shadow-lg mb-5 p-5 rounded-4 border-0 bg-white">
            <h4 class="card-title text-success mb-4 border-bottom pb-2">
                <i class="bi bi-journal-check me-2"></i>Registrar Nuevo Uso de Herramienta
            </h4>
            
            <% if (mensaje != null) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i><%= mensaje %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>
            <% if (error != null) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-x-octagon-fill me-2"></i><%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/UsosHerramientasServlet" method="POST" class="row g-4">
                
                <div class="col-md-6">
                    <label for="idHerramienta" class="form-label fw-bold fs-5">Herramienta Usada:</label>
                    <select id="idHerramienta" name="idHerramienta" class="form-select form-select-lg rounded-3" required>
                        <option value="">-- Seleccione una herramienta asignada --</option>
                        <% 
                            if (herramientasAsignadas != null && !herramientasAsignadas.isEmpty()) {
                                for (herramientas h : herramientasAsignadas) {
                        %>
                                    <option value="<%= h.getIdHerramienta() %>">
                                        <%= h.getNombre() %> (ID: <%= h.getIdHerramienta() %>)
                                    </option>
                        <% 
                                }
                            } else {
                        %>
                                <option value="" disabled>No hay herramientas asignadas.</option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="horasUso" class="form-label fw-bold fs-5">Tiempo de Uso (Horas, Ej: 1.5):</label>
                    <input type="number" step="0.01" id="horasUso" name="horasUso" class="form-control form-control-lg rounded-3" required placeholder="Ingrese las horas de uso" min="0">
                </div>

                <div class="col-12">
                    <label for="observaciones" class="form-label fw-bold fs-5">Condición Observada (Detalle para Mantenimiento):</label>
                    <textarea id="observaciones" name="observaciones" class="form-control rounded-3" rows="3" required placeholder="Escriba aquí cualquier observación sobre la condición de la herramienta (desgaste, ruido, funcionamiento óptimo, etc.)"></textarea>
                </div>
                
                <div class="col-12 mt-4">
                    <input type="hidden" name="accion" value="RegistrarUso">
                    <button type="submit" class="btn btn-success btn-lg w-100 shadow-lg rounded-pill fw-bold text-white">
                        <i class="bi bi-hdd-fill me-2"></i>Guardar Registro de Uso
                    </button>
                </div>
            </form>
        </div>
        
        <h2 class="mt-5 mb-3 text-secondary">
            <i class="bi bi-clock-history me-2"></i>Historial de Registros de Uso Recientes
        </h2>
        <div class="table-responsive rounded-4 shadow-sm bg-white p-3">
            <table class="table table-hover align-middle">
                <thead class="table-primary border-bottom border-primary border-3">
                    <tr>
                        <th>ID Uso</th>
                        <th>ID Herramienta</th>
                        <th>Horas de Uso</th>
                        <th>Condición Observada</th>
                        <th>Fecha/Hora Registro</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        if (listaUsos != null && !listaUsos.isEmpty()) { 
                            for (usos_herramientas uso : listaUsos) { 
                    %>
                            <tr>
                                <td><span class="badge bg-light text-secondary border"><%= uso.getIdUso() %></span></td>
                                <td><%= uso.getIdHerramienta() %></td>
                                <td class="fw-bold text-info"><%= String.format("%.2f", uso.getHorasUso()) %> hrs</td>
                                <td>
                                    <% 
                                        String obs = uso.getObservaciones().toLowerCase();
                                        String badgeClass = "secondary";
                                        String icon = "bi-wrench";
                                        // Lógica para asignar un color de badge
                                        if (obs.contains("óptima") || obs.contains("buena") || obs.contains("sin problema")) {
                                            badgeClass = "success";
                                            icon = "bi-check-circle-fill";
                                        } else if (obs.contains("desgaste") || obs.contains("leve") || obs.contains("aceite") || obs.contains("calibración")) {
                                            badgeClass = "warning text-dark";
                                            icon = "bi-exclamation-triangle-fill";
                                        } else if (obs.contains("revisión") || obs.contains("dañado") || obs.contains("roto") || obs.contains("reparación")) {
                                            badgeClass = "danger";
                                            icon = "bi-x-octagon-fill";
                                        }
                                    %>
                                    <span class="badge bg-<%= badgeClass %> py-2 px-3 fw-normal">
                                        <i class="<%= icon %> me-1"></i><%= uso.getObservaciones() %>
                                    </span>
                                </td>
                                <td><%= uso.getFecha() %></td>
                            </tr>
                    <%  } 
                        } else { %>
                        <tr><td colspan="5" class="text-center text-muted py-3"><i class="bi bi-info-circle me-1"></i>No hay registros de uso en el historial.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>

    </div>
    
</div>