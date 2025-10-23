<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%
    request.setAttribute("titulo", "Alertas Predictivas");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alertas Predictivas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Alertas Predictivas</h1>
            <p class="text-muted">Monitoreo de condiciones críticas y predicciones de fallos en la flota.</p>
        </header>

        <div class="alert alert-warning d-flex justify-content-between align-items-center shadow-sm">
            <span>
                <strong>Camión Volvo A1</strong> — requiere mantenimiento en <strong>200 km</strong>.
            </span>
            <span class="badge bg-warning text-dark">Próximo</span>
        </div>

        <div class="alert alert-danger d-flex justify-content-between align-items-center shadow-sm">
            <span>
                <strong>Excavadora X5</strong> — próxima a fallo en <strong>20 horas</strong> de uso.
            </span>
            <span class="badge bg-danger">Crítico</span>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>