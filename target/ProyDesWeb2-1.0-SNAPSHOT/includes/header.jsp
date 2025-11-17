<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Cabecera tipo GLOBAL CARS -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <!-- Marca + nombre del usuario si está logeado -->
        <a class="navbar-brand fw-bold fs-3" href="${pageContext.request.contextPath}/">
            🚗 GLOBAL CARS
        </a>
        
        <!-- Información del usuario (solo si está logeado) -->
        <c:if test="${not empty sessionScope.usuario}">
            <span class="navbar-text ms-3 text-light d-none d-lg-inline">
                <i class="bi bi-person-circle"></i> ${sessionScope.usuario.nombre}
                <c:if test="${sessionScope.usuario.rol == 'Admin'}">
                    <span class="badge bg-warning text-dark ms-1">Admin</span>
                </c:if>
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
                <!-- Enlace: Inicio -->
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">
                        <i class="bi bi-house-door"></i> Inicio
                    </a>
                </li>
                
                <!-- Enlace: Contáctenos -->
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vistas/contacto.jsp">
                        <i class="bi bi-envelope"></i> Contáctenos
                    </a>
                </li>
                
                <!-- Enlace: Preguntas Frecuentes -->
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vistas/preguntasFrecuentes.jsp">
                        <i class="bi bi-question-circle"></i> Preguntas Frecuentes
                    </a>
                </li>
                
                <!-- Enlace: Dashboard (solo para usuarios logueados como Admin) -->
                <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.rol == 'Admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/vistas/dashboard.jsp">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                </c:if>
                
                <!-- Información de usuario en móvil -->
                <c:if test="${not empty sessionScope.usuario}">
                    <li class="nav-item d-lg-none">
                        <span class="nav-link text-light">
                            <i class="bi bi-person-circle"></i> ${sessionScope.usuario.nombre}
                            <c:if test="${sessionScope.usuario.rol == 'Admin'}">
                                <span class="badge bg-warning text-dark ms-1">Admin</span>
                            </c:if>
                        </span>
                    </li>
                </c:if>
            </ul>
            
            <!-- Botones de sesión -->
            <div class="d-flex align-items-center">
                <c:choose>
                    <%-- Usuario NO está logueado --%>
                    <c:when test="${empty sessionScope.usuario}">
                        <a href="${pageContext.request.contextPath}/vistas/login.jsp" 
                           class="btn btn-outline-light me-2">
                            <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                        </a>
                        <a href="${pageContext.request.contextPath}/vistas/registro.jsp" 
                           class="btn btn-light">
                            <i class="bi bi-person-plus"></i> Registrarse
                        </a>
                    </c:when>
                    
                    <%-- Usuario SÍ está logueado --%>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/logout" 
                           class="btn btn-outline-light"
                           onclick="return confirm('¿Estás seguro de cerrar sesión?')">
                            <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>

<!-- Scripts de Bootstrap (solo si no están en tu footer) -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<!-- Script para mejorar el comportamiento del navbar en móviles -->
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const navbarCollapse = document.getElementById('navbarNav');
        const navLinks = document.querySelectorAll('#navbarNav .nav-link');
        
        // Cerrar el menú al hacer clic en un enlace (en móviles)
        navLinks.forEach(link => {
            link.addEventListener('click', () => {
                if (window.innerWidth < 992) { // Solo en móviles
                    const bsCollapse = bootstrap.Collapse.getInstance(navbarCollapse);
                    if (bsCollapse) {
                        bsCollapse.hide();
                    }
                }
            });
        });
    });
</script>

<style>
    /* Estilos adicionales para mejorar el navbar */
    .navbar-nav .nav-link {
        transition: all 0.3s ease;
    }
    
    .navbar-nav .nav-link:hover {
        background-color: rgba(255, 255, 255, 0.1);
        border-radius: 5px;
    }
    
    .navbar-brand {
        transition: transform 0.3s ease;
    }
    
    .navbar-brand:hover {
        transform: scale(1.05);
    }
    
    /* Mejorar el badge de Admin */
    .badge.bg-warning {
        font-size: 0.7rem;
        padding: 0.2rem 0.4rem;
        vertical-align: middle;
    }
</style>