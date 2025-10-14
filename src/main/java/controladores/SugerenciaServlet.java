package controladores;

import dao.SugerenciaDAO;
import dto.SugerenciaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/sugerencia")
public class SugerenciaServlet extends HttpServlet {

    private SugerenciaDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new SugerenciaDAO();
    }
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");

    if ("listar".equals(accion)) {
        List<SugerenciaDTO> lista = dao.listar();
        request.setAttribute("listaSugerencias", lista);
        request.getRequestDispatcher("/vistas/empresa/listar-sugerencias.jsp").forward(request, response);
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer datos del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String asunto = request.getParameter("asunto");
        String mensaje = request.getParameter("mensaje");

        // Crear DTO
        SugerenciaDTO s = new SugerenciaDTO();
        s.setNombre(nombre);
        s.setCorreo(correo);
        s.setAsunto(asunto);
        s.setMensaje(mensaje);

        // Guardar en DB
        boolean exito = dao.insertar(s);

        if (exito) {
            request.setAttribute("mensajeExito", "✅ ¡Gracias por tu sugerencia!");
        } else {
            request.setAttribute("mensajeError", "❌ No se pudo guardar tu sugerencia.");
        }

        request.getRequestDispatcher("/vistas/formulario-sugerencias.jsp").forward(request, response);
    }
}
