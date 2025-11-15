<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    request.setAttribute("titulo", "Mantenimiento");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Mantenimientos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Fondo gris más notorio */
        body {
            background-color: #e2e2e2 !important;
        }

        /* Card con gris más fuerte para resaltar */
        .card {
            background-color: #f5f5f5 !important;
            border: none;
        }

        .list-group-item {
            background-color: #f8f8f8 !important;
        }
    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-wrench-adjustable-circle me-2"></i>
        Historial de Mantenimientos
    </h2>

    <p class="text-center text-muted mb-4">
        Consulta los mantenimientos realizados a la maquinaria y vehículos.
    </p>

    <div class="card shadow-sm border-0">
        <div class="card-body px-0">

            <ul class="list-group list-group-flush">

                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <span>
                        <strong>Camión Volvo A1</strong> — Cambio de aceite
                    </span>
                    <span class="text-muted">01/09/2025</span>
                </li>

                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <span>
                        <strong>Excavadora X5</strong> — Revisión hidráulica
                    </span>
                    <span class="text-muted">25/08/2025</span>
                </li>

            </ul>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
