<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%
    request.setAttribute("titulo", "Mantenimiento");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Mantenimientos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Historial de Mantenimientos</h1>
            <p class="text-muted">Consulta los mantenimientos realizados en tu flota.</p>
        </header>

        <div class="card shadow-sm">
            <div class="card-body">
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