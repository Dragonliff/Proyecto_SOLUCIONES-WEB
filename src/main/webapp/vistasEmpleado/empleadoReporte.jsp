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

                            <div class="form-group mt-2">
                                <label>Tipo de combustible:</label>
                                <select name="tipoCombustible" class="form-control" required>
                                    <option value="">Seleccione...</option>
                                    <option value="Gasolina">Gasolina</option>
                                    <option value="Diesel">Diésel</option>
                                    <option value="Eléctrico">Eléctrico</option>
                                    <option value="Gas Natural">Gas Natural</option>
                                </select>
                            </div>

                            <div class="form-group mt-2">
                                <label>Litros consumidos:</label>
                                <input type="number" step="0.01" name="litros" class="form-control" required>
                            </div>

                            <div class="form-group mt-2">
                                <label>Precio por litro (S/):</label>
                                <input type="number" step="0.01" name="precioLitro" class="form-control" required>
                            </div>

                            <button type="submit" class="btn btn-primary mt-3">Guardar</button>
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
// -----------------------------------------------------------
// LÓGICA DE ALERTA AL INICIAR USO (POP-UP)
// -----------------------------------------------------------

// Recupera el mensaje de la sesión. Es crucial reemplazar el salto de línea (\n) por \\n 
// para que se muestre correctamente en el alert de JavaScript.
var alertaMensaje = '<%= session.getAttribute("alertaMantenimiento") != null ? 
                     ((String) session.getAttribute("alertaMantenimiento")).replace("\n", "\\n") : "" %>';

if (alertaMensaje.trim() !== "") {
    // Muestra el mensaje de alerta
    alert(alertaMensaje);
    
    // Elimina el atributo de la sesión inmediatamente.
    // Esto asegura que la alerta se muestre solo una vez, siguiendo el patrón Post-Redirect-Get.
    <% 
        session.removeAttribute("alertaMantenimiento");
    %>
}
</script>


