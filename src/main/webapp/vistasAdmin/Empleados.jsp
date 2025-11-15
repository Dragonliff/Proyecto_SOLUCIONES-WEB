<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.usuarios" %>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<%
    List<usuarios> lista = (List<usuarios>) request.getAttribute("lista");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Empleados</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/barraNavegacion.css">

    <!-- ---- ESTILO PERSONALIZADO DE FONDO Y TARJETAS ---- -->
    <style>
        body {
            background: #e6e7ea; /* Fondo suave */
        }

        .section-wrapper {
            background: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }

        h2 {
            font-weight: 700;
            color: #155aac;
        }

        table {
            background: white !important;
            border-radius: 10px;
            overflow: hidden;
        }

        .table thead {
            background: #155aac;
            color: white;
        }

        .btn-primary {
            background-color: #155aac;
            border-color: #155aac;
        }

        .btn-primary:hover {
            background-color: #0d4a90;
        }

        /* MODAL */
        .modal-header {
            background: #155aac;
        }
    </style>
</head>

<body>

    <div class="container py-5">
        <div class="section-wrapper">

            <h2 class="text-center mb-4">Gestión de Empleados (Mecánicos y Conductores)</h2>

            <!-- Botón agregar -->
            <div class="text-end mb-3">
                <button class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#empleadoModal" onclick="abrirModalAgregar()">
                    ➕ Nuevo Empleado
                </button>
            </div>

            <!-- Tabla -->
            <table class="table table-bordered table-hover align-middle">
                <thead class="text-center">
                    <tr>
                        <th>ID</th>
                        <th>Nombre Completo</th>
                        <th>Usuario</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Rol</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% if (lista != null && !lista.isEmpty()) { 
                       for (usuarios u : lista) { %>
                    <tr>
                        <td><%= u.getIdUsuario() %></td>
                        <td><%= u.getNombreCompleto() %></td>
                        <td><%= u.getUsuario() %></td>
                        <td><%= u.getCorreo() %></td>
                        <td><%= u.getTelefono() %></td>
                        <td><%= (u.getIdRol() == 2 ? "Mecánico" : "Conductor") %></td>
                        <td>
                            <span class="badge bg-<%= u.getEstado().equalsIgnoreCase("Activo") ? "success" : "secondary" %>">
                                <%= u.getEstado() %>
                            </span>
                        </td>
                        <td class="text-center">
                            <button class="btn btn-warning btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#empleadoModal"
                                    onclick="editarEmpleado('<%= u.getIdUsuario() %>',
                                                           '<%= u.getNombreCompleto() %>',
                                                           '<%= u.getUsuario() %>',
                                                           '<%= u.getCorreo() %>',
                                                           '<%= u.getTelefono() %>',
                                                           '<%= u.getIdRol() %>',
                                                           '<%= u.getEstado() %>')">
                                ✏️ Editar
                            </button>
                            <a href="EmpleadoServlet?action=eliminar&id=<%= u.getIdUsuario() %>"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Eliminar este empleado?');">
                                🗑️ Eliminar
                            </a>
                        </td>
                    </tr>
                <% }} else { %>
                    <tr><td colspan="8" class="text-center text-muted">No hay empleados registrados</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ================= MODAL ================= -->
    <div class="modal fade" id="empleadoModal" data-bs-backdrop="static"
         data-bs-keyboard="false" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <form id="formEmpleado" action="${pageContext.request.contextPath}/EmpleadoServlet"
              method="post" class="modal-content">

          <div class="modal-header text-white">
            <h5 class="modal-title" id="empleadoModalLabel">Agregar Empleado</h5>
            <button type="button" class="btn-close btn-close-white"
                    data-bs-dismiss="modal"></button>
          </div>

          <div class="modal-body">
            <input type="hidden" name="idUsuario" id="idUsuario">

            <div class="mb-3">
                <label class="form-label">Nombre Completo:</label>
                <input type="text" name="nombreCompleto" id="nombreCompleto"
                       class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Usuario:</label>
                <input type="text" name="usuario" id="usuario"
                       class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Correo:</label>
                <input type="email" name="correo" id="correo"
                       class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Teléfono:</label>
                <input type="text" name="telefono" id="telefono"
                       class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Rol:</label>
                <select name="idRol" id="idRol" class="form-select" required>
                    <option value="2">Mecánico</option>
                    <option value="3">Conductor</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Estado:</label>
                <select name="estado" id="estado" class="form-select">
                    <option value="Activo">Activo</option>
                    <option value="Inactivo">Inactivo</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña:</label>
                <input type="password" name="contrasena" id="contrasena"
                       class="form-control" required>
            </div>
          </div>

          <div class="modal-footer">
            <button type="submit" name="action" value="guardar"
                    class="btn btn-success">💾 Guardar</button>
            <button type="button" class="btn btn-secondary"
                    data-bs-dismiss="modal">Cancelar</button>
          </div>

        </form>
      </div>
    </div>

    <script>
    function abrirModalAgregar() {
        document.getElementById("empleadoModalLabel").innerText = "Agregar Empleado";
        document.querySelector("form").reset();
        document.getElementById("idUsuario").value = "";
    }

    function editarEmpleado(id, nombre, usuario, correo, telefono, rol, estado) {
        document.getElementById("empleadoModalLabel").innerText = "Editar Empleado";
        document.getElementById("idUsuario").value = id;
        document.getElementById("nombreCompleto").value = nombre;
        document.getElementById("usuario").value = usuario;
        document.getElementById("correo").value = correo;
        document.getElementById("telefono").value = telefono;
        document.getElementById("idRol").value = rol;
        document.getElementById("estado").value = estado;
        document.getElementById("contrasena").value = "";
    }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
