<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Global Cars</title>
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
        .registro-container {
            max-width: 500px;
            width: 100%;
        }
        .card {
            border: none;
            border-radius: 1rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .btn-registro {
            padding: 0.8rem;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="registro-container px-3">
        <div class="card">
            <div class="card-body p-5">
                <div class="text-center mb-4">
                    <i class="bi bi-person-plus-fill text-success" style="font-size: 3rem;"></i>
                    <h2 class="fw-bold mt-3">Crear Cuenta</h2>
                    <p class="text-muted">Regístrate en Global Cars</p>
                </div>

                <!-- Mensajes de error -->
                <c:if test="${param.error == '1'}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Por favor, completa todos los campos.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '2'}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Las contraseñas no coinciden.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '3'}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        El correo ya está registrado.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '4'}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        No se pudo completar el registro. Intenta nuevamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.error == '5'}">
                    <div class="alert alert-warning alert-dismissible fade show">
                        <i class="bi bi-exclamation-circle me-2"></i>
                        Error del servidor. Intenta nuevamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Formulario de Registro -->
                <form action="${pageContext.request.contextPath}/registro" method="post" id="formRegistro">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">
                            <i class="bi bi-person me-1"></i>Nombre Completo
                        </label>
                        <input type="text" 
                               name="nombre" 
                               id="nombre" 
                               class="form-control form-control-lg" 
                               placeholder="Juan Pérez"
                               required>
                    </div>

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
                               minlength="6"
                               required>
                        <small class="text-muted">Mínimo 6 caracteres</small>
                    </div>

                    <div class="mb-4">
                        <label for="confirmarContraseña" class="form-label">
                            <i class="bi bi-lock-fill me-1"></i>Confirmar Contraseña
                        </label>
                        <input type="password" 
                               name="confirmarContraseña" 
                               id="confirmarContraseña" 
                               class="form-control form-control-lg" 
                               placeholder="••••••••"
                               required>
                    </div>

                    <button type="submit" class="btn btn-success btn-registro w-100 mb-3">
                        <i class="bi bi-check-circle me-2"></i>Crear Cuenta
                    </button>
                </form>

                <div class="text-center">
                    <p class="mb-0">¿Ya tienes cuenta? 
                        <a href="${pageContext.request.contextPath}/vistas/login.jsp" 
                           class="text-decoration-none fw-bold">
                            Inicia sesión aquí
                        </a>
                    </p>
                </div>

                <!-- Enlace a inicio -->
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/" 
                       class="btn btn-outline-secondary btn-sm">
                        <i class="bi bi-arrow-left me-1"></i>Volver al Inicio
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validar que las contraseñas coincidan antes de enviar
        document.getElementById('formRegistro').addEventListener('submit', function(e) {
            const pass = document.getElementById('contraseña').value;
            const confirmPass = document.getElementById('confirmarContraseña').value;
            
            if (pass !== confirmPass) {
                e.preventDefault();
                alert('Las contraseñas no coinciden. Por favor, verifica.');
                document.getElementById('confirmarContraseña').focus();
            }
        });

        // Auto-cerrar alertas después de 5 segundos
        document.addEventListener('DOMContentLoaded', function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                setTimeout(() => {
                    const bsAlert = new bootstrap.Alert(alert);
                    bsAlert.close();
                }, 5000);
            });
        });
    </script>
</body>
</html>