<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.proveedorvehiculo" %>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    List<proveedorvehiculo> lista = (List<proveedorvehiculo>) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Proveedores de Vehículos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body { background: #e6e7ea; }
        .section-wrapper {
            background: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        h2 { font-weight: 700; color: #155aac; }
        .table thead {
            background: #155aac;
            color: white;
        }
    </style>
</head>

<body>

<div class="container py-5">
    <div class="section-wrapper">

        <h2 class="text-center mb-4">Gestión de Proveedores de Vehículos</h2>

        <!-- Botón agregar -->
        <div class="text-end mb-3">
            <button class="btn btn-primary" data-bs-toggle="modal"
                    data-bs-target="#proveedorVehiculoModal" onclick="nuevoProveedorVehiculo()">
                ➕ Nuevo Proveedor Vehículo
            </button>
        </div>

        <table class="table table-bordered table-hover align-middle">
            <thead class="text-center">
                <tr>
                    <th>ID</th>
                    <th>Nombre Empresa</th>
                    <th>Contacto</th>
                    <th>Teléfono</th>
                    <th>Email</th>
                    <th>Dirección</th>
                    <th>Cotización</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>

            <tbody>
            <% if (lista != null && !lista.isEmpty()) {
                   for (proveedorvehiculo p : lista) { %>

                <tr>
                    <td><%= p.getId_proveedorVehiculo() %></td>
                    <td><%= p.getNombre() %></td>
                    <td><%= p.getContacto() %></td>
                    <td><%= p.getTelefono() %></td>
                    <td><%= p.getEmail() %></td>
                    <td><%= p.getDireccion() %></td>
                    <td>S/. <%= p.getCotizacion() %></td>

                    <td>
                        <span class="badge bg-<%= p.getEstado().equals("Activo") ? "success" : "secondary" %>">
                            <%= p.getEstado() %>
                        </span>
                    </td>

                    <td class="text-center">

                        <button class="btn btn-warning btn-sm"
                            data-bs-toggle="modal"
                            data-bs-target="#proveedorVehiculoModal"
                            onclick="editarProveedorVehiculo(
                                '<%= p.getId_proveedorVehiculo() %>',
                                '<%= p.getNombre() %>',
                                '<%= p.getContacto() %>',
                                '<%= p.getTelefono() %>',
                                '<%= p.getEmail() %>',
                                '<%= p.getDireccion() %>',
                                '<%= p.getCotizacion() %>',
                                '<%= p.getEstado() %>'
                            )">
                            ✏ Editar
                        </button>

                        <a href="ProveedorVehiculoServlet?accion=eliminar&id=<%= p.getId_proveedorVehiculo() %>"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('¿Eliminar proveedor de vehículo?');">
                            🗑
                        </a>

                    </td>
                </tr>

            <% }} else { %>
                <tr><td colspan="9" class="text-center text-muted">No hay proveedores registrados</td></tr>
            <% } %>
            </tbody>
        </table>

    </div>
</div>


<!-- ================= MODAL ================= -->
<div class="modal fade" id="proveedorVehiculoModal" data-bs-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="ProveedorVehiculoServlet" method="post" class="modal-content">

            <div class="modal-header text-white" style="background: #155aac;">
                <h5 class="modal-title" id="modalTituloVehiculo">Agregar Proveedor Vehículo</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">

                <input type="hidden" name="id_proveedorVehiculo" id="id_proveedorVehiculo">

                <div class="mb-3">
                    <label class="form-label">Nombre Empresa:</label>
                    <input type="text" name="nombre" id="nombreVeh" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Contacto:</label>
                    <input type="text" name="contacto" id="contactoVeh" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Teléfono:</label>
                    <input type="text" name="telefono" id="telefonoVeh" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" id="emailVeh" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Dirección:</label>
                    <input type="text" name="direccion" id="direccionVeh" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Cotización (S/.):</label>
                    <input type="number" step="0.01" name="cotizacion" id="cotizacionVeh" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Estado:</label>
                    <select name="estado" id="estadoVeh" class="form-select">
                        <option value="Activo">Activo</option>
                        <option value="Inactivo">Inactivo</option>
                    </select>
                </div>

            </div>

            <div class="modal-footer">
                <button type="submit" name="accion" value="guardar" class="btn btn-success">Guardar</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            </div>

        </form>
    </div>
</div>


<script>
function nuevoProveedorVehiculo() {
    document.getElementById("modalTituloVehiculo").innerText = "Agregar Proveedor Vehículo";
    document.getElementById("id_proveedorVehiculo").value = "";
}

function editarProveedorVehiculo(id, nombre, contacto, telefono, email, direccion, cotizacion, estado) {
    document.getElementById("modalTituloVehiculo").innerText = "Editar Proveedor Vehículo";
    document.getElementById("id_proveedorVehiculo").value = id;
    document.getElementById("nombreVeh").value = nombre;
    document.getElementById("contactoVeh").value = contacto;
    document.getElementById("telefonoVeh").value = telefono;
    document.getElementById("emailVeh").value = email;
    document.getElementById("direccionVeh").value = direccion;
    document.getElementById("cotizacionVeh").value = cotizacion;
    document.getElementById("estadoVeh").value = estado;
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>