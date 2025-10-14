<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.LibroReclamacionesDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Libro de Reclamaciones - Lista</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

    <jsp:include page="/includes/header.jsp" />

    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="${pageContext.request.contextPath}/vistas/dashboard.jsp" class="btn btn-outline-dark rounded-pill">
                <i class="bi bi-arrow-left-circle me-1"></i> Volver
            </a>
            <h2 class="text-primary fw-bold mb-0">
                <i class="bi bi-journal-text me-2"></i> Lista de Reclamos
            </h2>
            <div></div> <!-- Espacio para mantener alineación -->
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Dirección</th>
                        <th>Documento</th>
                        <th>Tipo</th>
                        <th>Producto</th>
                        <th>Descripción</th>
                        <th>Pedido</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<LibroReclamacionesDTO> reclamos = (List<LibroReclamacionesDTO>) request.getAttribute("reclamos");
                        if (reclamos != null && !reclamos.isEmpty()) {
                            for (LibroReclamacionesDTO l : reclamos) {
                    %>
                        <tr>
                            <td class="text-center"><%= l.getId() %></td>
                            <td><%= l.getNombre() %></td>
                            <td><%= l.getCorreo() %></td>
                            <td><%= l.getTelefono() %></td>
                            <td><%= l.getDireccion() %></td>
                            <td><%= l.getDocumento() %></td>
                            <td><%= l.getTipo() %></td>
                            <td><%= l.getProducto() %></td>
                            <td><%= l.getDescripcion() %></td>
                            <td><%= l.getPedido() %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="10" class="text-center text-muted py-4">
                                <i class="bi bi-info-circle me-2"></i>No hay reclamos registrados.
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>