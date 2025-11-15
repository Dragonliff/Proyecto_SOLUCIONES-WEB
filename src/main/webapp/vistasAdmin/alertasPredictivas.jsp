<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    request.setAttribute("titulo", "Alertas Predictivas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alertas Predictivas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Fondo gris profesional */
        body {
            background-color: #e2e2e2 !important;
        }

        /* Cards para alertas */
        .alert-card {
            background-color: #f5f5f5 !important;
            border-left: 6px solid #ffc107;
            border-radius: 8px;
            padding: 18px;
            margin-bottom: 15px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .alert-card-critical {
            border-left-color: #dc3545;
        }

        .alert-title {
            font-size: 1.2rem;
            font-weight: bold;
        }

    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-bell-fill me-2"></i>
        Alertas Predictivas
    </h2>

    <p class="text-center text-muted mb-4">
        Monitoreo de condiciones críticas y predicciones de fallos en la flota.
    </p>

    <!-- Alerta Próxima -->
    <div class="alert-card d-flex justify-content-between align-items-center">
        <span>
            <span class="alert-title">Camión Volvo A1</span> — requiere mantenimiento en 
            <strong>200 km</strong>.
        </span>
        <span class="badge bg-warning text-dark">Próximo</span>
    </div>

    <!-- Alerta Crítica -->
    <div class="alert-card alert-card-critical d-flex justify-content-between align-items-center">
        <span>
            <span class="alert-title">Excavadora X5</span> — próxima a fallo en 
            <strong>20 horas</strong> de uso.
        </span>
        <span class="badge bg-danger">Crítico</span>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
