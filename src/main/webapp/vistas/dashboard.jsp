<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // ✅ VALIDACIÓN DE SESIÓN Y ROL
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    dto.UsuarioDTO usuario = (sesion != null) ? (dto.UsuarioDTO) sesion.getAttribute("usuario") : null;

    // Si no hay sesión o no es admin, redirigir
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=session");
        return;
    }
    
    if (!usuario.getRol().equals("Admin")) {
        response.sendRedirect(request.getContextPath() + "/?error=permission");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Administrativo - Global Cars</title>
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <!-- Tu CSS personalizado (si tienes) -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    
    <style>
        /* Estilos para las cards del dashboard */
        .dashboard-card {
            transition: all 0.3s ease;
            border: none;
            border-radius: 15px;
            overflow: hidden;
        }
        
        .dashboard-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        
        .dashboard-card .card-body {
            padding: 2rem;
        }
        
        .dashboard-card i {
            font-size: 3.5rem;
            margin-bottom: 1rem;
        }
        
        .dashboard-title {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
            border-radius: 10px;
        }
        
        .stat-badge {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 0.8rem;
        }
    </style>
</head>
<body class="bg-light">
    
    <!-- ✅ INCLUIR TU HEADER ORIGINAL -->
    <jsp:include page="../includes/header.jsp" />
    
    <!-- CONTENIDO DEL DASHBOARD -->
    <div class="container py-5">
        
        <!-- Título del Dashboard -->
        <div class="dashboard-title text-center shadow-lg">
            <h1 class="fw-bold mb-2">
                <i class="bi bi-speedometer2"></i> Panel Administrativo
            </h1>
            <p class="mb-0 fs-5">Bienvenido, <%= usuario.getNombre() %></p>
        </div>
        
        <!-- Mensajes de alerta -->
        <c:if test="${param.error == 'permission'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle me-2"></i>
                <strong>Acceso Denegado:</strong> No tienes permisos para acceder a esa sección.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <c:if test="${param.success != null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle me-2"></i>
                <strong>Éxito:</strong> Operación completada correctamente.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <!-- Grid de Opciones Administrativas -->
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            
            <!-- Card 1: Editar Empresa -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/empresa" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-warning bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-building text-warning"></i>
                            <h5 class="card-title fw-bold text-dark">Editar Empresa</h5>
                            <p class="card-text text-muted">
                                Gestiona la información corporativa de tu empresa
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
            <!-- Card 2: Agregar Carro -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/carro?accion=nuevo" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-success bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-plus-square text-success"></i>
                            <h5 class="card-title fw-bold text-dark">Agregar Carro</h5>
                            <p class="card-text text-muted">
                                Añade nuevos vehículos al catálogo
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
            <!-- Card 3: Administrar Carros -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/carro" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-primary bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-car-front-fill text-primary"></i>
                            <h5 class="card-title fw-bold text-dark">Administrar Carros</h5>
                            <p class="card-text text-muted">
                                Ver, editar y eliminar vehículos
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
            <!-- Card 4: Ver Sugerencias -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/sugerencia?accion=listar" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-info bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-chat-left-text text-info"></i>
                            <h5 class="card-title fw-bold text-dark">Ver Sugerencias</h5>
                            <p class="card-text text-muted">
                                Revisa las sugerencias de los clientes
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
            <!-- Card 5: Libro de Reclamaciones -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/libro" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-danger bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-exclamation-triangle text-danger"></i>
                            <h5 class="card-title fw-bold text-dark">Libro de Reclamaciones</h5>
                            <p class="card-text text-muted">
                                Gestiona los reclamos y quejas
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
            <!-- Card 6: Ver Contactos -->
            <div class="col">
                <a href="${pageContext.request.contextPath}/contacto" class="text-decoration-none">
                    <div class="card dashboard-card h-100 shadow-sm bg-secondary bg-opacity-10">
                        <div class="card-body text-center position-relative">
                            <i class="bi bi-envelope text-secondary"></i>
                            <h5 class="card-title fw-bold text-dark">Ver Contactos</h5>
                            <p class="card-text text-muted">
                                Revisa los mensajes recibidos
                            </p>
                        </div>
                    </div>
                </a>
            </div>
            
        </div>
        
        <!-- Sección de estadísticas rápidas (opcional) -->
        <div class="row mt-5">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title mb-3">
                            <i class="bi bi-graph-up"></i> Accesos Rápidos
                        </h5>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-primary w-100">
                                    <i class="bi bi-house-door"></i> Ver Sitio Web
                                </a>
                            </div>
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/carro" class="btn btn-outline-success w-100">
                                    <i class="bi bi-list-ul"></i> Lista de Carros
                                </a>
                            </div>
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/logout" 
                                   class="btn btn-outline-danger w-100"
                                   onclick="return confirm('¿Estás seguro de cerrar sesión?')">
                                    <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    
    <!-- ✅ INCLUIR TU FOOTER ORIGINAL (si tienes) -->
    <jsp:include page="../includes/footer.jsp" />
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Script adicional para mejorar UX -->
    <script>
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