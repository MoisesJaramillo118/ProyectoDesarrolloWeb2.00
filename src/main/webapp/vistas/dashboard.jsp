<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    dto.UsuarioDTO usuario = (sesion != null) ? (dto.UsuarioDTO) sesion.getAttribute("usuario") : null;

    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard Administrativo</title>
        <jsp:include page="../includes/header.jsp" />
        <link rel="stylesheet" href="../css/dashboard.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    </head>
    <body>

        <div class="container py-5">
            <h2 class="text-center mb-5 fw-bold text-primary">Panel Administrativo</h2>

            <div class="row row-cols-1 row-cols-md-3 g-4">
                <!-- Card: Editar Empresa -->
                <div class="col">
                    <a href="${pageContext.request.contextPath}/empresa" class="text-decoration-none">
                        <div class="card h-100 shadow-sm rounded-4 text-center p-4 border-0 bg-warning bg-opacity-25">
                            <i class="bi bi-building fs-1 text-warning mb-3"></i>
                            <h5 class="fw-bold text-dark">Editar Empresa</h5>
                        </div>
                    </a>
                </div>

                <!-- Card: Agregar Carro -->
                <div class="col">
                    <a href="${pageContext.request.contextPath}/carro?accion=nuevo" class="text-decoration-none">
                        <div class="card h-100 shadow-sm rounded-4 text-center p-4 border-0 bg-success bg-opacity-25">
                            <i class="bi bi-plus-square fs-1 text-success mb-3"></i>
                            <h5 class="fw-bold text-dark">Agregar Carro</h5>
                        </div>
                    </a>
                </div>

                <!-- Card: Administrar Carros -->
                <div class="col">
                    <a href="${pageContext.request.contextPath}/carro" class="text-decoration-none">
                        <div class="card h-100 shadow-sm rounded-4 text-center p-4 border-0 bg-primary bg-opacity-25">
                            <i class="bi bi-gear fs-1 text-primary mb-3"></i>
                            <h5 class="fw-bold text-dark">Administrar Carros</h5>
                        </div>
                    </a>
                </div>

                <!-- Card: Ver Sugerencias -->
                <div class="col">
                    <a href="${pageContext.request.contextPath}/sugerencia?accion=listar" class="text-decoration-none">
                        <div class="card h-100 shadow-sm rounded-4 text-center p-4 border-0 bg-info bg-opacity-25">
                            <i class="bi bi-chat-left-text fs-1 text-info mb-3"></i>
                            <h5 class="fw-bold text-dark">Ver Sugerencias</h5>
                        </div>
                    </a>
                </div>

                <!-- Card: Ver Reclamos -->
                <div class="col">
                    <a href="${pageContext.request.contextPath}/libro" class="text-decoration-none">
                        <div class="card h-100 shadow-sm rounded-4 text-center p-4 border-0 bg-danger bg-opacity-25">
                            <i class="bi bi-exclamation-triangle fs-1 text-danger mb-3"></i>
                            <h5 class="fw-bold text-dark">Ver Reclamos</h5>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>