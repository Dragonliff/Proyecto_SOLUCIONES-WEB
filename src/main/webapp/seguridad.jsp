<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    HttpSession sesion = request.getSession(false);
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI().toLowerCase();
    
    // 1. Validar sesión
    if (sesion == null || sesion.getAttribute("idRol") == null) {
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }
    
    int idRol = 0;
    try {
        idRol = (int) sesion.getAttribute("idRol");
    } catch (ClassCastException e) {
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }

    // 2. Definir rutas a verificar 
    boolean accedeAdmin = requestURI.contains("/vistasadmin/");
    boolean accedeMecanico = requestURI.contains("/vistasmecanico/");
    boolean accedeEmpleado = requestURI.contains("/vistasempleado/"); 

    boolean accesoDenegado = false;
    
    switch (idRol) {
        case 1: // ADMINISTRADOR
            if (accedeMecanico || accedeEmpleado) {
                accesoDenegado = true;
            }
            break;

        case 2: // MECÁNICO
            if (accedeAdmin || accedeEmpleado) {
                accesoDenegado = true;
            }
            break;

        case 3: // CONDUCTOR 
            if (accedeAdmin || accedeMecanico) {
                accesoDenegado = true;
            }
            break;
            
        default:
            response.sendRedirect(contextPath + "/index.jsp");
            return;
    }

    if (accesoDenegado) {
        response.sendRedirect(contextPath + "/accesoNoAutorizado.jsp");
        return;
    }
%>