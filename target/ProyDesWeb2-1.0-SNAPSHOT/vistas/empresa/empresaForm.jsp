<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.EmpresaDTO" %>
<%
    EmpresaDTO emp = (EmpresaDTO) request.getAttribute("empresaEditar");
    boolean existe = (emp != null);
    boolean editando = "true".equals(request.getParameter("editando"));
%>

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
    <title>Información de la Empresa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header -->
    <jsp:include page="/includes/header.jsp" />

    <!-- Contenido principal -->
<main class="flex-grow-1 py-5 bg-light">
    <div class="container">
        <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark rounded-pill mb-4">
            <i class="bi bi-arrow-left-circle me-1"></i> Volver
        </a>
        <h2 class="mb-4 text-center text-primary fw-bold">
            <i class="bi bi-building me-2"></i> Información de la Empresa
        </h2>

        <% if (existe && !editando) { %>
            <div class="card shadow-lg rounded-4 mb-4">
                <div class="card-body">
                    <p><i class="bi bi-file-earmark-text me-1"></i><strong>RUC:</strong> <%= emp.getRuc() %></p>
                    <p><i class="bi bi-building me-1"></i><strong>Razón Social:</strong> <%= emp.getRazonSocial() %></p>
                    <p><i class="bi bi-shop me-1"></i><strong>Nombre Comercial:</strong> <%= emp.getNombreComercial() %></p>
                    <p><i class="bi bi-geo-alt me-1"></i><strong>Dirección:</strong> <%= emp.getDireccion() %></p>
                    <p><i class="bi bi-telephone me-1"></i><strong>Teléfono:</strong> <%= emp.getTelefono() %></p>
                    <p><i class="bi bi-envelope me-1"></i><strong>Correo:</strong> <%= emp.getCorreo() %></p>
                </div>
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/empresa?editando=true" class="btn btn-primary rounded-pill px-4">
                    <i class="bi bi-pencil-square me-1"></i> Editar
                </a>
            </div>

        <% } else { %>
            <div class="row justify-content-center">
                <div class="col-lg-6 col-md-8">
                    <div class="card shadow-lg rounded-4">
                        <div class="card-body p-4">
                            <h4 class="text-center text-primary mb-4">
                                <%= existe ? "️ Editar Empresa" : " Registrar Empresa" %>
                            </h4>

                            <form action="${pageContext.request.contextPath}/empresa" method="post">
                                <input type="hidden" name="accion" value="<%= existe ? "actualizar" : "insertar" %>">
                                <% if (existe) { %>
                                    <input type="hidden" name="id" value="<%= emp.getId() %>">
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-file-earmark-text me-1"></i> RUC</label>
                                    <input type="text" class="form-control rounded-3" name="ruc" value="<%= existe ? emp.getRuc() : "" %>" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-building me-1"></i> Razón Social</label>
                                    <input type="text" class="form-control rounded-3" name="razonSocial" value="<%= existe ? emp.getRazonSocial() : "" %>" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-shop me-1"></i> Nombre Comercial</label>
                                    <input type="text" class="form-control rounded-3" name="nombreComercial" value="<%= existe ? emp.getNombreComercial() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-geo-alt me-1"></i> Dirección</label>
                                    <input type="text" class="form-control rounded-3" name="direccion" value="<%= existe ? emp.getDireccion() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-telephone me-1"></i> Teléfono</label>
                                    <input type="text" class="form-control rounded-3" name="telefono" value="<%= existe ? emp.getTelefono() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold"><i class="bi bi-envelope me-1"></i> Correo</label>
                                    <input type="email" class="form-control rounded-3" name="correo" value="<%= existe ? emp.getCorreo() : "" %>">
                                </div>

                                <div class="d-flex justify-content-between mt-4">
                                    <button type="submit" class="btn btn-success rounded-pill px-4">
                                        <i class="bi bi-save me-1"></i> <%= existe ? "Actualizar" : "Registrar" %>
                                    </button>
                                    <% if (existe) { %>
                                        <a href="${pageContext.request.contextPath}/empresa" class="btn btn-secondary rounded-pill px-4">Cancelar</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
</main>

    <!-- Footer -->
    <jsp:include page="/includes/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bun
