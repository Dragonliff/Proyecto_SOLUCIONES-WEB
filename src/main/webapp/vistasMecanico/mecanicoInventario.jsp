<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.herramientas" %>
<%@ page import="Modelo.usos_herramientas" %>
<%@ page import="java.util.List" %>
<%@ include file="../seguridad.jsp" %>
<%@ include file="layoutMecanicos.jsp" %>
<%

    request.setAttribute("titulo", "Inventario de Herramientas");


    List<usos_herramientas> listaUsos = (List<usos_herramientas>) request.getAttribute("registrosUso");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
    
    List<herramientas> herramientasAsignadas = (List<herramientas>) request.getAttribute("herramientasAsignadas");
%>


<div id="contenido-centrado" class="py-2 px-4"> 
    
    <div> 
        
        <h1 class="display-6 fw-bold mb-0 text-primary text-center">
            <i class="bi bi-tools me-2"></i>Registrar Reporte Uso
        </h1>
        <div class="border-bottom pb-4 mb-4"></div>


        <div class="card shadow-lg mb-5 p-5 rounded-4 border-0 bg-white">
            <h4 class="card-title text-success mb-4 border-bottom pb-2">
                <i class="bi bi-journal-check me-2"></i>Registrar Nuevo Uso de Herramienta
            </h4>
            
            <% if (mensaje != null) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i><%= mensaje %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>
            <% if (error != null) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-x-octagon-fill me-2"></i><%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/UsosHerramientasServlet" method="POST" class="row g-4">
                
                <div class="col-md-6">
                    <label for="idHerramienta" class="form-label fw-bold fs-5">Herramienta Usada:</label>
                    <select id="idHerramienta" name="idHerramienta" class="form-select form-select-lg rounded-3" onchange="mostrarAlerta(this.value)" required> 
                        <option value="">-- Seleccione una herramienta asignada --</option>
                        <% 
                            if (herramientasAsignadas != null && !herramientasAsignadas.isEmpty()) {
                                for (herramientas h : herramientasAsignadas) {
                        %>
                                    <option value="<%= h.getIdHerramienta() %>">
                                        <%= h.getNombre() %> (ID: <%= h.getIdHerramienta() %>)
                                    </option>
                        <% 
                                }
                            } else {
                        %>
                                <option value="" disabled>No hay herramientas asignadas.</option>
                        <% } %>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="horasUso" class="form-label fw-bold fs-5">Tiempo de Uso (Horas, Ej: 1.5):</label>
                    <input type="number" step="0.01" id="horasUso" name="horasUso" class="form-control form-control-lg rounded-3" required placeholder="Ingrese las horas de uso" min="0">
                </div>

                <div class="col-12">
                    <label for="observaciones" class="form-label fw-bold fs-5">Condición Observada (Detalle para Mantenimiento):</label>
                    <textarea id="observaciones" name="observaciones" class="form-control rounded-3" rows="3" required placeholder="Escriba aquí cualquier observación sobre la condición de la herramienta (desgaste, ruido, funcionamiento óptimo, etc.)"></textarea>
                </div>
                
                <div class="col-12" id="alerta-dinamica">
                </div>
                    
                <div class="col-12 mt-4">
                    <input type="hidden" name="accion" value="RegistrarUso">
                    <button type="submit" class="btn btn-success btn-lg w-100 shadow-lg rounded-pill fw-bold text-white">
                        <i class="bi bi-hdd-fill me-2"></i>Guardar Registro de Uso
                    </button>
                </div>
            </form>
        </div>
    </div>
    
</div>
                    
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
    // Define el contextPath para que la URL AJAX funcione correctamente
    var contextPath = "<%= request.getContextPath() %>";

    function mostrarAlerta(idHerramienta) {
        var alertaDiv = $('#alerta-dinamica');
        
        // Limpiar el aviso anterior
        alertaDiv.empty();

        if (idHerramienta && idHerramienta !== "") { 
            
            // Mensaje de carga mientras se espera la respuesta del Servlet
            alertaDiv.html('<div class="text-info"><i class="bi bi-arrow-clockwise spin"></i> Cargando alerta...</div>');

            // Llamada AJAX al UsosHerramientasServlet
            $.ajax({
                url: contextPath + '/UsosHerramientasServlet', 
                type: 'GET',
                data: {
                    accion: 'obtenerAlerta', 
                    idHerramienta: idHerramienta
                },
                success: function(response) {
                    // response es el HTML de la alerta devuelto por el Servlet
                    alertaDiv.html(response);
                },
                error: function() {
                    alertaDiv.html('<div class="alert alert-danger"><i class="bi bi-x-octagon-fill me-2"></i> Error: No se pudo cargar la alerta de la herramienta.</div>');
                }
            });
        }
    }
</script>