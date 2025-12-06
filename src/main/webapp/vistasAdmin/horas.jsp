<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Maquina" %>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    request.setAttribute("titulo", "Registro de Uso de Máquinas");

    List<Maquina> maquinas = (List<Maquina>) request.getAttribute("maquinas");

    if (maquinas == null) {
        maquinas = java.util.Collections.emptyList();
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Horas</title>
    <link rel="stylesheet" href="css/barraNavegacion.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f0f0f0 !important; 
        }
        .card-table {
            background: white;
            border-radius: 12px;
            padding: 20px;
        }
    </style>
</head>

<body>
    <div class="container my-4">
        <header class="mb-4">
            <h1 class="h3">Registro de Uso</h1>
            <p class="text-muted">Consulta y controla las horas de funcionamiento y kilometraje de cada máquina.</p>
        </header>

        <div class="card-table shadow-sm">
            <div class="table-responsive">
                <table class="table table-striped table-bordered align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Máquina</th>
                            <th scope="col">Horas de Uso</th>
                            <th scope="col">Kilómetros</th>
                            <th scope="col">Última Actualización</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        if (!maquinas.isEmpty()) {
                            for (Maquina m : maquinas) {
                    %>
                        <tr>
                            <td><%= m.getNombre() %></td>
                            <td><%= m.getHorasUso() %> h</td>
                            <td><%= m.getKilometraje() %> km</td>
                            <td><%= m.getUltimaActualizacion() != null ? m.getUltimaActualizacion() : "-" %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="4" class="text-center text-muted">
                                No hay máquinas registradas aún.
                            </td>
                        </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
