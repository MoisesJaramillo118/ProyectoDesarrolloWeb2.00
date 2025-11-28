<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Verificar que sea admin
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
        <title>Mensajes de Contacto - Admin</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css"/>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="../../includes/header.jsp" />

        <div class="container py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="bi bi-envelope"></i> Mensajes de Contacto</h2>
                <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Volver al Dashboard
                </a>
            </div>

            <div id="mensajeAjax"></div>

            <div class="table-responsive">
                <table id="tablaContactos" class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Teléfono</th>
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

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootst
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script>
$(document).ready(function() {
    const ctx = '${pageContext.request.contextPath}';

    const tabla = $('#tablaContactos').DataTable({
        ajax: {
            url: ctx + '/contacto?accion=listar',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'nombre' },
            { data: 'correo' },
            { data: 'telefono' },
            { data: 'asunto' },
            { data: 'mensaje' },
            {
                data: null,
                render: function(row) {
                    return '<button class="btn btn-sm btn-danger eliminar"data-id="' + row.id + '">' +
                                    '<i class="bi bi-trash"></i>' +
                                    '</button>';
                            }
            }
        ]
    });

    // Función para mostrar mensajes Bootstrap
    function mostrarMensaje(tipo, texto) {
        const alerta = `<div class="alert alert-${tipo} alert-dismissible fade show mt-3" role="alert">
                            ${texto}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                        </div>`;
        $('#mensajeAjax').html(alerta);
    }

    // Manejar eliminación vía AJAX
    $('#tablaContactos').on('click', '.eliminar', function() {
        const id = $(this).data('id'); 
        console.log("ID capturado:", id); // 👈 revisa en consola del navegador

        if (!id) {
            mostrarMensaje('danger', 'ID no válido o no encontrado.');
            return;
        }

        if (confirm('¿Eliminar este mensaje?')) {
            $.ajax({
                url: ctx + '/contacto?accion=eliminar&id=' + id,
                method: 'GET',
                dataType: 'json',
                success: function(resp) {
                    mostrarMensaje(resp.success ? 'success' : 'danger', resp.message);
                    if (resp.success) {
                        tabla.ajax.reload();
                    }
                },
                error: function() {
                    mostrarMensaje('danger', 'Error al eliminar mensaje.');
                }
            });
        }
    });
});
</script>

    </body>
</html>