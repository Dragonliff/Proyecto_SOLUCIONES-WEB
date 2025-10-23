<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("titulo", "Fallas Reportadas");
%>
<%@ include file="layoutEmpleados.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloFallas.css">

<div class="container-fluid">
    <h2 class="mb-4">Fallas Reportadas</h2>

    <div class="list-group mb-4">
        <a href="#" class="list-group-item list-group-item-action">
            <strong>Excavadora ZX200</strong> – Motor con ruido extraño
            <span class="badge bg-warning ms-2">Pendiente</span>
        </a>
        <a href="#" class="list-group-item list-group-item-action">
            <strong>Pickup Hilux</strong> – Revisión de frenos
            <span class="badge bg-success ms-2">Resuelta</span>
        </a>
    </div>

    <h5 class="mb-3">Reportar nueva falla</h5>
    <form class="mb-5">
        <div class="mb-3">
            <label class="form-label">Selecciona el Equipo</label>
            <div class="d-flex flex-wrap gap-2 equipo-cards-radio-container">
                <input type="radio" id="equipo-volvo" name="equipo-seleccionado" value="1" class="d-none">
                <label for="equipo-volvo" class="equipo-card">
                    <span class="fw-bold">Camión Volvo FH</span>
                </label>

                <input type="radio" id="equipo-excavadora" name="equipo-seleccionado" value="2" class="d-none">
                <label for="equipo-excavadora" class="equipo-card">
                    <span class="fw-bold">Excavadora ZX200</span>
                </label>

                <input type="radio" id="equipo-hilux" name="equipo-seleccionado" value="3" class="d-none">
                <label for="equipo-hilux" class="equipo-card">
                    <span class="fw-bold">Pickup Toyota Hilux</span>
                </label>
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label">Descripción</label>
            <textarea class="form-control" rows="3" placeholder="Describe la falla..."></textarea>
        </div>
        <button type="submit" class="btn btn-danger">Reportar</button>
    </form>
</div>

</div> 
</body>
</html>