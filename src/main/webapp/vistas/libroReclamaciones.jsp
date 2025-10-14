<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Libro de Reclamaciones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Cabecera -->
    <jsp:include page="/includes/header.jsp" />

    <!-- Contenido principal -->
    <main class="flex-grow-1 d-flex align-items-center py-5 bg-light">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10">
                    <div class="card shadow">
                        <div class="card-body p-4">
                            <h3 class="card-title text-center mb-4 fw-bold text-danger"> Libro de Reclamaciones</h3>
                            <p class="text-muted text-center mb-4">
                                Este formulario está destinado a presentar reclamos y quejas conforme a la normativa vigente.
                            </p>

                            <!-- Mensajes -->
                            <c:if test="${not empty mensajeExito}">
                                <div class="alert alert-success text-center">${mensajeExito}</div>
                            </c:if>
                            <c:if test="${not empty mensajeError}">
                                <div class="alert alert-danger text-center">${mensajeError}</div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/libro" method="post" novalidate>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Nombre completo</label>
                                        <input type="text" name="nombre" class="form-control" required>
                                        <div class="invalid-feedback">Campo obligatorio.</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Correo electrónico</label>
                                        <input type="email" name="correo" class="form-control" required>
                                        <div class="invalid-feedback">Campo obligatorio.</div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Teléfono</label>
                                        <input type="text" name="telefono" class="form-control" required>
                                        <div class="invalid-feedback">Campo obligatorio.</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Dirección</label>
                                        <input type="text" name="direccion" class="form-control" required>
                                        <div class="invalid-feedback">Campo obligatorio.</div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">DNI / RUC</label>
                                        <input type="text" name="documento" class="form-control" required>
                                        <div class="invalid-feedback">Campo obligatorio.</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Tipo de solicitud</label>
                                        <select name="tipo" class="form-select" required>
                                            <option value="">Selecciona...</option>
                                            <option value="Reclamo">Reclamo</option>
                                            <option value="Queja">Queja</option>
                                        </select>
                                        <div class="invalid-feedback">Selecciona un tipo.</div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Producto o servicio involucrado</label>
                                    <input type="text" name="producto" class="form-control" required>
                                    <div class="invalid-feedback">Campo obligatorio.</div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea name="descripcion" class="form-control" rows="4" required></textarea>
                                    <div class="invalid-feedback">Campo obligatorio.</div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Pedido del consumidor</label>
                                    <textarea name="pedido" class="form-control" rows="3" required></textarea>
                                    <div class="invalid-feedback">Campo obligatorio.</div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-danger btn-lg">Registrar Reclamo</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <jsp:include page="/includes/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (() => {
            'use strict'
            const forms = document.querySelectorAll('form')
            Array.from(forms).forEach(form => {
                form.addEventListener('submit', event => {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>
</body>
</html>
