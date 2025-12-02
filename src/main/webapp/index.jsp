<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>GLOBAL CARS - Financiamiento Inteligente</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    </head>
    <body class="d-flex flex-column min-vh-100">

        <!-- Header -->
        <jsp:include page="/includes/header.jsp" />

        <!-- Hero principal -->
        <section class="bg-primary text-white text-center py-5">
            <div class="container">
                <h1 class="display-4 fw-bold">Tu carro ideal, con el plan perfecto</h1>
                <p class="lead">Financia tu vehículo con cuotas accesibles, asesoría personalizada y aprobación rápida.</p>
                <a href="${pageContext.request.contextPath}/vistas/financiamiento.jsp" class="btn btn-light btn-lg mt-3">
                    Solicita tu financiamiento
                </a>
            </div>
        </section>

        <!-- Beneficios -->
        <section class="py-5 bg-light text-center">
            <div class="container">
                <h2 class="mb-4">¿Por qué elegir GLOBAL CARS?</h2>
                <div class="row g-4">
                    <div class="col-md-4">
                        <i class="bi bi-cash-coin fs-1 text-success"></i>
                        <h5 class="mt-3">Cuotas Flexibles</h5>
                        <p>Planes adaptados a tu presupuesto, sin sorpresas ocultas.</p>
                    </div>
                    <div class="col-md-4">
                        <i class="bi bi-person-check fs-1 text-success"></i>
                        <h5 class="mt-3">Aprobación Rápida</h5>
                        <p>Evaluación en minutos y asesoría personalizada para cada cliente.</p>
                    </div>
                    <div class="col-md-4">
                        <i class="bi bi-car-front-fill fs-1 text-success"></i>
                        <h5 class="mt-3">Variedad de Modelos</h5>
                        <p>Elige entre sedanes, SUV, pickups y más, todos disponibles para financiamiento.</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- Carrusel modular -->
        <section class="py-5 bg-white">
            <div class="container">
                <jsp:include page="/includes/carruselCarros.jsp" />
            </div>
        </section>

        <!-- Footer -->
        <jsp:include page="/includes/footer.jsp" />

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                        function calcularCuota() {
                                            const precio = parseFloat(document.getElementById('precio').value);
                                            const cuotas = parseInt(document.getElementById('cuotas').value);
                                            const resultado = document.getElementById('resultado');

                                            if (!isNaN(precio) && !isNaN(cuotas) && cuotas > 0) {
                                                const cuota = (precio / cuotas).toFixed(2);
                                                resultado.innerHTML = `
                        <div class="alert alert-info fw-semibold mt-3">
                            Tu cuota mensual aproximada sería: <span class="text-success">S/ ${cuota}</span>
                        </div>`;
                                            } else {
                                                resultado.innerHTML = `
                        <div class="alert alert-danger mt-3">
                            Por favor ingresa valores válidos para simular.
                        </div>`;
                                            }
                                        }
        </script>
    </body>
</html>