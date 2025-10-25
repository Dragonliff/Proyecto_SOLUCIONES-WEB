<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("titulo", "Mis Máquinas");
%>
<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    HttpSession sesionUsuario = request.getSession(false);
    String nombreUsuario = (String) sesionUsuario.getAttribute("nombreUsuario");
    if (nombreUsuario == null) {
        nombreUsuario = "Conductor";
    }
%>

<div class="container-fluid">
    <h1 class="mb-4">Bienvenido, <%= nombreUsuario %> ️</h1>
    <h2 class="text-muted mb-4">Aquí puedes ver tus máquinas asignadas</h2>
    
    <div class="container mt-4">
        <h2 class="text-center mb-4">Mis Máquinas Asignadas</h2>

        <c:if test="${empty listaAsignaciones}">
            <div class="alert alert-warning text-center">No tienes máquinas asignadas actualmente.</div>
        </c:if>

        <c:forEach var="a" items="${listaAsignaciones}">
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-primary">
                        🚗 ${a.nombreVehiculo} (${a.placa})
                    </h5>
                    <p class="card-text mb-1"><strong>Marca:</strong> ${a.marca}</p>
                    <p class="card-text mb-1"><strong>Modelo:</strong> ${a.modelo}</p>
                    <p class="card-text mb-1"><strong>Año:</strong> ${a.anio}</p>
                    <hr>
                    <p class="card-text">
                        <strong>Fecha Inicio:</strong> ${a.fechaInicio} <br>
                        <strong>Fecha Fin:</strong> ${a.fechaFin} <br>
                        <strong>Estado:</strong> 
                        <span class="badge bg-${a.estado == 'Activa' ? 'success' : 'secondary'}">${a.estado}</span>
                    </p>
                </div>
            </div>
        </c:forEach>
    </div>


</div>

</div> 
</body>
</html>