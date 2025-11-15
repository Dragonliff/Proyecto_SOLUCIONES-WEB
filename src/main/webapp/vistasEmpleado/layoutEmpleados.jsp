<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "Panel Empleado" %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="css/barraNavegacion.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            :root {
                --primary-color: #0077B6; /* Azul Oscuro */
                --accent-color: #48CAE4; /* Azul Claro/Cian */
                --bg-light: #F8F9FA;
                --text-dark: #343A40;
            }

            /* Estilos del Body: Flexbox con ancho total y fondo claro */
            body {
                background-color: var(--bg-light);
                font-family: 'Inter', sans-serif;
                color: var(--text-dark);
                min-height: 100vh;
                display: flex;
                width: 100%; /* Cambiado de 90% a 100% para evitar scroll horizontal innecesario */
                overflow-x: hidden;
            }

            /* 1. Estilos del Sidebar/Navegación */
            .sidebar {
                width: 280px;
                background-color: #FFFFFF;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                position: fixed; /* Sidebar fijo */
                top: 0;
                left: 0;
                bottom: 0;
                padding: 0;
                overflow-y: auto;
                z-index: 1000;
            }

            .sidebar-header {
                background-color: var(--primary-color);
                color: #FFFFFF;
                padding: 20px 15px;
                text-align: center;
                font-weight: 700;
                font-size: 1.5rem;
            }

            .nav-link {
                color: var(--text-dark);
                padding: 12px 20px;
                transition: all 0.2s;
                border-left: 5px solid transparent;
            }

            .nav-link:hover {
                color: var(--primary-color);
                background-color: #E0F2F7;
            }

            /* Estilo para enlace activo (deberías aplicarlo con JSP/JSTL según la página actual) */
            .nav-link.active {
                color: #FFFFFF !important;
                background-color: var(--primary-color);
                border-left: 5px solid var(--accent-color);
                box-shadow: inset 3px 0 10px rgba(0, 0, 0, 0.1);
            }

            .nav-link.active i {
                color: var(--accent-color);
            }

            .sidebar .nav-link i {
                margin-right: 10px;
                font-size: 1.1rem;
            }
            
            /* Enlace de cerrar sesión separado visualmente */
            .logout-link {
                color: var(--text-dark) !important;
                border: 1px solid #ccc;
                margin-top: 2rem;
            }
            .logout-link:hover {
                background-color: #F8D7DA; /* Fondo rojo claro */
                color: #DC3545 !important;
            }


            /* 2. Estilos del Contenido Principal */
            .main-content-wrapper { 
                margin-left: 100px; /* Separación fija para dejar espacio al sidebar */
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            .main-content {
                flex-grow: 1; 
                padding: 30px; /* Le damos un padding general al contenido */
            }

            /* 4. Responsive (para móviles) */
            @media (max-width: 992px) {
                .sidebar {
                    width: 100%;
                    height: auto;
                    position: relative;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                }
                .main-content-wrapper {
                    margin-left: 0;
                }
                .main-content {
                    padding-top: 20px;
                }
                .sidebar-header {
                    display: block; /* Mantenemos el header visible en móvil */
                }
                .sidebar .nav-link {
                    border-left: none;
                }
            }
        </style>
    </head>
    
    <body>
        
        <div class="sidebar d-flex flex-column">
            
            <div class="sidebar-header">
                <i class="bi bi-person-workspace me-2"></i>
                Panel Empleado
            </div>
            
            <ul class="nav flex-column p-3 flex-grow-0">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/EmpleadoAsignacionesServlet" 
                       class="nav-link">
                       <i class="bi bi-box-seam"></i>Mis Máquinas
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/UsoVehiculoServlet?accion=reporte" 
                       class="nav-link">
                       <i class="bi bi-file-earmark-bar-graph"></i>Reporte
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/vistasEmpleado/empleadoMantenimientos.jsp" 
                       class="nav-link">
                       <i class="bi bi-tools"></i>Mantenimientos
                    </a>
                </li>
            </ul>
            
            <hr class="mx-3">

            <div class="p-3">
                <a href="<%= request.getContextPath() %>/CerrarSesionServlet" 
                   class="nav-link logout-link text-center">
                   <i class="bi bi-box-arrow-right"></i>Cerrar Sesión
                </a>
            </div>
            
            <div class="p-3 text-center text-muted small mt-auto">© 2025 Resiliencia</div>
        </div>
    </body>
</html>