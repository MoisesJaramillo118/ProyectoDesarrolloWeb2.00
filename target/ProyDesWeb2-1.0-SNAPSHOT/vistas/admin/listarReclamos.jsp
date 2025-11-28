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
        <title>Libro de Reclamaciones - Admin</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    </head>
    <body>
        <jsp:include page="../../includes/header.jsp" />

        <div class="container py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="bi bi-exclamation-triangle"></i> Libro de Reclamaciones</h2>
                <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Volver al Dashboard
                </a>
            </div>

            <div class="table-responsive">
                <table id="tablaReclamos" class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tipo</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Producto</th>
                            <th>Descripción</th>
                            <th>Fecha</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            $(document).ready(function () {
                const ctx = '${pageContext.request.contextPath}';

                // Inicializar DataTable con AJAX
                const tabla = $('#tablaReclamos').DataTable({
                    ajax: {
                        url: ctx + '/libro?accion=listar',
                        dataSrc: ''
                    },
                    columns: [
                        {data: 'id'},
                        {
                            data: 'tipo',
                            render: function (data) {
                                return '<span class="badge ' + (data === 'Reclamo' ? 'bg-danger' : 'bg-warning') + '">' + data + '</span>';
                            }
                        },
                        {data: 'nombre'},
                        {data: 'correo'},
                        {data: 'producto'},
                        {data: 'descripcion'},
                        {data: 'fecha'},
                        {
                            data: null,
                            render: function (data, type, row) {
                                return '<button class="btn btn-sm btn-danger eliminar" data-id="' + row.id + '">' +
                                        '<i class="bi bi-trash"></i>' +
                                        '</button>';
                            }
                        }
                    ]
                });

                // Acción eliminar vía AJAX
                $('#tablaReclamos').on('click', '.eliminar', function () {
                    const id = $(this).data('id');
                    if (!id) {
                        alert('ID no válido o no encontrado.');
                        return;
                    }
                    if (confirm('¿Eliminar este reclamo?')) {
                        $.ajax({
                            url: ctx + '/libro?accion=eliminar&id=' + id,
                            method: 'GET',
                            dataType: 'json',
                            success: function (resp) {
                                alert(resp.message);
                                if (resp.success) {
                                    tabla.ajax.reload();
                                }
                            },
                            error: function () {
                                alert('Error al eliminar el reclamo.');
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>