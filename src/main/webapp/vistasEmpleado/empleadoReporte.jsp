<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>
<%@page import="ModeloDAO.UsoVehiculoDAO"%>
<%@page import="Modelo.usos_vehiculos"%>
<%@page import="Modelo.vehiculos"%>
<%@page import="java.util.List"%>

<%
    int idConductor = Integer.parseInt(session.getAttribute("idConductor").toString());
    List<vehiculos> vehiculosAsignados = (List<vehiculos>) request.getAttribute("vehiculosAsignados");
%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloFallas.css">

<div class="container mt-4">
    <h2>🚗 Reporte de Uso de Vehículos</h2>
    <table class="table table-bordered mt-3">
        <thead class="table-dark">
            <tr>
                <th>Placa</th>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
        <%
        for (vehiculos v : vehiculosAsignados) {
            java.util.Map<Integer, usos_vehiculos> usosActivos = 
                (java.util.Map<Integer, usos_vehiculos>) request.getAttribute("usosActivos");
            usos_vehiculos usoActivo = (usosActivos != null) ? usosActivos.get(v.getIdVehiculo()) : null;
        %>
            <tr>
                <td><%= v.getPlaca() %></td>
                <td><%= v.getMarca() %></td>
                <td><%= v.getModelo() %></td>
                <td>
                    <% if (usoActivo == null) { %>

                        <form action="<%= request.getContextPath() %>/UsoVehiculoServlet" method="post">
                            <input type="hidden" name="accion" value="iniciar">
                            <input type="hidden" name="idVehiculo" value="<%= v.getIdVehiculo() %>">
                            <input type="hidden" name="idConductor" value="<%= idConductor %>">
                            <button type="submit" class="btn btn-success">Empezar</button>
                        </form>
                    <% } else { %>

                        <button class="btn btn-danger" type="button" 
                                onclick="mostrarFormulario(<%= usoActivo.getIdUso() %>)">Detener</button>

                        <form id="form_<%= usoActivo.getIdUso() %>" 
                              action="<%= request.getContextPath() %>/UsoVehiculoServlet" 
                              method="post" 
                              style="display:none; margin-top:10px;">
                            <input type="hidden" name="accion" value="finalizar">
                            <input type="hidden" name="idUso" value="<%= usoActivo.getIdUso() %>">
                            
                            <div class="form-group">
                                <label>Kilómetros recorridos:</label>
                                <input type="number" step="0.1" name="kmRecorridos" class="form-control" required>
                            </div>
                            <div class="form-group mt-2">
                                <label>Observaciones:</label>
                                <input type="text" name="observaciones" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary mt-2">Guardar</button>
                        </form>
                    <% } %>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script>
function mostrarFormulario(idUso) {
    document.getElementById('form_' + idUso).style.display = 'block';
}
</script>

</body>
</html>

