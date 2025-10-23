<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%
    request.setAttribute("titulo", "Reportes");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes Estadísticos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Reportes Estadísticos</h1>
            <p class="text-muted">Visualiza métricas clave de disponibilidad, costos y horas de uso de la flota.</p>
        </header>

        <div class="row g-3 mb-4">
            <div class="col-md-4">
                <div class="card shadow-sm text-center">
                    <div class="card-body">
                        <h5 class="card-title">Disponibilidad</h5>
                        <p class="fs-3 fw-bold text-success">92%</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm text-center">
                    <div class="card-body">
                        <h5 class="card-title">Costo Mensual</h5>
                        <p class="fs-3 fw-bold text-danger">$12,500</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm text-center">
                    <div class="card-body">
                        <h5 class="card-title">Horas de Uso</h5>
                        <p class="fs-3 fw-bold text-primary">780 h</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-3">
            <div class="col-md-6">
                <div class="card shadow-sm p-3">
                    <h5 class="card-title text-center">Disponibilidad Mensual</h5>
                    <canvas id="chartDisponibilidad"></canvas>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card shadow-sm p-3">
                    <h5 class="card-title text-center">Costos de Mantenimiento</h5>
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
                    borderColor: 'green',
                    backgroundColor: 'rgba(0, 128, 0, 0.2)',
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
                    backgroundColor: 'rgba(220, 53, 69, 0.7)'
                }]
            }
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>