<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error del Servidor - SimCar</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center align-items-center" style="min-height: 100vh;">
            <div class="col-md-6 text-center">
                <i class="bi bi-exclamation-octagon text-danger" style="font-size: 5rem;"></i>
                <h1 class="display-4 fw-bold mt-4">500</h1>
                <h2 class="mb-4">Error Interno del Servidor</h2>
                <p class="lead text-muted mb-4">
                    Algo salió mal. Por favor, intenta nuevamente más tarde.
                </p>
                <a href="${pageContext.request.contextPath}/vistas/login.jsp" class="btn btn-primary">
                    <i class="bi bi-house-door me-2"></i>Volver al Inicio
                </a>
            </div>
        </div>
    </div>
</body>
</html>