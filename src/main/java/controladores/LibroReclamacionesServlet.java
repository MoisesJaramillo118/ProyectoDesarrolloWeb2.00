package controladores;

import dao.LibroReclamacionesDAO;
import dto.LibroReclamacionesDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/libro")
public class LibroReclamacionesServlet extends HttpServlet {

    private LibroReclamacionesDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new LibroReclamacionesDAO();
    }
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LibroReclamacionesDTO> reclamos = dao.listar();
        request.setAttribute("reclamos", reclamos);
        request.getRequestDispatcher("/vistas/empresa/listar-reclamos.jsp").forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LibroReclamacionesDTO l = new LibroReclamacionesDTO();
        l.setNombre(request.getParameter("nombre"));
        l.setCorreo(request.getParameter("correo"));
        l.setTelefono(request.getParameter("telefono"));
        l.setDireccion(request.getParameter("direccion"));
        l.setDocumento(request.getParameter("documento"));
        l.setTipo(request.getParameter("tipo"));
        l.setProducto(request.getParameter("producto"));
        l.setDescripcion(request.getParameter("descripcion"));
        l.setPedido(request.getParameter("pedido"));

        boolean exito = dao.insertar(l);

        if (exito) {
            request.setAttribute("mensajeExito", "✅ Reclamo registrado correctamente.");
        } else {
            request.setAttribute("mensajeError", "❌ No se pudo registrar el reclamo.");
        }

        request.getRequestDispatcher("/vistas/libroReclamaciones.jsp").forward(request, response);
    }
    
    
}
