<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    // Evitar cach� del navegador
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    
    HttpSession sesion = request.getSession(false);
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI().toLowerCase();
    
    // 1. Validar sesi�n
    if (sesion == null || sesion.getAttribute("idRol") == null) {
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }
    
    int idRol = 0;
    try {
        idRol = (int) sesion.getAttribute("idRol");
    } catch (ClassCastException e) {
        // En caso de error de tipo, se trata como sesi�n inv�lida.
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }

    // 2. Definir rutas a verificar (en min�sculas)
    boolean accedeAdmin = requestURI.contains("/vistasadmin/");
    boolean accedeMecanico = requestURI.contains("/vistasmecanico/");
    boolean accedeEmpleado = requestURI.contains("/vistasempleado/"); 

    // 3. L�gica de Restricci�n de Acceso
    boolean accesoDenegado = false;
    
    switch (idRol) {
        case 1: // ADMINISTRADOR
            // Solo puede acceder a /vistasAdmin/
            if (accedeMecanico || accedeEmpleado) {
                accesoDenegado = true;
            }
            break;

        case 2: // MEC�NICO
            // Solo puede acceder a /vistasMecanico/
            if (accedeAdmin || accedeEmpleado) {
                accesoDenegado = true;
            }
            break;

        case 3: // CONDUCTOR (Empleado)
            // Solo puede acceder a /vistasEmpleado/
            if (accedeAdmin || accedeMecanico) {
                accesoDenegado = true;
            }
            break;
            
        default:
            // Rol desconocido. Cerrar sesi�n por seguridad.
            response.sendRedirect(contextPath + "/index.jsp");
            return;
    }

    // 4. Redirecci�n si se deniega el acceso
    if (accesoDenegado) {
        response.sendRedirect(contextPath + "/accesoNoAutorizado.jsp");
        return;
    }
%>