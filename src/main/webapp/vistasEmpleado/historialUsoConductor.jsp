<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>
<%@page import="java.util.List"%>
<%@page import="Modelo.usos_vehiculos"%>

<%
    List<usos_vehiculos> historial = (List<usos_vehiculos>) request.getAttribute("historial");
%>

<div class="container mt-4">

    <h2>📘 Historial de uso de vehículos</h2>
    <hr>

    <% if (historial == null || historial.isEmpty()) { %>

        <div class="alert alert-info mt-3">
            No hay registros en tu historial de uso.
        </div>

    <% } else { %>

        <table class="table table-bordered table-striped mt-3">
            <thead class="table-dark">
                <tr>
                    <th>ID Uso</th>
                    <th>ID Vehículo</th>
                    <th>Fecha</th>
                    <th>Horas</th>
                    <th>KM Recorridos</th>
                    <th>Combustible</th>
                    <th>Litros</th>
                    <th>Precio/Litro</th>
                    <th>Costo Total</th>
                    <th>Descripción</th>
                </tr>
            </thead>
            <tbody>

                <% for (usos_vehiculos u : historial) { %>
                <tr>
                    <td><%= u.getIdUso() %></td>
                    <td><%= u.getIdVehiculo() %></td>
                    <td><%= u.getFecha() %></td>
                    <td><%= u.getHorasUso() %></td>
                    <td><%= u.getKmRecorridos() %></td>
                    <td><%= u.getTipoCombustible() %></td>
                    <td><%= u.getLitros() %></td>
                    <td>S/ <%= u.getPrecioLitro() %></td>
                    <td><strong>S/ <%= u.getCostoTotal() %></strong></td>
                    <td><%= u.getDescripcion() %></td>
                </tr>
                <% } %>

            </tbody>
        </table>

    <% } %>

    <a href="<%= request.getContextPath() %>/UsoVehiculoServlet?accion=reporte" 
       class="btn btn-secondary mt-3">Volver</a>

</div>