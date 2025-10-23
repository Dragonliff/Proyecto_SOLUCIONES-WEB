<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("titulo", "Inventario de Herramientas");
%>
<%@ include file="layoutEmpleados.jsp" %>

<div class="container-fluid">
    <h2 class="mb-4">Inventario de Herramientas</h2>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?drill,tool" class="card-img-top" alt="Taladro Bosch">
                <div class="card-body text-center">
                    <h5 class="card-title">Taladro Bosch</h5>
                    <p><span class="badge bg-success">Disponible</span></p>
                </div>
                <div class="card-footer text-muted">
                    Último uso: 12/09/2025
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?wrench,tool" class="card-img-top" alt="Juego de Llaves">
                <div class="card-body text-center">
                    <h5 class="card-title">Juego de Llaves</h5>
                    <p><span class="badge bg-warning text-dark">En uso</span></p>
                </div>
                <div class="card-footer text-muted">
                    Asignado a: Toyota Hilux
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?hammer,tool" class="card-img-top" alt="Martillo Stanley">
                <div class="card-body text-center">
                    <h5 class="card-title">Martillo Stanley</h5>
                    <p><span class="badge bg-danger">Dañado</span></p>
                </div>
                <div class="card-footer text-muted">
                    Reportado el: 10/09/2025
                </div>
            </div>
        </div>
    </div>
</div>

</div> 
</body>
</html>