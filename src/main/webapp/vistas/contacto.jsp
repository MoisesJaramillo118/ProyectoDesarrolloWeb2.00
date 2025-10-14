<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contáctenos</title>
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
                <div class="col-lg-6 col-md-8">
                    <div class="card shadow">
                        <div class="card-body p-4">
                            <h3 class="card-title text-center mb-4 fw-bold text-primary">📞 Contáctenos</h3>
                            <p class="text-muted text-center mb-4">
                                Si tienes alguna consulta o comentario, completa este formulario y te responderemos a la brevedad.
                            </p>

                            <!-- Mensajes -->
                            <c:if test="${not empty mensajeExito}">
                                <div class="alert alert-success text-center">${mensajeExito}</div>
                            </c:if>
                            <c:if test="${not empty mensajeError}">
                                <div class="alert alert-danger text-center">${mensajeError}</div>
                            </c:if>

                            <!-- Formulario -->
                            <form action="${pageContext.request.contextPath}/contacto" method="post" novalidate>
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre completo</label>
                                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                                    <div class="invalid-feedback">Por favor ingresa tu nombre.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="correo" class="form-label">Correo electrónico</label>
                                    <input type="email" class="form-control" id="correo" name="correo" required>
                                    <div class="invalid-feedback">Por favor ingresa un correo válido.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono (opcional)</label>
                                    <input type="text" class="form-control" id="telefono" name="telefono">
                                </div>

                                <div class="mb-3">
                                    <label for="asunto" class="form-label">Asunto</label>
                                    <input type="text" class="form-control" id="asunto" name="asunto" required>
                                    <div class="invalid-feedback">Por favor ingresa un asunto.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="mensaje" class="form-label">Mensaje</label>
                                    <textarea class="form-control" id="mensaje" name="mensaje" rows="4" required></textarea>
                                    <div class="invalid-feedback">Por favor escribe tu mensaje.</div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary btn-lg">Enviar mensaje</button>
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
