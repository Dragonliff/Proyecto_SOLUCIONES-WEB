<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("titulo", "Mis Máquinas");
%>
<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>
<div class="container-fluid">
    <h2 class="mb-4">Mis Máquinas</h2>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?truck" class="card-img-top" alt="Camión Volvo">
                <div class="card-body text-center">
                    <h5 class="card-title">Camión Volvo FH</h5>
                    <p><span class="badge bg-success">Activo</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="empleadoFallas.jsp" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                    <button class="btn btn-warning btn-sm ms-2">Cambiar Estado</button>
                    <div class="mt-3">
                        <small class="text-muted">Antigüedad: 35%</small>
                        <div class="progress">
                            <div class="progress-bar bg-info" style="width: 35%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?excavator" class="card-img-top" alt="Excavadora ZX200">
                <div class="card-body text-center">
                    <h5 class="card-title">Excavadora ZX200</h5>
                    <p><span class="badge bg-success">Operativa</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="empleadoFallas.jsp" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                    <button class="btn btn-warning btn-sm ms-2">Cambiar Estado</button>
                    <div class="mt-3">
                        <small class="text-muted">Antigüedad: 60%</small>
                        <div class="progress">
                            <div class="progress-bar bg-warning" style="width: 60%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?pickup,car" class="card-img-top" alt="Toyota Hilux">
                <div class="card-body text-center">
                    <h5 class="card-title">Pickup Toyota Hilux</h5>
                    <p><span class="badge bg-warning text-dark">En uso</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="empleadoFallas.jsp" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                    <button class="btn btn-warning btn-sm ms-2">Cambiar Estado</button>
                    <div class="mt-3">
                        <small class="text-muted">Antigüedad: 80%</small>
                        <div class="progress">
                            <div class="progress-bar bg-danger" style="width: 80%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div> 
</body>
</html>