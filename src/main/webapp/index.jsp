<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Plataforma de Mantenimiento Predictivo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .login-card {
            max-width: 400px;
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="card shadow login-card p-4">
        <h3 class="text-center mb-4">Plataforma de Mantenimiento Predictivo</h3>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="form-group">
                <label for="correo">Correo:</label>
                <input type="text" id="correo" name="correo" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">Iniciar sesión</button>
        </form>
        
        <p style="color:red;">
            ${mensajeError}
        </p>
    </div>
</body>
</html>