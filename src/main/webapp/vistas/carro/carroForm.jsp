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
        <title>${carroEditar != null ? "Editar Carro" : "Agregar Carro"}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    </head>
    <body>

        <jsp:include page="/includes/header.jsp" />

        <div class="container py-5">
            <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark mb-4 rounded-pill">
                <i class="bi bi-arrow-left-circle me-1"></i> Volver al panel
            </a>

            <div class="card shadow-lg rounded-4">
                <div class="card-header bg-primary text-white text-center rounded-top-4">
                    <h4 class="mb-0">
                        ${carroEditar != null ? "✏️ Editar Carro" : "➕ Agregar Carro"}
                    </h4>
                </div>
                <div class="card-body">
                    <!-- Div para mensajes AJAX -->
                    <div id="mensaje" class="mb-3"></div>

                    <form id="carroForm" action="${pageContext.request.contextPath}/carro" method="post" enctype="multipart/form-data" class="row g-4">

                        <c:if test="${carroEditar != null}">
                            <input type="hidden" name="accion" value="actualizar">
                            <input type="hidden" name="id" value="${carroEditar.id}">
                            <input type="hidden" name="imagenActual" value="${carroEditar.imagen}">
                        </c:if>
                        <c:if test="${carroEditar == null}">
                            <input type="hidden" name="accion" value="insertar">
                        </c:if>

                        <div class="col-md-6">
                            <label class="form-label fw-semibold">
                                <i class="bi bi-card-text me-1"></i> Nombre
                            </label>
                            <input type="text" name="nombre" class="form-control rounded-3"
                                   value="${carroEditar.nombre}" required>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label fw-semibold">
                                <i class="bi bi-currency-dollar me-1"></i> Precio (S/.)
                            </label>
                            <input type="number" step="0.01" name="precio" class="form-control rounded-3"
                                   value="${carroEditar.precio}" required>
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-semibold">
                                <i class="bi bi-info-circle me-1"></i> Descripción
                            </label>
                            <textarea name="descripcion" class="form-control rounded-3" rows="3" required>${carroEditar.descripcion}</textarea>
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-semibold">
                                <i class="bi bi-image me-1"></i> Adjuntar Imagen del Carro
                            </label>
                            <input type="file" name="archivoImagen" class="form-control rounded-3" accept="image/*" <c:if test="${carroEditar == null}">required</c:if>>
                                <small class="text-muted">Formatos permitidos: JPG, PNG. Se guardará en la carpeta <code>/img</code>.</small>
                            </div>

                            <div class="col-12 text-center mt-4">
                                <button type="submit" class="btn btn-success px-5 py-2 rounded-pill fw-bold">
                                    <i class="bi bi-save me-1"></i> Guardar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- jQuery -->
            <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>                    
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                $(document).ready(function () {
                    $("#carroForm").on("submit", function (e) {
                        e.preventDefault();

                        const formData = new FormData(this);

                        $.ajax({
                            url: this.action,
                            type: "POST",
                            data: formData,
                            processData: false,
                            contentType: false,
                            headers: {"X-Requested-With": "XMLHttpRequest"},
                            dataType: "json",
                            success: function (data) {
                                const mensajeDiv = $("#mensaje");
                                if (data.success) {
                                    mensajeDiv.html(
                                            '<div class="alert alert-success alert-dismissible fade show" role="alert">'
                                            + data.message +
                                            '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>'
                                            + '</div>'
                                            );
                                    setTimeout(() => window.location.href = "${pageContext.request.contextPath}/carro", 2000);
                                } else {
                                    mensajeDiv.html(
                                            '<div class="alert alert-danger alert-dismissible fade show" role="alert">'
                                            + data.message +
                                            '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>'
                                            + '</div>'
                                            );
                                }
                            },
                            error: function (xhr, status, error) {
                                $("#mensaje").html(
                                        '<div class="alert alert-danger">Error inesperado: ' + error + '</div>'
                                        );
                            }
                        });
                    });
                });
        </script>
    </body>
</html>