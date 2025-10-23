
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="layout.jsp" %>

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
              <form >
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

    </div>  
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>