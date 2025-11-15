<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    request.setAttribute("titulo", "Fallas Reportadas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Fallas Reportadas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #e2e2e2 !important;
        }

        .card {
            background-color: #f5f5f5 !important;
            border: none;
        }

        .table tbody tr {
            background-color: #fafafa !important;
        }
    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        Fallas Reportadas
    </h2>

    <p class="text-center text-muted mb-4">
        Registro de fallas detectadas en la flota y su estado de resolución.
    </p>

    <div class="card shadow-sm">
        <div class="card-body">

            <div class="table-responsive">
                <table class="table table-hover table-bordered align-middle">

                    <thead class="table-dark text-center">
                        <tr>
                            <th scope="col">Máquina</th>
                            <th scope="col">Descripción</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Estado</th>
                        </tr>
                    </thead>

                    <tbody class="text-center">

                        <tr>
                            <td>Camión Volvo A1</td>
                            <td>Freno desgastado</td>
                            <td>05/09/2025</td>
                            <td><span class="badge bg-warning text-dark">Pendiente</span></td>
                        </tr>

                        <tr>
                            <td>Generador B2</td>
                            <td>Sobrecalentamiento</td>
                            <td>02/09/2025</td>
                            <td><span class="badge bg-success">Resuelto</span></td>
                        </tr>

                    </tbody>

                </table>
            </div>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
