<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="bg-dark text-light pt-4 mt-auto">
    <div class="container">
        <div class="row">

            <!-- Información de la empresa -->
            <div class="col-md-3 mb-4">
                <h5>Sistema Web</h5>
                <p>Plataforma integral para gestión de procesos empresariales con tecnología moderna y segura.</p>
                <div>
                    <a href="https://www.facebook.com" target="_blank" class="text-light me-2" title="Facebook">📘</a>
                    <a href="https://www.twitter.com" target="_blank" class="text-light me-2" title="Twitter">🐦</a>
                    <a href="https://www.linkedin.com" target="_blank" class="text-light me-2" title="LinkedIn">💼</a>
                    <a href="mailto:info@empresa.com" class="text-light" title="Email">📧</a>
                </div>
            </div>

            <!-- Enlaces rápidos -->
            <div class="col-md-3 mb-4">
                <h5>Enlaces Rápidos</h5>
                <ul class="list-unstyled">
                    <li><a href="${pageContext.request.contextPath}/" class="text-light">Inicio</a></li>

                    <li><a href="${pageContext.request.contextPath}/vistas/preguntasFrecuentes.jsp" class="text-light">Preguntas Frecuentes</a></li>
                    <li><a href="${pageContext.request.contextPath}/vistas/contacto.jsp" class="text-light">Contáctenos</a></li>
                    <li><a href="${pageContext.request.contextPath}/vistas/formulario-sugerencias.jsp" class="text-light">Enviar Sugerencias</a></li>
                    <li><a href="${pageContext.request.contextPath}/vistas/libroReclamaciones.jsp" class="text-light">Libro de Reclamaciones</a></li>
                </ul>
            </div>

            <!-- Información de contacto -->
            <div class="col-md-3 mb-4">
                <h5>Contacto</h5>
                <p><strong>Dirección:</strong><br>Av. Universitaria 1801<br>San Miguel, Lima - Perú</p>
                <p><strong>Teléfono:</strong><br>(01) 626-2000</p>
                <p><strong>Email:</strong><br><a href="mailto:info@empresa.com" class="text-light">info@empresa.com</a></p>
            </div>

            <!-- Horario de atención -->
            <div class="col-md-3 mb-4">
                <h5>Horario de Atención</h5>
                <p><strong>Lunes a Viernes:</strong><br>8:00 AM - 6:00 PM</p>
                <p><strong>Sábados:</strong><br>9:00 AM - 1:00 PM</p>
                <p><strong>Domingos:</strong><br>Cerrado</p>
            </div>
        </div>

        <hr class="bg-light">

        <!-- Legal y derechos -->
        <div class="row">
            <div class="col-md-6 text-center text-md-start">
                <p>&copy; <%= java.time.Year.now().getValue() %> Sistema Web. Todos los derechos reservados.</p>
                <p>Desarrollado con tecnología Java - JSP | Versión 1.0</p>
            </div>
            <div class="col-md-6 text-center text-md-end">
                <a href="#" class="text-light me-2" onclick="alert('Política de Privacidad - Próximamente')">Política de Privacidad</a>
                <span class="text-light">|</span>
                <a href="#" class="text-light mx-2" onclick="alert('Términos y Condiciones - Próximamente')">Términos y Condiciones</a>
                <span class="text-light">|</span>
                <a href="${pageContext.request.contextPath}/vistas/contactenos.jsp" class="text-light ms-2">Soporte</a>
            </div>
        </div>
    </div>
</footer>
