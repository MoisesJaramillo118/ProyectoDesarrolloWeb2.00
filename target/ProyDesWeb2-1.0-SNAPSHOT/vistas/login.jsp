<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Inicio de sesión</h2>
        
        <form action="${pageContext.request.contextPath}/login" method="post" class="mx-auto" style="max-width: 400px;">
            <div class="mb-3">
                <label for="correo" class="form-label">Correo</label>
                <input type="text" name="correo" id="correo" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="contraseña" class="form-label">Contraseña</label>
                <input type="password" name="contraseña" id="contraseña" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-success w-100">Ingresar</button>
        </form>

        <c:if test="${param.error == '1'}">
            <div class="alert alert-danger mt-3 text-center">Credenciales incorrectas.</div>
        </c:if>
        <c:if test="${param.error == '2'}">
            <div class="alert alert-warning mt-3 text-center">Error interno. Intenta nuevamente.</div>
        </c:if>
    </div>
</body>
</html>