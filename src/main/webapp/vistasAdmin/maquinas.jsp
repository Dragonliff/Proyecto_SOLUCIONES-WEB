
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Activo" %>
<%@ include file="layout.jsp" %>
<%
    request.setAttribute("titulo", "Listado de Máquinas");
        

    List<Activo> activos = (List<Activo>) request.getAttribute("activos");

%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Máquinas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/barraNavegacion.css">
    <link rel="stylesheet" href="css/custom-cards.css">
</head>

<body>
    <div class="container my-4">
        
        <div class="mb-4">
            <h1 class="h3 d-inline">Vehículos</h1>

            <button type="button" class="btn btn-sm btn-primary ms-2" data-bs-toggle="modal" data-bs-target="#modalAgregarActivo">
                + Agregar Vehículo
            </button>
        </div>
        
        <div class="modal fade" id="modalAgregarActivo" tabindex="-1" aria-labelledby="modalAgregarActivoLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <form action="<%= request.getContextPath() %>/ActivoServlet" method="post">
                <div class="modal-header">
                  <h5 class="modal-title" id="modalAgregarActivoLabel">Agregar Nuevo Vehículo</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>

                <div class="modal-body row g-3">
                  <input type="hidden" name="accion" value="agregar">

                  <div class="col-md-6">
                    <label for="placa" class="form-label">Placa</label>
                    <input type="text" class="form-control" id="placa" name="placa" required>
                  </div>

                  <div class="col-md-6">
                    <label for="codigoInterno" class="form-label">Código Interno</label>
                    <input type="text" class="form-control" id="codigoInterno" name="codigoInterno" required>
                  </div>

                  <div class="col-md-6">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                  </div>

                  <div class="col-md-6">
                    <label for="idTipoActivo" class="form-label">Tipo de Activo</label>
                    <select class="form-select" id="idTipoActivo" name="idTipoActivo" required>
                      <option value="1">Vehículo</option>
                      <option value="2">Maquinaria</option>
                      <option value="3">Equipo</option>
                    </select>
                  </div>

                  <div class="col-md-6">
                    <label for="marca" class="form-label">Marca</label>
                    <input type="text" class="form-control" id="marca" name="marca">
                  </div>

                  <div class="col-md-6">
                    <label for="modelo" class="form-label">Modelo</label>
                    <input type="text" class="form-control" id="modelo" name="modelo">
                  </div>

                  <div class="col-md-6">
                    <label for="anio" class="form-label">Año</label>
                    <input type="number" class="form-control" id="anio" name="anio">
                  </div>

                  <div class="col-md-6">
                    <label for="capacidadPasajeros" class="form-label">Capacidad de Pasajeros</label>
                    <input type="number" class="form-control" id="capacidadPasajeros" name="capacidadPasajeros">
                  </div>

                  <div class="col-md-6">
                    <label for="kilometrajeActual" class="form-label">Kilometraje Actual (km)</label>
                    <input type="number" step="0.1" class="form-control" id="kilometrajeActual" name="kilometrajeActual" required>
                  </div>

                  <div class="col-md-6">
                    <label for="estado" class="form-label">Estado</label>
                    <select class="form-select" id="estado" name="estado">
                      <option value="Activo">Activo</option>
                      <option value="Mantenimiento">Mantenimiento</option>
                      <option value="Inactivo">Inactivo</option>
                    </select>
                  </div>
                </div>

                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                  <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
              </form>
            </div>
          </div>
        </div>

       <!-- LISTA DE VEHÍCULOS -->
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
            <%
                if (activos != null && !activos.isEmpty()) {
                    for (Activo a : activos) {
                        String estadoColorClass = "status-badge-secondary";
                        if ("Activo".equalsIgnoreCase(a.getEstado())) estadoColorClass = "status-badge-success";
                        else if ("Mantenimiento".equalsIgnoreCase(a.getEstado())) estadoColorClass = "status-badge-warning";
                        else if ("Inactivo".equalsIgnoreCase(a.getEstado())) estadoColorClass = "status-badge-danger";
                        
                        String ubicacion = "Taller Central";
                        double eficiencia = 95.5;
                        int combustible = 80;
            %>
                <div class="col">
                    <div class="custom-machine-card shadow-sm p-3">
                        
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div class="d-flex align-items-center">
                                <div class="vehicle-icon-wrapper">
                                    <i class="bi bi-truck vehicle-icon-placeholder"></i>
                                </div>
                                <div>
                                    <h5 class="machine-id mb-0"><%= a.getNombre() %></h5>
                                    <small class="text-muted"><%= a.getModelo() %> (Distribución B)</small>
                                </div>
                            </div>
                            <span class="status-badge <%= estadoColorClass %>"><%= a.getEstado() %></span>
                        </div>

                        <div class="mb-3 machine-detail-group">
                            <p class="mb-1"><i class="bi bi-geo-alt-fill me-2"></i><%= ubicacion %></p>
                            <p class="mb-0"><i class="bi bi-person-fill me-2"></i>Conductor: <strong><%= a.getConductor() %></strong></p>
                        </div>
                        
                        <div class="metrics-grid">
                            <div class="metric-item">
                                <i class="bi bi-graph-up metric-icon-green"></i>
                                <h4 class="metric-value text-success mb-0"><%= String.format("%.1f", eficiencia) %>%</h4>
                                <small class="metric-label text-muted">Eficiencia</small>
                            </div>

                            <div class="metric-item">
                                <i class="bi bi-fuel-pump-fill metric-icon-blue"></i>
                                <h4 class="metric-value text-info mb-0"><%= combustible %></h4>
                                <small class="metric-label text-muted">Combustible</small>
                            </div>

                            <div class="metric-item">
                                <i class="bi bi-clock metric-icon-secondary"></i>
                                <h4 class="metric-value text-secondary mb-0"><%= String.format("%,d", a.getKilometraje()) %> km</h4>
                                <small class="metric-label text-muted">km</small>
                            </div>
                        </div>

                        <div class="mt-3 text-center">
                            <a href="#" class="btn btn-sm btn-outline-primary w-100">Ver Detalles</a>
                        </div>
                        
                    </div>
                </div>
                <%
                        } 
                    } else {
                %>
                <div class="col-12">
                    <p class="text-muted">No hay máquinas registradas aún.</p>
                </div>
                <% } %>
        </div>
    </div>
        
        
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>