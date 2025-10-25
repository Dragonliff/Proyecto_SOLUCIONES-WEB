<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.vehiculos" %> 
<%-- Asume que 'layout.jsp' abre <html>, <body> y <main> --%>
<%@ include file="layout.jsp" %> 
<jsp:include page="../seguridad.jsp" />

<%-- ************************************************************************* --%>
<%-- INICIO DEL CONTENIDO DE LA PÁGINA --%>
<%-- ************************************************************************* --%>

    <div class="container my-4">
        
        <div class="mb-4">
            <h1 class="h3 d-inline">Vehículos</h1>

            <button type="button" class="btn btn-sm btn-primary ms-2" 
                    data-bs-toggle="modal" data-bs-target="#modalActivo"
                    onclick="prepararFormulario(null)"> 
                + Agregar Vehículo 
            </button>
        </div>
        
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Placa</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Año</th>
                        <th>Tipo</th>
                        <th>Kilometraje (km)</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%  
                        // 🔴 CAMBIO CLAVE: Obtención de la lista
                        // Se obtiene el atributo "vehiculos" que envía el Servlet.
                        // Si no lo encuentra, usa una lista vacía para evitar NullPointerException.
                        List<vehiculos> lista = (List<vehiculos>) request.getAttribute("vehiculos");
                        if (lista == null) {
                            lista = java.util.Collections.emptyList();
                        }
                        
                        if (!lista.isEmpty()) {
                            for (vehiculos v : lista) { 
                                String estadoClass = "estado-default";
                                if ("Operativo".equalsIgnoreCase(v.getEstado())) {
                                    estadoClass = "estado-operativo";
                                } else if ("En Mantenimiento".equalsIgnoreCase(v.getEstado())) {
                                    estadoClass = "estado-mantenimiento";
                                } else if ("Fuera de Servicio".equalsIgnoreCase(v.getEstado())) {
                                    estadoClass = "estado-inactivo";
                                }
                    %>
                    <tr>
                        <td><%= v.getIdVehiculo() %></td>
                        <td><%= v.getPlaca() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getModelo() %></td>
                        <td><%= v.getAnio() %></td>
                        <td><%= v.getTipoVehiculo() %></td>
                        <td><%= String.format("%.2f", v.getKilometrajeActual()) %></td>
                        <td><span class="<%= estadoClass %>"><%= v.getEstado() %></span></td>
                        <td>
                            <button type="button" class="btn btn-sm btn-info me-1" 
                                    data-bs-toggle="modal" data-bs-target="#modalActivo"
                                    onclick="prepararFormulario(
                                        '<%= v.getIdVehiculo() %>', 
                                        '<%= v.getPlaca() %>', 
                                        '<%= v.getMarca() %>', 
                                        '<%= v.getModelo() %>', 
                                        '<%= v.getAnio() %>', 
                                        '<%= v.getTipoVehiculo() %>', 
                                        '<%= v.getKilometrajeActual() %>', 
                                        '<%= v.getEstado() %>'
                                    )">
                                Editar
                            </button>
                            
                            <% 
                            // BOTONES ELIMINAR / ACTIVAR
                            if ("Operativo".equalsIgnoreCase(v.getEstado())) { %>
                                <a href="VehiculoServlet?accion=eliminar&id=<%= v.getIdVehiculo() %>" 
                                   class="btn btn-sm btn-danger" 
                                   onclick="return confirm('¿Confirma la ELIMINACIÓN PERMANENTE de la placa <%= v.getPlaca() %>?');">
                                    Eliminar
                                </a>
                            <% } else { %>
                                <a href="VehiculoServlet?accion=activar&id=<%= v.getIdVehiculo() %>" 
                                   class="btn btn-sm btn-success" 
                                   onclick="return confirm('¿Confirma la activación a Operativo de la placa <%= v.getPlaca() %>?');">
                                    Activar
                                </a>
                            <% } %>
                        </td>
                    </tr>
                    <%      }
                        } else { %>
                        <tr><td colspan="9" class="text-center">No hay vehículos registrados.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    
    <div class="modal fade" id="modalActivo" tabindex="-1" aria-labelledby="modalActivoLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <form id="formVehiculo" action="${pageContext.request.contextPath}/VehiculoServlet" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTitle">Agregar Nuevo Vehículo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                    </div>

                    <div class="modal-body row g-3">
                        <input type="hidden" name="accion" value="guardar">
                        <input type="hidden" name="idVehiculo" id="idVehiculo" value=""> 

                        <div class="col-md-6">
                            <label for="placa" class="form-label">Placa</label>
                            <input type="text" class="form-control" id="placaModal" name="placa" required>
                        </div>

                        <div class="col-md-6">
                            <label for="marca" class="form-label">Marca</label>
                            <input type="text" class="form-control" id="marcaModal" name="marca" required>
                        </div>
                        
                        <div class="col-md-6">
                            <label for="modelo" class="form-label">Modelo</label>
                            <input type="text" class="form-control" id="modeloModal" name="modelo" required>
                        </div>

                        <div class="col-md-6">
                            <label for="anio" class="form-label">Año</label>
                            <input type="number" class="form-control" id="anioModal" name="anio" required>
                        </div>

                        <div class="col-md-6">
                            <label for="tipoVehiculo" class="form-label">Tipo de Vehículo</label>
                            <select class="form-select" id="tipoVehiculoModal" name="tipoVehiculo" required>
                                <option value="Camioneta">Camioneta</option>
                                <option value="Camión">Camión</option>
                                <option value="Auto">Auto</option>
                                <option value="Motocicleta">Motocicleta</option>
                                <option value="Otro">Otro</option>
                            </select>
                        </div>

                        <div class="col-md-6">
                            <label for="kilometrajeActual" class="form-label">Kilometraje Actual (km)</label>
                            <input type="number" step="0.1" class="form-control" id="kilometrajeActualModal" name="kilometrajeActual" required>
                        </div>

                        <div class="col-md-6">
                            <label for="estado" class="form-label">Estado</label>
                            <select class="form-select" id="estadoModal" name="estado">
                                <option value="Operativo">Operativo</option>
                                <option value="En Mantenimiento">En Mantenimiento</option>
                                <option value="Fuera de Servicio">Fuera de Servicio</option>
                            </select>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary" id="btnGuardar">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        /**
         * Prepara el formulario del modal para Agregar o Editar.
         */
        function prepararFormulario(id, placa, marca, modelo, anio, tipo, km, estado) {
            const defaultTipo = 'Auto'; 
            const defaultEstado = 'Operativo'; 

            document.getElementById('modalTitle').innerText = id ? 'Editar Vehículo' : 'Agregar Nuevo Vehículo';
            document.getElementById('btnGuardar').innerText = id ? 'Actualizar' : 'Guardar';
            
            // Asignación de valores
            document.getElementById('idVehiculo').value = id || '';
            document.getElementById('placaModal').value = placa || '';
            document.getElementById('marcaModal').value = marca || '';
            document.getElementById('modeloModal').value = modelo || '';
            document.getElementById('anioModal').value = anio || '';
            
            // Manejo de kilometraje
            document.getElementById('kilometrajeActualModal').value = km ? parseFloat(km).toFixed(2) : ''; 
            
            // Asignar el valor a los SELECTs
            document.getElementById('tipoVehiculoModal').value = tipo || defaultTipo;
            document.getElementById('estadoModal').value = estado || defaultEstado;
            
            // Lógica de Placa: readOnly en edición para que el valor se envíe
            document.getElementById('placaModal').readOnly = !!id;
            document.getElementById('placaModal').required = true; 
        }
    </script>
    
<%-- ************************************************************************* --%>
</main>
</body>
</html>