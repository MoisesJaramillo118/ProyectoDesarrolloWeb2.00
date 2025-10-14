package controladores;

import dao.CarroDAO;
import dto.CarroDTO;
import dto.UsuarioDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/carro")
@MultipartConfig
public class CarroServlet extends HttpServlet {

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
        HttpSession sesion = request.getSession(false);
        UsuarioDTO usuario = (sesion != null) ? (UsuarioDTO) sesion.getAttribute("usuario") : null;

        if (usuario == null) {
            response.sendRedirect("vistas/login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        try {
            if (accion == null) {
                List<CarroDTO> lista = dao.listarCarros();
                request.setAttribute("listaCarros", lista);
                request.getRequestDispatcher("vistas/carro/listar.jsp").forward(request, response);
            } else if (accion.equals("editar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                CarroDTO carro = dao.obtenerPorId(id);
                request.setAttribute("carroEditar", carro);
                request.getRequestDispatcher("vistas/carro/carroForm.jsp").forward(request, response);
            } else if (accion.equals("nuevo")) {
                request.getRequestDispatcher("vistas/carro/carroForm.jsp").forward(request, response);
            } else if (accion.equals("eliminar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(id);
                response.sendRedirect("carro");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vistas/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            CarroDTO c = new CarroDTO();
            c.setNombre(request.getParameter("nombre"));
            c.setDescripcion(request.getParameter("descripcion"));
            c.setPrecio(Double.parseDouble(request.getParameter("precio")));

            String nombreImagen = null;
            Part archivo = request.getPart("archivoImagen");

            if (archivo != null && archivo.getSize() > 0) {
                nombreImagen = Paths.get(archivo.getSubmittedFileName()).getFileName().toString();
                String rutaImg = getServletContext().getRealPath("/img");
                String rutaDestino = rutaImg + File.separator + nombreImagen;
                archivo.write(rutaDestino);
            } else {
                nombreImagen = request.getParameter("imagenActual"); 
            }

            c.setImagen(nombreImagen);

            if ("insertar".equals(accion)) {
                dao.insertar(c);
            } else if ("actualizar".equals(accion)) {
                c.setId(Integer.parseInt(request.getParameter("id")));
                dao.actualizar(c);
            }

            response.sendRedirect("carro");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("vistas/error.jsp");
        }
    }
}
