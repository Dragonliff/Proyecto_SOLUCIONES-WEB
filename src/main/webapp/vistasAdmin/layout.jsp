<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "Sistema" %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="css/barraLateral.css"> 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    
    <body class="d-flex">
        <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark sidebar-pro" style="width: 250px; height: 100vh;">
            <a href="<%= request.getContextPath() %>/EmpleadoServlet" 
                class="d-flex align-items-center justify-content-center mb-2 text-decoration-none"
                style="padding: 5px 0;">

                 <img src="${pageContext.request.contextPath}/img/cronix.png"
                      alt="Gestión de Activos"
                      style="width: 150px; height: auto; display: block;">
             </a>

            <hr>
            
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/vistasAdmin/inicio.jsp" class="nav-link sidebar-link text-white active-link" aria-current="page">
                        <i class="bi bi-house-door-fill me-2"></i> Inicio
                    </a>
                </li>
                <li>
                    <a href="<%= request.getContextPath() %>/EmpleadoServlet" class="nav-link sidebar-link text-white">
                        <i class="bi bi-people-fill me-2"></i> Empleados
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/VehiculoServlet" class="nav-link sidebar-link text-white">
                        <i class="bi bi-truck-flatbed me-2"></i> Vehículos
                    </a>
                </li>
                
                <li>
                    <a href="<%= request.getContextPath() %>/HerramientaServlet" class="nav-link sidebar-link text-white">
                        <i class="bi bi-boxes me-2"></i> Herramientas
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/ProveedorServlet?accion=listar" class="nav-link sidebar-link text-white">
                       <i class="bi bi-truck me-2"></i> Proveedores
                   </a>
               </li>
               
               <li>
                    <a href="<%= request.getContextPath() %>/AdminSolicitudesReemplazoServlet?accion=listar"
                       class="nav-link sidebar-link text-white">
                        <i class="bi bi-file-earmark-text me-2"></i> Solicitudes de Reemplazo
                    </a>
                </li>

                
                <li>
                    <a href="${pageContext.request.contextPath}/AsignacionConductorVehiculoServlet?accion=listar" class="nav-link sidebar-link text-white">
                        <i class="bi bi-box-seam-fill me-2"></i> Asignar Vehiculo
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/AsignacionHerramientaServlet?accion=listar" class="nav-link sidebar-link text-white">
                        <i class="bi bi-tools me-2"></i> Asignar Herramienta
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/mantenimientos.jsp" class="nav-link sidebar-link text-white">
                        <i class="bi bi-wrench-adjustable-circle-fill me-2"></i> Mantenimientos
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/fallasReportadas.jsp" class="nav-link sidebar-link text-white">
                        <i class="bi bi-exclamation-octagon-fill me-2"></i> Fallas reportadas
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/alertasPredictivas.jsp" class="nav-link sidebar-link text-white">
                        <i class="bi bi-bell-fill me-2"></i> Alertas predictivas
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/vistasAdmin/reportes.jsp" class="nav-link sidebar-link text-white">
                        <i class="bi bi-file-earmark-bar-graph-fill me-2"></i> Reportes
                    </a>
                </li>
            </ul>
            <hr>
            <div>
                 <a href="${pageContext.request.contextPath}/CerrarSesionServlet" class="nav-link sidebar-link text-danger logout-link">
                    <i class="bi bi-box-arrow-right me-2"></i> Cerrar Sesión
                </a>
                <p class="text-secondary small mt-2 mb-0">© 2025 - Resiliencia</p>
            </div>
        </div>
        
        </body>
</html>