<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Formulario de Sugerencias</title>
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
                                <h3 class="card-title text-center mb-4 fw-bold text-primary">? Envíanos tu sugerencia</h3>
                                <p class="text-muted text-center mb-4">
                                    Queremos mejorar nuestros servicios. Tu opinión es muy importante para nosotros.
                                </p>
                     
                                <!-- Formulario -->
                                <form id="formSugerencia" novalidate>
                                    <!-- Nombre -->
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre completo</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Juan Pérez" required>
                                        <div class="invalid-feedback">
                                            Por favor ingresa tu nombre.
                                        </div>
                                    </div>

                                    <!-- Correo -->
                                    <div class="mb-3">
                                        <label for="correo" class="form-label">Correo electrónico</label>
                                        <input type="email" class="form-control" id="correo" name="correo" placeholder="correo@ejemplo.com" required>
                                        <div class="invalid-feedback">
                                            Ingresa un correo válido.
                                        </div>
                                    </div>

                                    <!-- Asunto -->
                                    <div class="mb-3">
                                        <label for="asunto" class="form-label">Asunto</label>
                                        <input type="text" class="form-control" id="asunto" name="asunto" placeholder="Ej: Mejorar la atención" required>
                                        <div class="invalid-feedback">
                                            Ingresa un asunto.
                                        </div>
                                    </div>

                                    <!-- Mensaje -->
                                    <div class="mb-3">
                                        <label for="mensaje" class="form-label">Mensaje</label>
                                        <textarea class="form-control" id="mensaje" name="mensaje" rows="4" placeholder="Escribe aquí tu sugerencia..." required></textarea>
                                        <div class="invalid-feedback">
                                            Escribe tu sugerencia.
                                        </div>
                                    </div>

                                    <!-- Botón -->
                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary btn-lg">Enviar sugerencia</button>
                                    </div>
                                    <div class="d-grid mt-3">
                                    <button type="button" class="btn btn-success btn-lg" id="btnFetch">
                                    <!--Enviar sugerencia con Fetch -->
                                    </button>
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
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

        <script>
            $(document).ready(function () {
                const ctx = '${pageContext.request.contextPath}';

                $('form').on('submit', function (event) {
                    event.preventDefault(); 
                    if (!this.checkValidity()) {
                        event.stopPropagation();
                        this.classList.add('was-validated');
                        return;
                    }

                    // Capturar datos del formulario
                    const datos = {
                        nombre: $('#nombre').val(),
                        correo: $('#correo').val(),
                        asunto: $('#asunto').val(),
                        mensaje: $('#mensaje').val()
                    };

                    // Enviar vía AJAX
                    $.ajax({
                        url: ctx + '/sugerencia',
                        method: 'POST',
                        data: datos,
                        dataType: 'json',
                        success: function (resp) {
                            if (resp.success) {
                                // Mostrar mensaje bonito
                                $('.card-body').prepend(
                                        '<div class="alert alert-success text-center">' + resp.message + '</div>'
                                        );
                                $('form')[0].reset();
                                $('form').removeClass('was-validated');
                            } else {
                                $('.card-body').prepend(
                                        '<div class="alert alert-danger text-center">' + resp.message + '</div>'
                                        );
                            }
                        },
                        error: function () {
                            $('.card-body').prepend(
                                    '<div class="alert alert-danger text-center">Error al enviar la sugerencia.</div>'
                                    );
                        }
                    });
                });
            });
        </script>
        <!-- Script: envío con Fetch API -->
         <script>
        document.getElementById("btnFetch").addEventListener("click", function () {
        const form = document.getElementById("formSugerencia");

    // Validación nativa de bootstrap/jsp
    if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
    }

    const datos = {
        nombre: document.getElementById("nombre").value,
        correo: document.getElementById("correo").value,
        asunto: document.getElementById("asunto").value,
        mensaje: document.getElementById("mensaje").value
    };

    fetch('${pageContext.request.contextPath}/sugerencia', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
    .then(res => res.json())
    .then(resp => {
        const container = document.querySelector('.card-body');
        if (resp.success) {
            container.insertAdjacentHTML(
                'afterbegin',
                `<div class="alert alert-success text-center">${resp.message}</div>`
            );
            form.reset();
            form.classList.remove("was-validated");
        } else {
            container.insertAdjacentHTML(
                'afterbegin',
                `<div class="alert alert-danger text-center">${resp.message}</div>`
            );
        }
    })
    .catch(err => {
        document.querySelector('.card-body').insertAdjacentHTML(
            'afterbegin',
            `<div class="alert alert-danger text-center">Error en la petición Fetch.</div>`
        );
        console.error(err);
    });
});
    </script>
    </body>
</html>
