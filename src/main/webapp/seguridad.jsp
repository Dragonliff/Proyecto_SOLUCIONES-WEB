<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    // Evitar caché del navegador
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    HttpSession sesion = request.getSession(false);
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI();

    // 1. Validar sesión activa (si no hay sesión, ir a index.jsp)
    if (sesion == null || sesion.getAttribute("idRol") == null) {
        response.sendRedirect(contextPath + "/index.jsp");
        return;
    }

    // 2. Obtener rol (numérico)
    // Usamos Integer para manejar mejor un posible null o error, aunque aquí ya validamos
    Integer idRolObj = (Integer) sesion.getAttribute("idRol"); 
    int idRol = (idRolObj != null) ? idRolObj : 0; // 0 como rol por defecto desconocido

    // 3. Verificar rutas accedidas
    boolean accedeAdmin = requestURI.contains("/vistasAdmin/");
    boolean accedeConductor = requestURI.contains("/vistasEmpleado/"); 
    boolean accedeMecanico = requestURI.contains("/vistasMecanico/");
    
    // Bandera para indicar si el acceso está prohibido para el rol actual
    boolean accesoDenegado = false; 

    // 4. Lógica de restricción de acceso: ¿El ROL ACTUAL puede acceder a la URI solicitada?
    
    // CASO GENERAL: Si la URI actual NO pertenece a NINGÚN rol (p.ej. /otraRuta/), no se chequea aquí, 
    // pero si accede a una ruta específica, se valida:
    
    switch (idRol) {
        case 1: // ADMINISTRADOR
            // El Admin DEBE acceder a /vistasAdmin/. Si accede a otra vista, DENEGAR.
            if (accedeConductor || accedeMecanico) {
                accesoDenegado = true;
            }
            break;

        case 2: // CONDUCTOR
            // El Conductor DEBE acceder a /vistasEmpleado/. Si accede a otra vista, DENEGAR.
            if (accedeAdmin || accedeConductor) {
                accesoDenegado = true;
            }
            break;

        case 3: // MECÁNICO
            // El Mecánico DEBE acceder a /vistasMecanico/. Si accede a otra vista, DENEGAR.
            if (accedeAdmin || accedeMecanico) {
                accesoDenegado = true;
            }
            break;

        default:
            // Rol desconocido o no válido
            accesoDenegado = true; 
            break;
    }

    // 5. Redireccionar si el acceso es denegado
    if (accesoDenegado) {
        // **IMPORTANTE: Asegúrate de renombrar tu archivo a 'accesoNoAutorizado.jsp' (sin espacios)**
        response.sendRedirect(contextPath + "/accesoNoAutorizado.jsp"); 
        return;
    }

    // Si no se activó la bandera de denegación, el flujo continúa a la página solicitada.
%>