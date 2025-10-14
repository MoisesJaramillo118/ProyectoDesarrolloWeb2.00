<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Cabecera tipo GLOBAL CARS -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">

        <!-- Marca + nombre del usuario si está logeado -->
        <a class="navbar-brand fw-bold fs-3" href="${pageContext.request.contextPath}/">
            GLOBAL CARS
        </a>
        <c:if test="${not empty sessionScope.usuario}">
            <span class="navbar-text ms-3 text-light">
                👤 ${sessionScope.usuario.nombre}
            </span>
        </c:if>

        <!-- BOTÓN TOGGLER (para móviles) -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Contenido colapsable -->
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav me-3">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vistas/contacto.jsp">Contáctenos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vistas/preguntasFrecuentes.jsp">Preguntas Frecuentes</a>
                </li>
                <c:if test="${not empty sessionScope.usuario}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/vistas/dashboard.jsp">Dashboard</a>
                    </li>
                </c:if>
            </ul>

            <!-- Botones de sesión -->
            <c:choose>
                <c:when test="${empty sessionScope.usuario}">
                    <a href="${pageContext.request.contextPath}/vistas/login.jsp" class="btn btn-outline-light me-2">Iniciar sesión</a>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/logout" method="post" class="d-inline">
                        <button type="submit" class="btn btn-outline-light me-2">Cerrar sesión</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<!-- Scripts de Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Script para que el toggler cierre al hacer click nuevamente -->
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const toggler = document.querySelector('.navbar-toggler');
        const nav = document.getElementById('navbarNav');

        if (toggler && nav) {
            toggler.addEventListener('click', () => {
                const collapse = bootstrap.Collapse.getOrCreateInstance(nav);
                if (nav.classList.contains('show')) {
                    collapse.hide();
                } else {
                    collapse.show();
                }
            });
        }
    });
</script>
