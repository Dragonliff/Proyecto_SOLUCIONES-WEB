<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Modelo.herramientas, Modelo.proveedores" %>
<%@ page import="ModeloDAO.HerramientaDAO, ModeloDAO.ProveedorDAO" %>
<%
    List<herramientas> lista = (List<herramientas>) request.getAttribute("lista");
    List<proveedores> listaProveedores = (List<proveedores>) request.getAttribute("proveedores");
    if (listaProveedores == null) {
        listaProveedores = new ArrayList<>(); 
    }
%>
<%@ include file="layout.jsp" %>
<%@ include file="../seguridad.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Herramientas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body style="background: #e5e6e7;">

<div class="container py-4">
    <h2 class="text-center mb-4 fw-bold">
        <i class="bi bi-tools me-2"></i>Gestión de Herramientas
    </h2>

    <div class="d-flex justify-content-end mb-3">
        <button class="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#modalAgregar">
            <i class="bi bi-plus-circle"></i> Nueva Herramienta
        </button>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <%
                if (lista == null || lista.isEmpty()) {
            %>
                <div class="alert alert-info text-center">No hay herramientas registradas actualmente.</div>
            <%
                } else {
            %>
            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tipo</th>
                        <th>Estado</th>
                        <th>Proveedor</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (herramientas h : lista) {
                %>
                    <tr class="text-center">
                        <td><%= h.getIdHerramienta() %></td>
                        <td><%= h.getNombre() %></td>
                        <td><%= h.getTipo() %></td>
                        <td>
                            <span class="badge 
                                <%= h.getEstado().equals("Disponible") ? "bg-success" : 
                                    (h.getEstado().equals("En Uso") ? "bg-warning text-dark" : "bg-danger") %>">
                                <%= h.getEstado() %>
                            </span>
                        </td>
                        <td>
                            <%
                                String nombreProveedor = "";
                                for (proveedores p : listaProveedores) {
                                    if (p.getId_proveedor() == h.getIdProveedor()) {
                                        nombreProveedor = p.getNombre();
                                        break;
                                    }
                                }
                            %>
                            <%= nombreProveedor %>
                        </td>
                        <td>
                            <button class="btn btn-warning btn-sm me-1"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modalEditar<%= h.getIdHerramienta() %>">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <a href="HerramientaServlet?accion=eliminar&id=<%= h.getIdHerramienta() %>"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Seguro que deseas eliminar esta herramienta?')">
                                <i class="bi bi-trash3"></i>
                            </a>
                        </td>
                    </tr>

                    <div class="modal fade" id="modalEditar<%= h.getIdHerramienta() %>" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="HerramientaServlet" method="post">
                                    <div class="modal-header bg-warning text-dark">
                                        <h5 class="modal-title"><i class="bi bi-pencil-square"></i> Editar Herramienta</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" name="accion" value="guardar">
                                        <input type="hidden" name="idHerramienta" value="<%= h.getIdHerramienta() %>">

                                        <div class="mb-3">
                                            <label class="form-label">Nombre:</label>
                                            <input type="text" name="nombre" value="<%= h.getNombre() %>" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Tipo:</label>
                                            <input type="text" name="tipo" value="<%= h.getTipo() %>" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Estado:</label>
                                            <select name="estado" class="form-select" required>
                                                <option value="Disponible" <%= h.getEstado().equals("Disponible") ? "selected" : "" %>>Disponible</option>
                                                <option value="En Uso" <%= h.getEstado().equals("En Uso") ? "selected" : "" %>>En Uso</option>
                                                <option value="Mantenimiento" <%= h.getEstado().equals("Mantenimiento") ? "selected" : "" %>>Mantenimiento</option>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Proveedor:</label>
                                            <select name="idProveedor" class="form-select" required>
                                                <option value="">Seleccione proveedor</option>
                                                <%
                                                    for (proveedores p : listaProveedores) {
                                                        String selected = (p.getId_proveedor() == h.getIdProveedor()) ? "selected" : "";
                                                %>
                                                    <option value="<%= p.getId_proveedor() %>" <%= selected %>><%= p.getNombre() %></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-success">
                                            <i class="bi bi-save2"></i> Guardar Cambios
                                        </button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                <%
                    }
                %>
                </tbody>
            </table>
            <%
                }
            %>
        </div>
    </div>
</div>

<div class="modal fade" id="modalAgregar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="HerramientaServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title"><i class="bi bi-plus-circle"></i> Agregar Nueva Herramienta</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="accion" value="guardar">

                    <div class="mb-3">
                        <label class="form-label">Nombre:</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Tipo:</label>
                        <input type="text" name="tipo" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Estado:</label>
                        <select name="estado" class="form-select" required>
                            <option value="Disponible">Disponible</option>
                            <option value="En Uso">En Uso</option>
                            <option value="Mantenimiento">Mantenimiento</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Proveedor:</label>
                        <select name="idProveedor" class="form-select" required>
                            <option value="">Seleccione proveedor</option>
                            <%
                                for (proveedores p : listaProveedores) {
                            %>
                                <option value="<%= p.getId_proveedor() %>"><%= p.getNombre() %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-check-circle"></i> Guardar
                    </button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>