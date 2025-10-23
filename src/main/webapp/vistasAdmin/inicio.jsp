<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>
<%
    request.setAttribute("titulo", "Inicio");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema Web de Resiliencia Operativa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/barraNavegacion.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
GO
<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Panel de Control</h1>
            <p class="text-muted">Bienvenido al sistema de resiliencia operativa. Aquí podrás visualizar el estado general de tu flota.</p>
        </header>

        <div class="row g-3">
            <div class="col-12 col-md-4">
                <div class="card text-bg-primary shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">Flota Registrada</h5>
                        <p class="card-text fs-3 fw-bold">25 Máquinas</p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-4">
                <div class="card text-bg-success shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">Disponibilidad</h5>
                        <p class="card-text fs-3 fw-bold">92%</p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-4">
                <div class="card text-bg-warning shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">Alertas Activas</h5>
                        <p class="card-text fs-3 fw-bold">4</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>