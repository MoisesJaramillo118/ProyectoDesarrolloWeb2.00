<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - SimCar</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-container {
            max-width: 450px;
            width: 100%;
        }
        .card {
            border: none;
            border-radius: 1rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .btn-login {
            padding: 0.8rem;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="login-container px-3">
        <div class="card">
            <div class="card-body p-5">
                <div class="text-center mb-4">
                    <i class="bi bi-car-front-fill text-primary" style="font-size: 3rem;"></i>
                    <h2 class="fw-bold mt-3">SimCar</h2>
                    <p class="text-muted">Inicia sesión en tu cuenta</p>
                </div>

                <!-- Mensajes de éxito -->
                <c:if test="${param.logout == '1'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle me-2"></i>
                        Sesión cerrada correctamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.registro == '1'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle me-2"></i>
                        Registro exitoso. Ya puedes iniciar sesión.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Mensajes de error -->
                <c:if test="${param.error == '1'}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Correo o contraseña incorrectos.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '2'}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-circle me-2"></i>
                        Error del servidor. Intenta nuevamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '3'}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-circle me-2"></i>
                        Por favor, completa todos los campos.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == 'session'}">
                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                        <i class="bi bi-info-circle me-2"></i>
                        Tu sesión ha expirado. Por favor, inicia sesión nuevamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Formulario de Login -->
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label for="correo" class="form-label">
                            <i class="bi bi-envelope me-1"></i>Correo Electrónico
                        </label>
                        <input type="email" 
                               name="correo" 
                               id="correo" 
                               class="form-control form-control-lg" 
                               placeholder="tu@correo.com"
                               required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="contraseña" class="form-label">
                            <i class="bi bi-lock me-1"></i>Contraseña
                        </label>
                        <input type="password" 
                               name="contraseña" 
                               id="contraseña" 
                               class="form-control form-control-lg" 
                               placeholder="••••••••"
                               required>
                    </div>

                    <button type="submit" class="btn btn-primary btn-login w-100 mb-3">
                        <i class="bi bi-box-arrow-in-right me-2"></i>Iniciar Sesión
                    </button>
                </form>

                <div class="text-center">
                    <p class="mb-0">¿No tienes cuenta? 
                        <a href="${pageContext.request.contextPath}/vistas/registro.jsp" class="text-decoration-none fw-bold">
                            Regístrate aquí
                        </a>
                    </p>
                </div>

                <!-- Credenciales de prueba -->
                <div class="mt-4 p-3 bg-light rounded">
                    <small class="text-muted d-block mb-2"><strong>Credenciales de prueba:</strong></small>
                    <small class="text-muted d-block">Admin: admin@admin.com / admin</small>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
