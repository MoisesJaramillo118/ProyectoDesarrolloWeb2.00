<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    dto.UsuarioDTO usuario = (sesion != null) ? (dto.UsuarioDTO) sesion.getAttribute("usuario") : null;

    if (usuario == null || !usuario.getRol().equals("Admin")) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=permission");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sugerencias - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />
    
    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-chat-left-text"></i> Sugerencias</h2>
            <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Volver al Dashboard
            </a>
        </div>

        <c:choose>
            <c:when test="${empty sugerencias}">
                <div class="alert alert-info text-center">
                    <i class="bi bi-info-circle fs-1 d-block mb-3"></i>
                    <h4>No hay sugerencias</h4>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                                <th>Asunto</th>
                                <th>Mensaje</th>
                                <th>Fecha</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sugerencia" items="${sugerencias}">
                                <tr>
                                    <td>${sugerencia.id}</td>
                                    <td>${sugerencia.nombre}</td>
                                    <td>${sugerencia.correo}</td>
                                    <td>${sugerencia.asunto}</td>
                                    <td>${sugerencia.mensaje}</td>
                                    <td>
                                        <fmt:formatDate value="${sugerencia.fecha}" pattern="dd/MM/yyyy HH:mm"/>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm btn-danger" 
                                                onclick="if(confirm('¿Eliminar?')) window.location.href='${pageContext.request.contextPath}/sugerencia?accion=eliminar&id=${sugerencia.id}'">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>