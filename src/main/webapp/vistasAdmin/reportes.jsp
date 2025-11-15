<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    request.setAttribute("titulo", "Reportes");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes Estadísticos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Charts -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        /* Fondo gris profesional */
        body {
            background-color: #e2e2e2 !important;
        }

        /* Estilo unificado de tarjetas */
        .card {
            background-color: #f5f5f5 !important;
            border-radius: 10px;
        }
    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="mb-4 fw-bold text-center">
        <i class="bi bi-bar-chart-fill me-2"></i>Reportes Estadísticos
    </h2>

    <p class="text-center text-muted mb-4">
        Visualiza métricas clave de disponibilidad, costos y horas de uso de la flota.
    </p>

    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="card shadow-sm text-center p-3">
                <h5>Disponibilidad</h5>
                <p class="fs-3 fw-bold text-success">92%</p>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm text-center p-3">
                <h5>Costo Mensual</h5>
                <p class="fs-3 fw-bold text-danger">$12,500</p>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm text-center p-3">
                <h5>Horas de Uso</h5>
                <p class="fs-3 fw-bold text-primary">780 h</p>
            </div>
        </div>
    </div>

    <!-- Gráficos -->
    <div class="row g-3">
        <div class="col-md-6">
            <div class="card shadow-sm p-3">
                <h5 class="text-center mb-3">Disponibilidad Mensual</h5>
                <canvas id="chartDisponibilidad"></canvas>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm p-3">
                <h5 class="text-center mb-3">Costos de Mantenimiento</h5>
                <canvas id="chartCostos"></canvas>
            </div>
        </div>
    </div>

</div>

<script>
    new Chart(document.getElementById('chartDisponibilidad'), {
        type: 'line',
        data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May'],
            datasets: [{
                label: 'Disponibilidad (%)',
                data: [90, 88, 93, 91, 92],
                borderColor: '#198754',
                backgroundColor: 'rgba(25,135,84,0.25)',
                fill: true,
                tension: 0.3
            }]
        }
    });

    new Chart(document.getElementById('chartCostos'), {
        type: 'bar',
        data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May'],
            datasets: [{
                label: 'Costos ($)',
                data: [10000, 12000, 9500, 14000, 12500],
                backgroundColor: 'rgba(220,53,69,0.8)'
            }]
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
