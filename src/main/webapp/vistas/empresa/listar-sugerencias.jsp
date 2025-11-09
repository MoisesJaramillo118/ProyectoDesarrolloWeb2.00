<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Sugerencias</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

<div class="container mt-4">
    <h2 class="text-center mb-4">Lista de Sugerencias</h2>

    <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-dark mb-3">⬅ Volver</a>

    <div class="table-responsive">
        <table id="tablaSugerencias" class="table table-bordered table-hover">
            <thead class="table-dark text-center">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Asunto</th>
                    <th>Mensaje</th>
                    <th>Fecha</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${listaSugerencias}">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.nombre}</td>
                    <td>${s.correo}</td>
                    <td>${s.asunto}</td>
                    <td>${s.mensaje}</td>
                    <td>${s.fecha}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery + DataTables JS -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<!-- Inicialización de DataTable -->
<script>
    $(document).ready(function() {
        $('#tablaSugerencias').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
            },
            pageLength: 5,
            lengthMenu: [5, 10, 20, 50],
            responsive: true
        });
    });
</script>
</body>
</html>