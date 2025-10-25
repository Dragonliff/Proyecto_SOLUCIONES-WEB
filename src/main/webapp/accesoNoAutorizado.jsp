<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Acceso Denegado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .access-denied {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="access-denied text-center">
            <h1 class="text-danger mb-4">🚫 Acceso Denegado</h1>
            <p class="lead">No cuentas con los permisos necesarios para acceder a esta sección.</p>
            
            <%
                HttpSession sesion = request.getSession(false);
                // Obtener el contextPath para construir rutas absolutas
                String contextPath = request.getContextPath();
                
                String volverUrl = contextPath + "/index.jsp"; // Ruta por defecto para deslogueado/fallo
                String btnTexto = "Volver a la página de inicio";

                // Obtener el ID de Rol (1=ADMIN, 2=CONDUCTOR/EMPLEADO, 3=MECÁNICO)
                Integer idRol = (sesion != null) ? (Integer) sesion.getAttribute("idRol") : null;

                if (idRol != null) {
                    if (idRol == 1) { // ADMINISTRADOR
                        // Ruta de inicio del Administrador según seguridad.jsp: /vistasAdmin/inicio.jsp
                        volverUrl = contextPath + "/vistasAdmin/inicio.jsp";
                        btnTexto = "Volver al Panel de Administración";
                    } else if (idRol == 2) { // CONDUCTOR (Empleado)
                        // Ruta de inicio del Conductor según seguridad.jsp: /vistasEmpleado/empleadoMaquinas.jsp
                        volverUrl = contextPath + "/vistasEmpleado/empleadoMaquinas.jsp";
                        btnTexto = "Volver a Mis Máquinas";
                    } else if (idRol == 3) { // MECÁNICO
                        // Ruta de inicio del Mecánico según seguridad.jsp: /vistasMecanico/mecanico.jsp
                        volverUrl = contextPath + "/vistasMecanico/mecanico.jsp";
                        btnTexto = "Volver al Panel de Mecánico";
                    } else {
                        // Rol válido pero desconocido en el switch
                        btnTexto = "Volver al inicio de sesión";
                    }
                }
            %>

            <div class="mt-4">
                <a href="<%= volverUrl %>" class="btn btn-primary btn-lg">
                    <%= btnTexto %>
                </a>
            </div>
        </div>
    </div>
</body>
</html>