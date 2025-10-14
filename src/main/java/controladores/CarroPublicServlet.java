package controladores;

import dao.CarroDAO;
import dto.CarroDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/verCarros")
public class CarroPublicServlet extends HttpServlet {

    private CarroDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new CarroDAO();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CarroDTO> lista = dao.listarCarros();
            request.setAttribute("carros", lista);
            // Redirige a index.jsp (donde está tu carrusel)
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vistas/error.jsp");
        }
    }
}
