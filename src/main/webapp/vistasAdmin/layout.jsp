<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "Sistema de Mantenimiento" %></title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        
        <link rel="stylesheet" href="css/barraLateral.css"> 

        <style>
            
            body {
                min-height: 100vh;
                background-color: #eef1f6; 
                overflow-x: hidden; 
            }

            .sidebar-pro {
                width: 260px;             
                min-width: 260px;         
                height: 100vh;            
                position: sticky;         
                top: 0;                   
                left: 0;
                background-color: #212529; 
                overflow-y: auto;         
                z-index: 1000;            
                box-shadow: 4px 0 10px rgba(0,0,0,0.1); 
            }

            .sidebar-link {
                color: rgba(255, 255, 255, 0.8) !important;
                margin-bottom: 5px;
                transition: all 0.3s ease;
                border-radius: 8px;
                font-size: 0.95rem;
            }

            .sidebar-link:hover {
                background-color: rgba(255, 255, 255, 0.1);
                color: #fff !important;
                transform: translateX(5px); 
            }

            .nav-link.active, .active-link {
                background-color: #0d6efd !important; 
                color: #fff !important;
                font-weight: 600;
                box-shadow: 0 4px 6px rgba(13, 110, 253, 0.4);
            }

            .sidebar-pro::-webkit-scrollbar {
                width: 5px;
            }
            .sidebar-pro::-webkit-scrollbar-track {
                background: #212529;
            }
            .sidebar-pro::-webkit-scrollbar-thumb {
                background: #495057;
                border-radius: 10px;
            }
            .sidebar-pro::-webkit-scrollbar-thumb:hover {
                background: #6c757d;
            }
        </style>
    </head>
    
    <body class="d-flex">

        <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark sidebar-pro">
            
            <a href="<%= request.getContextPath() %>/DashboardServlet" 
               class="d-flex align-items-center justify-content-center mb-3 text-decoration-none"
               style="padding: 10px 0;">
                <img src="${pageContext.request.contextPath}/img/cronix.png"
                      alt="Cronix Logo"
                      style="width: 140px; height: auto; display: block; filter: drop-shadow(0px 0px 5px rgba(255,255,255,0.2));">
             </a>

            <hr class="text-secondary">
            
            <ul class="nav nav-pills flex-column mb-auto">
                
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/DashboardServlet" class="nav-link sidebar-link">
                        <i class="bi bi-speedometer2 me-2"></i> Dashboard
                    </a>
                </li>
                
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/EmpleadoServlet" class="nav-link sidebar-link">
                        <i class="bi bi-people-fill me-2"></i> Empleados
                    </a>
                </li>
                
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/VehiculoServlet" class="nav-link sidebar-link">
                        <i class="bi bi-truck-flatbed me-2"></i> Vehículos
                    </a>
                </li>
                
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/HerramientaServlet" class="nav-link sidebar-link">
                        <i class="bi bi-tools me-2"></i> Herramientas
                    </a>
                </li>
                
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/ProveedorServlet?accion=listar" class="nav-link sidebar-link">
                       <i class="bi bi-building-fill me-2"></i> Proveedores
                   </a>
               </li>
               
               <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/ProveedorVehiculoServlet"  class="nav-link sidebar-link">
                        <i class="bi bi-truck me-2"></i> Prov. Vehículos
                    </a>
                </li>

               <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/AdminSolicitudesReemplazoServlet?accion=listar" class="nav-link sidebar-link">
                        <i class="bi bi-file-earmark-text me-2"></i> Solicitudes
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/AsignacionConductorVehiculoServlet?accion=listar" class="nav-link sidebar-link">
                        <i class="bi bi-person-badge-fill me-2"></i> Asignar Vehículo
                    </a>
                </li>
                
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/AsignacionHerramientaServlet?accion=listar" class="nav-link sidebar-link">
                        <i class="bi bi-wrench me-2"></i> Asignar Herram.
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/historialGlobalServlet" class="nav-link sidebar-link">
                        <i class="bi bi-clock-history me-2"></i> Mant. Vehículos
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/HistorialHerramientasServlet" class="nav-link sidebar-link">
                        <i class="bi bi-clipboard-data-fill me-2"></i> Mant. Herram.
                    </a>
                </li>
                
            </ul>
            
            <hr class="text-secondary">
            
            <div class="mt-auto">
                 <a href="${pageContext.request.contextPath}/CerrarSesionServlet" class="nav-link sidebar-link text-danger fw-bold">
                    <i class="bi bi-box-arrow-right me-2"></i> Cerrar Sesión
                </a>
                <div class="text-center mt-3 text-secondary" style="font-size: 0.75rem;">
                    <small>© 2025 Resiliencia</small><br>
                    <small>Ver. 1.0.4</small>
                </div>
            </div>
        </div>
        
        </body>
</html>