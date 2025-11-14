<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.asignaciones_mecanico_herramientas" %>
<%
    request.setAttribute("titulo", "Fallas Reportadas");
%>
<%@ include file="layoutMecanicos.jsp" %>
<%@ include file="../seguridad.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes Estadísticos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloFallas.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
    
    <div class="container mt-5">
        
        <h1 >
            👋 Bienvenido, <%= session.getAttribute("nombreUsuario") != null ? session.getAttribute("nombreUsuario") : "Mecánico" %>
        </h1>
        
        <h2 class="text-center mb-4">🔧 Mis Herramientas Asignadas</h2>

        <%
            List<asignaciones_mecanico_herramientas> herramientas = 
                (List<asignaciones_mecanico_herramientas>) request.getAttribute("herramientasAsignadas");
            if (herramientas == null || herramientas.isEmpty()) {
        %>
            <div class="alert alert-warning text-center">No tienes herramientas asignadas actualmente.</div>
        <% } else { %>
            <table class="table table-bordered table-hover">
                <thead class="table-dark text-center">
                    <tr>
                        <th>Herramienta</th>
                        <th>Estado</th>
                        <th>Fecha Inicio</th>
                    </tr>
                </thead>
                <tbody>
                <% for (asignaciones_mecanico_herramientas a : herramientas) { %>
                    <tr class="text-center">
                        <td><%= a.getHerramienta().getNombre() %></td>
                        <td><%= a.getEstado() %></td>
                        <td><%= a.getFechaInicio() %></td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } %>
    </div>

</body>
</html>