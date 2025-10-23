<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("titulo", "Mantenimientos");
%>
<%@ include file="layoutEmpleados.jsp" %>

<div class="container-fluid">
    <h2 class="mb-4">Mantenimientos</h2>

    <table class="table table-hover shadow-sm">
        <thead class="table-dark">
            <tr>
                <th>Equipo</th>
                <th>Fecha</th>
                <th>Estado</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Excavadora ZX200</td>
                <td>15/09/2025</td>
                <td><span class="badge bg-success">Completado</span></td>
            </tr>
            <tr>
                <td>Camión Volvo FH</td>
                <td>25/09/2025</td>
                <td><span class="badge bg-warning text-dark">Pendiente</span></td>
            </tr>
            <tr>
                <td>Pickup Hilux</td>
                <td>30/09/2025</td>
                <td><span class="badge bg-danger">Atrasado</span></td>
            </tr>
        </tbody>
    </table>
</div>

</div> 
</body>
</html>