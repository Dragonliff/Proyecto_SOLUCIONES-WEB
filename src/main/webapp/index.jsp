<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Acceso - Mantenimiento Predictivo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #212529; /* Color de respaldo */
            /* Imagen de fondo relacionada a autos/taller */
            background-image: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.7)), 
                              url('https://images.unsplash.com/photo-1487754180451-c456f719a1fc?q=80&w=2070&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
        }

        .login-card {
            width: 100%;
            max-width: 420px;
            border: none;
            border-radius: 15px;
            backdrop-filter: blur(10px); /* Efecto cristal */
            background: rgba(255, 255, 255, 0.95);
            box-shadow: 0 15px 35px rgba(0,0,0,0.5);
            overflow: hidden;
        }

        .card-header-custom {
            background: #0d6efd; /* Azul Bootstrap, puedes cambiarlo por el color de tu marca */
            color: white;
            padding: 20px;
            text-align: center;
        }

        .btn-custom {
            background-color: #0d6efd;
            border: none;
            padding: 12px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-custom:hover {
            background-color: #0b5ed7;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(13, 110, 253, 0.3);
        }
        
        .input-group-text {
            background-color: #f8f9fa;
            border-right: none;
        }
        
        .form-floating .form-control {
            border-left: none;
        }

        .brand-logo {
            font-size: 3rem;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

    <div class="card login-card fade-in">
        <div class="card-header-custom">
            <i class="bi bi-car-front-fill brand-logo"></i>
            <h4 class="m-0 fw-bold">CRONIX</h4>
            <small>Sistema de Mantenimiento</small>
        </div>

        <div class="card-body p-4 pt-5">
            
            <% 
                String error = (String) request.getAttribute("mensajeError");
                if (error != null && !error.trim().isEmpty()) { 
            %>
                <div class="alert alert-danger d-flex align-items-center mb-4" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <div><%= error %></div>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                
                <div class="input-group mb-4">
                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="correo" name="correo" placeholder="name@example.com" required>
                        <label for="correo">Correo Electrónico</label>
                    </div>
                </div>

                <div class="input-group mb-4">
                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                        <label for="password">Contraseña</label>
                    </div>
                </div>

                <div class="d-grid gap-2 mb-3">
                    <button type="submit" class="btn btn-primary btn-custom btn-lg">
                        Iniciar Sesión <i class="bi bi-arrow-right-short"></i>
                    </button>
                </div>

            </form>
        </div>
        
        <div class="card-footer text-center py-3 bg-light">
            <small class="text-muted">&copy; 2025 Mantenimiento Predictivo v1.0</small>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>