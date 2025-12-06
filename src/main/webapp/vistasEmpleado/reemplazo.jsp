<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layoutEmpleados.jsp" %>
<%@ include file="../seguridad.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.SolicitudReemplazo" %>
<%@ page import="Modelo.asignaciones_conductor_vehiculo" %>

<div class="container mt-4">

    <h3>Solicitud de Reemplazo</h3>
    <hr>

    <%
        String mensaje = (String) request.getAttribute("mensaje");
        if (mensaje != null) {
    %>
        <div class="alert alert-info"><%= mensaje %></div>
    <%
        }
    %>

    <h5>Nuevo reemplazo</h5>
    <form action="${pageContext.request.contextPath}/ReemplazoServlet" 
      method="POST" 
      enctype="multipart/form-data">
        <input type="hidden" name="accion" value="guardar">

        <input type="hidden" name="idConductor" value="<%= session.getAttribute("idConductor") %>">

        <div class="mb-3">
            <label class="form-label fw-bold">Vehículo asignado</label>
            <select class="form-select" name="idVehiculoActual" required>
                <option value="">Seleccione un vehículo</option>

                <%
                    List<Modelo.vehiculos> vehiculos = 
                        (List<Modelo.vehiculos>) request.getAttribute("vehiculos");

                    if (vehiculos != null) {
                        for (Modelo.vehiculos v : vehiculos) {
                %>
                            <option value="<%= v.getIdVehiculo() %>">
                                <%= v.getPlaca() %> - <%= v.getMarca() %> <%= v.getModelo() %>
                            </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="mb-3">
            <label>Motivo</label>
            <select class="form-control" name="motivo">
                <option>Mantenimiento urgente</option>
                <option>Falla mecánica</option>
                <option>Vehículo inseguro</option>
                <option>Ruido extraño</option>
                <option>Otro</option>
            </select>
        </div>

        <div class="mb-3">
            <label>Detalle adicional</label>
            <textarea class="form-control" name="detalle" rows="3"></textarea>
        </div>
            
        <div class="mb-3">
            <label class="form-label">Adjuntar imagen (opcional)</label>
            <input type="file" name="imagen" accept="image/*" class="form-control">
        </div>

        <button class="btn btn-primary">Enviar solicitud</button>
    </form>

    <hr>

    <h5>Mis solicitudes enviadas</h5>

    <table class="table table-bordered table-striped mt-3">
        <thead>
            <tr>
                <th>ID</th>
                <th>Vehículo</th>
                <th>Motivo</th>
                <th>Detalle</th>
                <th>Estado</th>
                <th>Fecha</th>
            </tr>
        </thead>

        <tbody>
            <%
                List<Modelo.SolicitudReemplazo> lista = 
                    (List<Modelo.SolicitudReemplazo>) request.getAttribute("lista");

                if (lista != null && !lista.isEmpty()) {
                    for (Modelo.SolicitudReemplazo s : lista) {
            %>
            <tr>
                <td><%= s.getIdSolicitud() %></td>
                <td><%= s.getIdVehiculoActual() %></td>
                <td><%= s.getMotivo() %></td>
                <td><%= s.getDetalle() %></td>
                <td><%= s.getEstado() %></td>
                <td><%= s.getFechaSolicitud() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" class="text-center">No tienes solicitudes aún</td>
            </tr>
            <% } %>
        </tbody>

    </table>

</div>
