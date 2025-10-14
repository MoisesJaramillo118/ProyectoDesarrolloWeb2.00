package controladores;

import dao.ContactoDAO;
import dto.ContactoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/contacto")
public class ContactoServlet extends HttpServlet {

    private ContactoDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new ContactoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String asunto = request.getParameter("asunto");
        String mensaje = request.getParameter("mensaje");

        ContactoDTO c = new ContactoDTO();
        c.setNombre(nombre);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        c.setAsunto(asunto);
        c.setMensaje(mensaje);

        boolean exito = dao.insertar(c);

        if (exito) {
            request.setAttribute("mensajeExito", "✅ ¡Tu mensaje fue enviado correctamente!");
        } else {
            request.setAttribute("mensajeError", "❌ Ocurrió un error al enviar tu mensaje.");
        }

        request.getRequestDispatcher("/vistas/contacto.jsp").forward(request, response);
    }
}
