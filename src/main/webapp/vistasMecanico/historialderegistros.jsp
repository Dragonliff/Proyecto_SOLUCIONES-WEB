<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.usos_herramientas" %>
<%@ page import="java.util.List" %>
<%@ include file="../seguridad.jsp" %>
<%@ include file="layoutMecanicos.jsp" %>

<%
    request.setAttribute("titulo", "Historial de Uso de Herramientas");

    List<usos_herramientas> listaUsos = (List<usos_herramientas>) request.getAttribute("registrosUso");
%>

<div id="contenido-centrado" class="py-4 px-4">

    <h1 class="display-6 fw-bold mb-3 text-primary text-center">
        <i class="bi bi-clock-history me-2"></i>Historial de Registros de Uso
    </h1>

    <div class="table-responsive rounded-4 shadow-sm bg-white p-4">
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
                <% 
                        } 
                    } else { 
                %>
                    <tr><td colspan="5" class="text-center text-muted py-3">
                        <i class="bi bi-info-circle me-1"></i>No hay registros de uso.
                    </td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>