<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nombreUsuario = (String) session.getAttribute("nombreUsuario");
    String rolUsuario = "Mecánico";
    String tituloPagina = (String) request.getAttribute("titulo");
    if (tituloPagina == null) { tituloPagina = "Panel de Tareas"; }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= tituloPagina %></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            :root {
                --primary-color: #0077B6;
                --accent-color: #48CAE4;
                --bg-light: #F8F9FA;
                --text-dark: #343A40;
            }

            body {
                background-color: var(--bg-light);
                font-family: 'Inter', sans-serif;
                color: var(--text-dark);
                min-height: 100vh;
                display: flex;
                width: 90%;
                overflow-x: hidden;

            }

            /* 1. Estilos del Sidebar/Navegación */
            .sidebar {
                width: 280px;
                background-color: #FFFFFF;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                position: fixed;
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

            /* 2. Estilos del Contenido Principal */
            .main-content-wrapper { 
                margin-left: 280px;
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            .main-content {
                flex-grow: 1; 
                padding: 0; /* ¡CRUCIAL! Quitamos el padding global para que el contenido lo maneje */
            }
            
            /* 3. Estilos del Footer */
            .footer {
                background-color: #E9ECEF;
                padding: 15px;
                text-align: center;
                font-size: 0.85rem;
                color: #6C757D;
                width: 100%;
                margin-top: auto;
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
                    display: none;
                }
            }
        </style>
    </head>
    
    <body>
        <div class="d-flex w-50"> 
            
            <nav id="sidebar" class="sidebar">
                <div class="sidebar-header">
                    <span class="fs-4">⚙️ Resiliencia</span>
                </div>
                
                <ul class="nav flex-column mt-1">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/HerramientasMecanicoServlet" class="nav-link active">
                            <i class="bi bi-list-task"></i> Mis Asignaciones
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="mecanicoMantenimientos.jsp" class="nav-link">
                            <i class="bi bi-hammer"></i> Mantenimientos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="<%= request.getContextPath() %>/UsosHerramientasServlet?accion=Listar" class="nav-link">
                            <i class="bi bi-box-seam"></i> Inventario
                        </a>
                    </li>
                    <li class="nav-item mt-4 pt-2 border-top">
                        <a href="<%= request.getContextPath() %>/CerrarSesionServlet" class="nav-link text-danger">
                            <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                        </a>
                    </li>            
                </ul>
            </nav>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>