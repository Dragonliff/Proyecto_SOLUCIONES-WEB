<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("titulo", "Inventario de Activos");
%>
<%@ include file="layout.jsp" %>

<div class="container-fluid">
    <h2 class="mb-4">Inventario de Activos</h2>
    <p class="text-muted">Visualiza y gestiona la maquinaria, vehículos y herramientas registradas en tu PYME.</p>

    <h4 class="mt-5 mb-3">Maquinaria</h4>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?excavator" class="card-img-top" alt="Excavadora ZX200">
                <div class="card-body text-center">
                    <h5 class="card-title">Excavadora ZX200</h5>
                    <p><span class="badge bg-success">Operativa</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Uso: 65%</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 65%;"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?generator,machine" class="card-img-top" alt="Generador Diesel">
                <div class="card-body text-center">
                    <h5 class="card-title">Generador Diesel</h5>
                    <p><span class="badge bg-warning text-dark">En Reparación</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Uso: 45%</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 45%;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <h4 class="mt-5 mb-3">Vehículos</h4>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <!-- Card Camión -->
        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?truck,volvo" class="card-img-top" alt="Camión Volvo FH">
                <div class="card-body text-center">
                    <h5 class="card-title">Camión Volvo FH</h5>
                    <p><span class="badge bg-success">Operativo</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Kilometraje: 245,000 km</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 70%;"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?pickup,toyota" class="card-img-top" alt="Toyota Hilux">
                <div class="card-body text-center">
                    <h5 class="card-title">Pickup Toyota Hilux</h5>
                    <p><span class="badge bg-success">Disponible</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Kilometraje: 88,000 km</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 30%;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <h4 class="mt-5 mb-3">Herramientas</h4>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <!-- Card Taladro -->
        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?drill,tool" class="card-img-top" alt="Taladro Bosch GSB">
                <div class="card-body text-center">
                    <h5 class="card-title">Taladro Bosch GSB</h5>
                    <p><span class="badge bg-success">Disponible</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Último uso: 12/09/2025</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 20%;"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card shadow-sm h-100">
                <img src="https://source.unsplash.com/400x250/?compressor,tool" class="card-img-top" alt="Compresor de Aire">
                <div class="card-body text-center">
                    <h5 class="card-title">Compresor de Aire</h5>
                    <p><span class="badge bg-warning text-dark">En uso</span></p>
                    <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                    <a href="#" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>
                </div>
                <div class="card-footer text-muted">
                    <small>Último mantenimiento: Ago 2025</small>
                    <div class="progress">
                        <div class="progress-bar bg-info" style="width: 50%;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>