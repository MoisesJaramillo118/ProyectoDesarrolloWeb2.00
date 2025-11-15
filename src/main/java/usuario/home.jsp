<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    dto.UsuarioDTO usuario = (sesion != null) ? (dto.UsuarioDTO) sesion.getAttribute("usuario") : null;

    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=session");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio - SimCar</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        .navbar-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 4rem 0;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">
                <i class="bi bi-car-front-fill me-2"></i>SimCar
            </a>
            
            <div class="d-flex align-items-center">
                <span class="text-white me-3">
                    <i class="bi bi-person-circle me-2"></i>
                    Hola, <strong><%= usuario.getNombre() %></strong>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-light btn-sm">
                    <i class="bi bi-box-arrow-right me-1"></i>Salir
                </a>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section text-center">
        <div class="container">
            <h1 class="display-4 fw-bold mb-3">Bienvenido a SimCar</h1>
            <p class="lead">Encuentra el auto de tus sueños</p>
        </div>
    </section>

    <!-- Contenido -->
    <div class="container py-5">
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center p-4">
                        <i class="bi bi-car-front fs-1 text-primary mb-3"></i>
                        <h5 class="card-title fw-bold">Ver Catálogo</h5>
                        <p class="card-text">Explora nuestra amplia selección de vehículos</p>
                        <a href="${pageContext.request.contextPath}/verCarros" class="btn btn-primary">
                            Ver Carros
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center p-4">
                        <i class="bi bi-envelope fs-1 text-success mb-3"></i>
                        <h5 class="card-title fw-bold">Contáctanos</h5>
                        <p class="card-text">¿Tienes preguntas? Estamos aquí para ayudarte</p>
                        <a href="${pageContext.request.contextPath}/vistas/contacto.jsp" class="btn btn-success">
                            Contactar
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>