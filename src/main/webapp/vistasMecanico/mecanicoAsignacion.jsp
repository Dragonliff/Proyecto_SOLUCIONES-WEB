<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.asignaciones_mecanico_herramientas" %>
<%
    request.setAttribute("titulo", "Mis Asignaciones");
%>
<%@ include file="layoutMecanicos.jsp" %>
<%@ include file="../seguridad.jsp" %>
<style>
    /* * El problema de centrado se corrige aquí: 
     * El contenedor ocupa el 100% del espacio asignado por el layout, 
     * y el padding crea los márgenes laterales.
     */
    .mechanic-dashboard {
        width: 150%; /* Asegura que el contenedor ocupe el 100% del espacio asignado */
    }
    
    /* Sección de Bienvenida */
    .header-section {
        background-color: #FFFFFF;
        border-left: 5px solid #0077B6;
        padding: 25px 35px; 
        margin-bottom: 40px;
        border-radius: 0.5rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        display: flex; 
        align-items: center;
    }

    .welcome-icon {
        font-size: 2.5rem;
        color: #48CAE4;
        margin-right: 15px;
    }

    .welcome-content {
        line-height: 1.2;
    }

    .welcome-title {
        color: #0077B6;
        font-weight: 600;
        font-size: 2rem;
        margin-bottom: 5px !important;
    }

    /* Tarjeta de Asignaciones */
    .tool-assignment-card {
        border: 1px solid #E9ECEF;
        border-radius: 0.5rem;
        box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
        overflow: hidden;
    }

    .tool-assignment-card .card-header {
        background: linear-gradient(90deg, #0077B6 0%, #48CAE4 100%);
        color: #FFFFFF;
        font-size: 1.25rem;
        font-weight: 700;
        padding: 1.2rem 1.5rem;
        border-bottom: none;
    }
    
    .card-header .bi {
        font-size: 1.5rem;
        margin-right: 10px;
        position: relative;
        top: -2px; 
    }

    /* Tabla de Asignaciones */
    .assignment-table {
        margin-bottom: 0;
        border-collapse: separate; 
        border-spacing: 0;
    }
    
    .assignment-table thead th {
        background-color: #F1F3F5;
        color: #495057;
        font-weight: 600;
        border-bottom: 2px solid #DEE2E6;
    }
    
    .assignment-table tbody tr:hover {
        background-color: #E0F2F7;
        cursor: default;
    }
    
    /* Badges de Estado */
    .badge-style {
        padding: 0.4em 0.8em;
        font-size: 0.8em;
        font-weight: 700;
        border-radius: 50rem;
        display: inline-block;
    }
    
    .badge-asignada { background-color: #FFC107; color: #343A40; }
    .badge-en-uso { background-color: #17A2B8; color: #FFFFFF; }
    .badge-disponible { background-color: #28A745; color: #FFFFFF; }
    
</style>

    
<div class="mechanic-dashboard pt-4 pb-5"> 
    
    <div class="header-section shadow-sm">
        <i class="bi bi-emoji-smile welcome-icon"></i>
        <div class="welcome-content">
            <h1 class="welcome-title">
                Bienvenido, <%= session.getAttribute("nombreUsuario") != null ? session.getAttribute("nombreUsuario") : "Mecánico" %>
            </h1>
            <p class="lead text-muted mb-0">Revisa tus herramientas y recursos para el día de hoy.</p>
        </div>
    </div>
    
    <div class="card tool-assignment-card">
        <div class="card-header">
            <h2 class="card-title mb-0">
                <i class="bi bi-wrench"></i> Mis Herramientas Asignadas
            </h2>
        </div>
        <div class="card-body p-0">

        <%
            List<asignaciones_mecanico_herramientas> herramientas = 
                (List<asignaciones_mecanico_herramientas>) request.getAttribute("herramientasAsignadas");
            if (herramientas == null || herramientas.isEmpty()) {
        %>
            <div class="alert alert-info border-left-info text-center p-3 rounded-2 m-4">No tienes herramientas asignadas actualmente.</div>
        <% } else { %>
            <div class="table-responsive">
                <table class="table assignment-table text-center">
                    <thead>
                        <tr>
                            <th class="py-3">Herramienta</th>
                            <th class="py-3">Estado</th>
                            <th class="py-3">Fecha Inicio</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (asignaciones_mecanico_herramientas a : herramientas) { %>
                        <tr>
                            <td class="tool-name align-middle"><%= a.getHerramienta().getNombre() %></td>
                            <td class="align-middle">
                                <%
                                    String estado = a.getEstado();
                                    String badgeClass = "";
                                    if ("Activa".equals(estado)) { 
                                        badgeClass = "badge-disponible";
                                    } else if ("En Uso".equals(estado)) {
                                        badgeClass = "badge-en-uso";
                                    } else if ("Asignada".equals(estado)) {
                                        badgeClass = "badge-asignada";
                                    } else {
                                        badgeClass = "bg-secondary text-white";
                                    }
                                %>
                                <span class="badge-style <%= badgeClass %>"><%= estado %></span>
                            </td>
                            <td class="align-middle"><%= a.getFechaInicio() %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        <% } %>
        </div>
    </div>
</div>