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
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
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

            <div class="table-responsive">
                <table id="tablaSugerencias" class="table table-hover">
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
                    <tbody></tbody>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

        <script>
            $(document).ready(function () {
                const ctx = '${pageContext.request.contextPath}';
                const tabla = $('#tablaSugerencias').DataTable({
                    ajax: {
                        url: ctx + '/sugerencia?accion=listar',
                        dataSrc: ''
                    },
                    columns: [
                        {data: 'id'}, 
                        {data: 'nombre'}, 
                        {data: 'correo'}, 
                        {data: 'asunto'}, 
                        {data: 'mensaje'},
                        {data: 'fecha'}, 
                        {
                            data: null,
                            render: function (data, type, row) {
                                return '<button class="btn btn-sm btn-danger eliminar" data-id="' + row.id + '">' +
                                        '<i class="bi bi-trash"></i>' +
                                        '</button>';
                            }
                        }
                    ],
                    language: {
                        url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
                    },
                    pageLength: 5,
                    lengthMenu: [5, 10, 20, 50],
                    responsive: true
                });

                $('#tablaSugerencias').on('click', '.eliminar', function () {
                    const id = $(this).data('id');

                    if (!id || isNaN(id)) {
                        alert('ID inválido. No se puede eliminar.');
                        return;
                    }

                    if (confirm('¿Seguro que deseas eliminar esta sugerencia?')) {
                        $.ajax({
                            url: ctx + '/sugerencia?accion=eliminar&id=' + id,
                            method: 'GET',
                            dataType: 'json',
                            success: function (resp) {
                                alert(resp.message);
                                tabla.ajax.reload();
                            },
                            error: function () {
                                alert('Error al eliminar la sugerencia.');
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>