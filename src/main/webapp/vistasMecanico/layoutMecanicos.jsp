<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "Panel Empleado" %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/barraNavegacion.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    
    <body class="d-flex">
        <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark" style="width: 220px; height: 100vh;">
            <a href="empleadoDashboard.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                <span class="fs-4">Resiliencia</span>
            </a>
            <hr>
            <ul class="nav nav-pills flex-column mb-auto">
                <li>
                    <a href="${pageContext.request.contextPath}/HerramientasMecanicoServlet" class="nav-link sidebar-link text-white">Fallas</a>
                </li>
                <li>
                    <a href="mecanicoMantenimientos.jsp" class="nav-link sidebar-link text-white">Mantenimientos</a>
                </li>
                <li>
                    <a href="<%= request.getContextPath() %>/UsosHerramientasServlet?accion=Listar" class="nav-link sidebar-link text-white">Inventario de Herramientas</a>
                </li>
                <li>
                    <a href="<%= request.getContextPath() %>/CerrarSesionServlet" class="nav-link sidebar-link text-white">Cerrar Sesión</a>
                </li>             
            </ul>
            <hr>
            <p class="text-white small">© 2025</p>
        </div>
    </body>
</html>