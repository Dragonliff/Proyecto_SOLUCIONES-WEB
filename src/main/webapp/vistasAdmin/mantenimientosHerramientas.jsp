<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.herramientas" %>

<%
    request.setAttribute("titulo", "Mantenimiento de Herramientas");
%>

<%-- 
    El layout.jsp ya incluye las librerías de Bootstrap, la barra lateral y 
    probablemente abre las etiquetas <html> y <body>.
--%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%-- 
    AQUÍ DEBERÍA IR EL CONTENIDO PRINCIPAL, asumiendo que layout.jsp terminó la estructura
    de la cabecera (incluyendo <head> y librerías).
--%>

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
    
    .alerta-baja { background-color: #d4edda; color: #155724; } 
    .alerta-media { background-color: #fff3cd; color: #856404; } 
    .alerta-alta { background-color: #f8d7da; color: #721c24; }
</style>

<div class="container py-4">

    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-tools me-2"></i>Gestión de Mantenimiento de Herramientas
    </h2>

    <div class="card shadow-sm">
        <div class="card-body">

            <% 
                List<herramientas> lista = (List<herramientas>) request.getAttribute("lista");
                if (lista == null || lista.isEmpty()) { 
            %>
                <div class="alert alert-info text-center">No hay herramientas registradas o disponibles para mantenimiento.</div>
            <% 
                } else { 
            %>

            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tipo</th>
                        <th>Estado Actual</th>
                        <th>Horas Totales (General)</th>
                        <th>Horas Acumuladas (Alerta)</th>
                        <th>Alerta Mantenimiento</th>
                        <th>Acción</th>
                    </tr>
                </thead>

                <tbody class="text-center">
                <%    
                    for (herramientas h : lista) {

                        String alertaClass = "";
                        if (h.getEstadoAlerta() != null) {
                            if (h.getEstadoAlerta().contains("ALTA") || h.getEstadoAlerta().contains("URGENTE")) {
                                alertaClass = "alerta-alta";
                            } else if (h.getEstadoAlerta().contains("MEDIA")) {
                                alertaClass = "alerta-media";
                            } else {
                                alertaClass = "alerta-baja";
                            }
                        }

                        boolean puedeDarMantenimiento = h.getEstado().equals("Operativa") || h.getEstado().equals("En Uso");
                %>
                    <tr>
                        <td><%= h.getIdHerramienta() %></td>
                        <td><%= h.getNombre() %></td>
                        <td><%= h.getTipo() %></td>
                        <td><%= h.getEstado() %></td>
                        
                        <td><%= String.format("%,.2f", h.getHorasTotales()) %> hrs</td>
                        
                        <td>
                            <strong><%= String.format("%,.2f", h.getHorasAcumuladas()) %></strong> hrs
                        </td>
                        
                        <td class="<%= alertaClass %>">
                            <strong><%= h.getEstadoAlerta() != null ? h.getEstadoAlerta() : "N/D" %></strong>
                        </td>

                        <td>
                            <%-- CRUCIAL: Apunta al HistorialHerramientasServlet --%>
                            <form action="HistorialHerramientasServlet" method="GET" style="display:inline;"> 
                                
                                <input type="hidden" name="accion" value="realizarMantenimiento">
                                <input type="hidden" name="id" value="<%= h.getIdHerramienta() %>">
                                
                                <input type="hidden" name="horasAcumuladas" value="<%= h.getHorasAcumuladas() %>">
                                <input type="hidden" name="horasTotales" value="<%= h.getHorasTotales() %>">
                                
                                <button type="submit" 
                                        class="btn btn-sm <%= puedeDarMantenimiento ? "btn-primary" : "btn-secondary" %>"
                                        <%= puedeDarMantenimiento ? "" : "disabled" %>
                                        onclick="return confirm('¿Confirmar Mantenimiento para <%= h.getNombre() %>? Esto sumará <%= String.format("%,.2f", h.getHorasAcumuladas()) %> hrs al total y reiniciará el contador.');">
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

<%-- Cierre de las etiquetas HTML/Body iniciadas en el layout.jsp --%>
</body>
</html>