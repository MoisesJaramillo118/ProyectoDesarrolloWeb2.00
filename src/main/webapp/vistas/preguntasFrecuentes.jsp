<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/includes/header.jsp" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Preguntas Frecuentes | GLOBAL CARS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <style>
        .faq-section {
            max-width: 900px;
            margin: 50px auto;
        }
        .faq-title {
            text-align: center;
            margin-bottom: 40px;
            font-weight: bold;
        }
        .accordion-button {
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="faq-section">
        <h1 class="faq-title">Preguntas Frecuentes</h1>

        <div class="accordion" id="faqAccordion">
            <!-- Pregunta 1 -->
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingOne">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        ¿Cómo puedo cotizar un auto?
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#faqAccordion">
                    <div class="accordion-body">
                        Para cotizar un auto, solo necesitas iniciar sesión, seleccionar el modelo que te interesa y hacer clic en <strong>“Cotizar”</strong>. Nuestro equipo se pondrá en contacto contigo para brindarte más detalles.
                    </div>
                </div>
            </div>

            <!-- Pregunta 2 -->
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingTwo">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        ¿Qué documentos necesito para comprar un auto?
                    </button>
                </h2>
                <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#faqAccordion">
                    <div class="accordion-body">
                        Necesitas tu DNI vigente, comprobante de ingresos y un recibo de servicio. En caso de financiamiento, podrían requerirse documentos adicionales.
                    </div>
                </div>
            </div>

            <!-- Pregunta 3 -->
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingThree">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        ¿Puedo financiar mi auto?
                    </button>
                </h2>
                <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#faqAccordion">
                    <div class="accordion-body">
                        Sí, contamos con convenios con distintas entidades financieras. Podrás elegir la opción que más se acomode a tus necesidades.
                    </div>
                </div>
            </div>

            <!-- Pregunta 4 -->
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingFour">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                        ¿Puedo probar el auto antes de comprarlo?
                    </button>
                </h2>
                <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#faqAccordion">
                    <div class="accordion-body">
                        ¡Por supuesto! Puedes agendar una prueba de manejo en cualquiera de nuestras sedes. Solo necesitas presentar tu DNI.
                    </div>
                </div>
            </div>

            <!-- Pregunta 5 -->
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingFive">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                        ¿Qué métodos de pago aceptan?
                    </button>
                </h2>
                <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#faqAccordion">
                    <div class="accordion-body">
                        Aceptamos pagos en efectivo, transferencias bancarias, tarjetas de crédito y débito. También puedes financiar con nuestras entidades aliadas.
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
