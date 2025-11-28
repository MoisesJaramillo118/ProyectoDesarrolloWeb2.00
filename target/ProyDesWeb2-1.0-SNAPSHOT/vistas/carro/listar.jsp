<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Lista de Carros</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    </head>
    <body>

        <jsp:include page="/includes/header.jsp" />

        <div class="container py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark rounded-pill">
                    <i class="bi bi-arrow-left-circle me-1"></i> Volver
                </a>
                <h2 class="fw-bold text-primary">Carros Registrados</h2>
                <a href="${pageContext.request.contextPath}/carro?accion=nuevo" class="btn btn-outline-primary rounded-pill">
                    <i class="bi bi-plus-circle me-1"></i> Agregar nuevo carro
                </a>
            </div>

            <div class="table-responsive">
                <table id="tablaCarros" class="table table-striped table-hover align-middle">
                    <thead class="table-primary text-center">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Imagen</th>
                            <th>Precio</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <!-- jQuery + DataTables JS -->
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

        <!-- Inicialización de DataTable -->
        <script>
            $(document).ready(function () {
                const ctx = '${pageContext.request.contextPath}';

                $('#tablaCarros').DataTable({
                    ajax: {
                        url: ctx + '/carro?accion=listarAjax',
                        dataSrc: ''
                    },
                    columns: [
                        {data: 'id'},
                        {data: 'nombre'},
                        {data: 'descripcion'},
                        {
                            data: 'imagen',
                            render: function (data, type, row) {
                                return '<img src="' + ctx + '/img/' + data + '" alt="' + row.nombre + '" width="80" class="rounded shadow-sm">';
                            }
                        },
                        {
                            data: 'precio',
                            render: function (data) {
                                return '<span class="fw-bold text-success">S/. ' + data + '</span>';
                            }
                        },
                        {
                            data: 'id',
                            render: function (data, type, row) {
                                return '' +
                                        '<a href="' + ctx + '/carro?accion=editar&id=' + data + '" class="btn btn-sm btn-warning rounded-pill me-1">' +
                                        '<i class="bi bi-pencil-square"></i> Editar' +
                                        '</a>' +
                                        '<a href="' + ctx + '/carro?accion=eliminar&id=' + data + '" class="btn btn-sm btn-danger rounded-pill" ' +
                                        'onclick="return confirm(\'¿Estás seguro de eliminar este carro?\');">' +
                                        '<i class="bi bi-trash"></i> Eliminar' +
                                        '</a>';
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
            });
        </script>
    </body>
</html>