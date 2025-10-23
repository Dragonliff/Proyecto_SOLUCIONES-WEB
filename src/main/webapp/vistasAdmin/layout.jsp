<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "Sistema" %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/barraNavegacion.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    
    <body class="d-flex">
        <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark" style="width: 220px; height: 100vh;">
            <a href="index.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                <span class="fs-4">Resiliencia</span>
            </a>
            <hr>
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/vistasAdmin/inicio.jsp" class="nav-link sidebar-link text-white">Inicio</a>
                </li>
                <li>
                    <a href="<%= request.getContextPath() %>/EmpleadoServlet" class="nav-link sidebar-link text-white">Empleados</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/maquinas.jsp" class="nav-link sidebar-link text-white">Máquinas / Vehículos</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/horas.jsp" class="nav-link sidebar-link text-white">Horas de uso / Kilómetros</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/inventario.jsp" class="nav-link sidebar-link text-white">Inventario</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/mantenimientos.jsp" class="nav-link sidebar-link text-white">Mantenimientos</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/fallasReportadas.jsp" class="nav-link sidebar-link text-white">Fallas reportadas</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/alertasPredictivas.jsp" class="nav-link sidebar-link text-white">Alertas predictivas</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/reportes.jsp" class="nav-link sidebar-link text-white">Reportes</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/CerrarSesionServlet" class="nav-link sidebar-link text-white">Cerrar Sesión</a>
                </li>             
            </ul>
            <hr>
            <p class="text-white small">© 2025</p>
        </div>
    </body>
</html>
