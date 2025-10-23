<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="layout.jsp" %>
<%
    request.setAttribute("titulo", "Vista del Empleado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Vista del Empleado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/barraNavegacion.css">
</head>

<body>
    <div class="container my-4">
        <h1 class="h3 mb-4">Panel del Empleado</h1>
        <p class="text-muted">Aquí puedes consultar tus máquinas asignadas, revisar mantenimientos y reportar fallas.</p>

        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card text-bg-primary mb-3 shadow-sm">
                    <div class="card-body text-center">
                        <h5 class="card-title">Máquinas Asignadas</h5>
                        <p class="fs-4 mb-0">5</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-bg-success mb-3 shadow-sm">
                    <div class="card-body text-center">
                        <h5 class="card-title">Mantenimientos Pendientes</h5>
                        <p class="fs-4 mb-0">2</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-bg-warning mb-3 shadow-sm">
                    <div class="card-body text-center">
                        <h5 class="card-title">Fallas Reportadas</h5>
                        <p class="fs-4 mb-0">1</p>
                    </div>
                </div>
            </div>
        </div>

        <h2 class="h4 mb-3">Mis Máquinas</h2>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <!-- Máquina 1 -->
            <div class="col">
                <div class="card shadow-sm">
                    <img src="https://source.unsplash.com/400x250/?truck,machine" class="card-img-top" alt="Camión Volvo A1">
                    <div class="card-body text-center">
                        <h5 class="card-title">Camión Volvo A1</h5>
                        <p><span class="badge bg-success">Activo</span></p>
                        <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                        <a href="reportarFalla.jsp" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>

                        <div class="mt-3">
                            <small class="text-muted">Antigüedad: 35%</small>
                            <div class="progress">
                                <div class="progress-bar bg-info" role="progressbar" style="width: 35%;" aria-valuenow="35" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card shadow-sm">
                    <img src="https://source.unsplash.com/400x250/?excavator" class="card-img-top" alt="Excavadora X5">
                    <div class="card-body text-center">
                        <h5 class="card-title">Excavadora X5</h5>
                        <p><span class="badge bg-warning text-dark">En Mantenimiento</span></p>
                        <a href="#" class="btn btn-primary btn-sm">Detalles</a>
                        <a href="reportarFalla.jsp" class="btn btn-danger btn-sm ms-2">Reportar Falla</a>

                        <div class="mt-3">
                            <small class="text-muted">Antigüedad: 65%</small>
                            <div class="progress">
                                <div class="progress-bar bg-warning" role="progressbar" style="width: 65%;" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <h2 class="h4 mt-5 mb-3">Mapa de mis máquinas</h2>
        <div style="width:100%; height:400px; background:#eaeaea; display:flex; align-items:center; justify-content:center; border-radius:8px;">
            <span class="text-muted">[Aquí iría un mapa con la ubicación de las máquinas asignadas]</span>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>