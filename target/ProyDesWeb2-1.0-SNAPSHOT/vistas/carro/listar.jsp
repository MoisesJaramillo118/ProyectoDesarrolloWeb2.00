<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Carros</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>

    <!-- Header -->
    <jsp:include page="/includes/header.jsp" />

    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark rounded-pill">
                <i class="bi bi-arrow-left-circle me-1"></i> Volver
            </a>
            <h2 class="fw-bold text-primary">Lista de Carros</h2>
            <a href="${pageContext.request.contextPath}/carro?accion=nuevo" class="btn btn-outline-primary rounded-pill">
                <i class="bi bi-plus-circle me-1"></i> Agregar nuevo carro
            </a>
        </div>

        <c:choose>
            <c:when test="${not empty listaCarros}">
                <div class="table-responsive">
                    <table class="table table-hover align-middle shadow-sm">
                        <thead class="table-primary text-center">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Imagen</th>
                                <th>Precio</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${listaCarros}">
                                <tr>
                                    <td class="text-center">${c.id}</td>
                                    <td>${c.nombre}</td>
                                    <td>${c.descripcion}</td>
                                    <td class="text-center">
                                        <img src="${pageContext.request.contextPath}/img/${c.imagen}"
                                             alt="${c.nombre}" width="100" class="rounded shadow-sm">
                                    </td>
                                    <td class="fw-bold text-success">S/. ${c.precio}</td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/carro?accion=editar&id=${c.id}"
                                           class="btn btn-sm btn-warning rounded-pill me-1">
                                           <i class="bi bi-pencil-square"></i> Editar
                                        </a>
                                        <a href="${pageContext.request.contextPath}/carro?accion=eliminar&id=${c.id}"
                                           class="btn btn-sm btn-danger rounded-pill"
                                           onclick="return confirm('¿Estás seguro de eliminar este carro?');">
                                           <i class="bi bi-trash"></i> Eliminar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning text-center mt-4">
                    No hay carros registrados aún.
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>