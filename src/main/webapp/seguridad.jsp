<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    // Evitar caché del navegador
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    HttpSession sesion = request.getSession(false);
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI();

    // Validar sesión activa
    if (sesion == null || sesion.getAttribute("idRol") == null) {
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }

    // Obtener rol (numérico)
    int idRol = (int) sesion.getAttribute("idRol");

    // Verificar rutas accedidas
    boolean accedeAdmin = requestURI.contains("/vistasAdmin/");
    boolean accedeConductor = requestURI.contains("/vistasEmpleado/");
    boolean accedeMecanico = requestURI.contains("/vistasMecanico/");

    // Lógica de restricción de acceso
    switch (idRol) {
        case 1: // ADMINISTRADOR
            if (accedeConductor || accedeMecanico) {
                response.sendRedirect(contextPath + "/vistasAdmin/inicio.jsp");
                return;
            }
            break;

        case 2: // CONDUCTOR
            if (accedeAdmin || accedeMecanico) {
                response.sendRedirect(contextPath + "/vistasEmpleado/empleadoMaquinas.jsp");
                return;
            }
            break;

        case 3: // MECÁNICO
            if (accedeAdmin || accedeConductor) {
                response.sendRedirect(contextPath + "/vistasMecanico/mecanico.jsp");
                return;
            }
            break;

        default:
            // Rol desconocido
            response.sendRedirect(contextPath + "/index.jsp");
            return;
    }
%>
