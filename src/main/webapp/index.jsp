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
            overflow: hidden; /* Importante para que el video no genere scroll */
            background-color: #000; /* Fondo negro por si el video tarda en cargar */
        }

        /* --- CONFIGURACIÓN DEL VIDEO DE FONDO --- */
        .video-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -1; /* Se asegura que esté detrás de todo */
        }

        .video-background video {
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            object-fit: cover; /* Hace que el video cubra toda la pantalla sin deformarse */
        }

        /* Capa oscura para que las letras blancas se lean bien */
        .video-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.65); /* 65% de oscuridad */
            z-index: 1;
        }

        /* --- ESTILOS DE LA TARJETA DE LOGIN --- */
        .login-card {
            width: 100%;
            max-width: 400px;
            border: none;
            border-radius: 12px;
            /* Efecto cristal (Glassmorphism) */
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.5);
            z-index: 2; /* Debe estar por encima del video */
            overflow: hidden;
        }

        .card-header-custom {
            background: #0d6efd; /* Azul principal */
            color: white;
            padding: 25px 20px;
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
        }
        
        /* Ajustes para inputs modernos */
        .input-group-text {
            background-color: #fff;
            border-right: none;
        }
        .form-floating .form-control {
            border-left: none;
            background-color: #fff;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #ced4da;
        }
    </style>
</head>
<body>

    <div class="video-background">
        <video autoplay muted loop playsinline>
            <source src="video/fondo_login.mp4" type="video/mp4">
            Tu navegador no soporta videos HTML5.
        </video>
        <div class="video-overlay"></div>
    </div>

    <div class="card login-card animate__animated animate__fadeIn">
        <div class="card-header-custom">
            <i class="bi bi-car-front-fill" style="font-size: 3rem;"></i>
            <h4 class="mt-2 fw-bold">CRONIX</h4>
            <small>Sistema de Mantenimiento</small>
        </div>

        <div class="card-body p-4 pt-4">
            
            <% 
                String error = (String) request.getAttribute("mensajeError");
                if (error != null && !error.trim().isEmpty()) { 
            %>
                <div class="alert alert-danger d-flex align-items-center mb-3" role="alert">
                    <i class="bi bi-exclamation-circle-fill me-2"></i>
                    <small><%= error %></small>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                
                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="correo" name="correo" placeholder="correo" required>
                        <label for="correo">Correo Electrónico</label>
                    </div>
                </div>

                <div class="input-group mb-4">
                    <span class="input-group-text"><i class="bi bi-key"></i></span>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password" placeholder="pass" required>
                        <label for="password">Contraseña</label>
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-custom btn-lg">
                        INGRESAR
                    </button>
                </div>

                <div class="text-center mt-3">
                    <small class="text-muted">© 2025 Mantenimiento </small>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>