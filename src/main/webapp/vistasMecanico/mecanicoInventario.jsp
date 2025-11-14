<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.herramientas" %>
<%@ page import="Modelo.usos_herramientas" %>
<%@ page import="java.util.List" %>

<%-- Inclusiones (usando la ruta absoluta para seguridad.jsp) --%>
<%@ include file="layoutMecanicos.jsp" %>
<%@ include file="/seguridad.jsp" %> 

<%
    // *****************************************************************
    // BLOQUE CORREGIDO: Declaración de Variables al inicio
    // *****************************************************************
    request.setAttribute("titulo", "Inventario y Registro de Uso de Herramientas");

    // Estas declaraciones son CRUCIALES para evitar el error "cannot be resolved to a variable"
    List<usos_herramientas> listaUsos = (List<usos_herramientas>) request.getAttribute("registrosUso");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
    
    // 🎯 NUEVA DECLARACIÓN: Lista de herramientas asignadas (cargada desde el Servlet)
    List<herramientas> herramientasAsignadas = (List<herramientas>) request.getAttribute("herramientasAsignadas");
%>

<div class="container-fluid">
    <h2 class="mb-4">Inventario de Herramientas</h2>

    <div class="card shadow-lg mb-5 p-4">
        <h3 class="card-title text-primary">📝 Registrar Uso de Herramienta</h3>
        
        <%-- Mostrar mensajes de feedback (variables ya declaradas) --%>
        <% if (mensaje != null) { %>
            <div class="alert alert-success"><%= mensaje %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <%-- CORRECCIÓN: Action del formulario usa el patrón de URL correcto --%>
        <form action="<%= request.getContextPath() %>/UsosHerramientasServlet" method="POST" class="row g-3">
            
            <div class="col-md-6">
                <label for="idHerramienta" class="form-label">Herramienta Usada (Lista Asignada):</label>
                
                <%-- 🎯 CAMBIO CRÍTICO: Reemplazo de INPUT por SELECT y llenado con datos del Servlet --%>
                <select id="idHerramienta" name="idHerramienta" class="form-select" required>
                    <option value="">-- Seleccione una herramienta --</option>
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
                <%-- FIN DEL SELECT --%>
            </div>

            <div class="col-md-6">
                <label for="horasUso" class="form-label">Tiempo de Uso (Horas o Decimal):</label>
                <input type="text" id="horasUso" name="horasUso" class="form-control" required placeholder="Ej: 0.75 (45 min) o 2.5">
            </div>

            <div class="col-12">
                <label for="observaciones" class="form-label">Condición Observada (Requisito Predictivo):</label>
                <textarea id="observaciones" name="observaciones" class="form-control" rows="2" required placeholder="Ej: Óptima, Desgaste leve en el mango, Requiere calibración."></textarea>
            </div>
            
            <div class="col-12">
                <input type="hidden" name="accion" value="RegistrarUso">
                <button type="submit" class="btn btn-primary w-100">Guardar Registro de Uso</button>
            </div>
        </form>
    </div>

    <h3 class="mt-5 mb-3">🕒 Historial de Registros de Uso</h3>
    <table class="table table-striped table-bordered shadow-sm">
        <thead class="table-dark">
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
                        <td><%= uso.getIdUso() %></td>
                        <td><%= uso.getIdHerramienta() %></td>
                        <td><%= String.format("%.2f", uso.getHorasUso()) %></td>
                        <td>
                            <% 
                                String obs = uso.getObservaciones().toLowerCase();
                                String badgeClass = "secondary";
                                // Lógica para asignar un color de badge
                                if (obs.contains("óptima")) {
                                    badgeClass = "success";
                                } else if (obs.contains("desgaste") || obs.contains("leve")) {
                                    badgeClass = "warning text-dark";
                                } else if (obs.contains("revisión") || obs.contains("dañado")) {
                                    badgeClass = "danger";
                                }
                            %>
                            <span class="badge bg-<%= badgeClass %>"><%= uso.getObservaciones() %></span>
                        </td>
                        <td><%= uso.getFecha() %></td>
                    </tr>
            <%  } 
            } else { %>
                <tr><td colspan="5" class="text-center text-muted">Aún no hay registros de uso en el sistema.</td></tr>
            <% } %>
        </tbody>
    </table>

</div>
</body>
</html>