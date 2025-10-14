<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Sugerencias</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <jsp:include page="/includes/header.jsp" />

    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark rounded-pill">
                <i class="bi bi-arrow-left-circle me-1"></i> Volver
            </a>
            <h2 class="text-primary fw-bold mb-0">
                <i class="bi bi-envelope-paper me-2"></i> Lista de Sugerencias
            </h2>
            <div></div> <!-- Espacio para mantener alineación -->
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Asunto</th>
                        <th>Mensaje</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${listaSugerencias}">
                        <tr>
                            <td class="text-center">${s.id}</td>
                            <td>${s.nombre}</td>
                            <td>${s.correo}</td>
                            <td>${s.asunto}</td>
                            <td>${s.mensaje}</td>
                            <td class="text-center">${s.fecha}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>