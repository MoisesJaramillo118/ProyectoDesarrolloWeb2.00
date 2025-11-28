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
import org.json.JSONArray;
import org.json.JSONObject;

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
            
            } else if ("listarAjax".equals(accion)) {
                List<CarroDTO> lista = dao.listarCarros();
                JSONArray arr = new JSONArray();
                for (CarroDTO c : lista) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getId());
                    obj.put("nombre", c.getNombre());
                    obj.put("descripcion", c.getDescripcion());
                    obj.put("imagen", c.getImagen());
                    obj.put("precio", c.getPrecio());
                    arr.put(obj);
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(arr.toString());
            
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                CarroDTO carro = dao.obtenerPorId(id);
                request.setAttribute("carroEditar", carro);
                request.getRequestDispatcher("vistas/carro/carroForm.jsp").forward(request, response);
            } else if ("nuevo".equals(accion)) {
                request.getRequestDispatcher("vistas/carro/carroForm.jsp").forward(request, response);
            } else if ("eliminar".equals(accion)) {
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

        boolean esAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        JSONObject json = new JSONObject();

        String accion = request.getParameter("accion");
        boolean ok = false;

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
                ok = true; 
                json.put("success", ok);
                json.put("message", "Carro registrado correctamente.");
            } else if ("actualizar".equals(accion)) {
                c.setId(Integer.parseInt(request.getParameter("id")));
                dao.actualizar(c);
                ok = true;
                json.put("success", ok);
                json.put("message", "Carro actualizado correctamente.");
            } else {
                json.put("success", false);
                json.put("message", "Acción inválida.");
            }

            if (esAjax) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
                return;
            } else {
                if (ok) {
                    response.sendRedirect("carro?success=1");
                } else {
                    response.sendRedirect("carro?error=1");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (esAjax) {
                json.put("success", false);
                json.put("message", "Error al procesar el carro.");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
            } else {
                response.sendRedirect("vistas/error.jsp");
            }
        }
    }
}
