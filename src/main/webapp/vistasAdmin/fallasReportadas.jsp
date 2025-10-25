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
</head>

<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Fallas Reportadas</h1>
            <p class="text-muted">Registro de fallas detectadas en la flota y su estado de resolución.</p>
        </header>

        <div class="table-responsive">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Máquina</th>
                        <th scope="col">Descripción</th>
                        <th scope="col">Fecha</th>
                        <th scope="col">Estado</th>
                    </tr>
                </thead>
                <tbody>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>