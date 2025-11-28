package controladores;

import dao.LibroReclamacionesDAO;
import dto.LibroReclamacionesDTO;
import dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/libro")
public class LibroReclamacionesServlet extends HttpServlet {

    private boolean esAdmin(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return false;
        }

        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        return usuario != null && "Admin".equals(usuario.getRol());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("/vistas/admin/listarReclamos.jsp").forward(request, response);
            return;
        };
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Solo admin puede VER la lista de reclamos
        if (!esAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/?error=permission");
            return;
        }

        try {
            LibroReclamacionesDAO dao = new LibroReclamacionesDAO();

            if ("listar".equals(accion)) {
                if (!esAdmin(request)) {
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    json.put("message", "Permiso denegado");
                    response.getWriter().print(json.toString());
                    return;
                }

                List<LibroReclamacionesDTO> reclamos = dao.listarTodos();
                JSONArray arr = new JSONArray();
                for (LibroReclamacionesDTO r : reclamos) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", r.getId());
                    obj.put("tipo", r.getTipo());
                    obj.put("nombre", r.getNombre());
                    obj.put("correo", r.getCorreo());
                    obj.put("producto", r.getProducto());
                    obj.put("descripcion", r.getDescripcion());
                    obj.put("fecha", r.getFecha().toString());
                    arr.put(obj);
                }
                response.getWriter().print(arr.toString());
                return;
            }

            if ("eliminar".equals(accion)) {
                JSONObject json = new JSONObject();
                try {
                    String idParam = request.getParameter("id");
                    if (idParam == null || idParam.isEmpty()) {
                        json.put("success", false);
                        json.put("message", "ID no proporcionado");
                    } else {
                        int id = Integer.parseInt(idParam);
                        boolean eliminado = dao.eliminar(id); // el DAO puede lanzar Exception

                        if (eliminado) {
                            json.put("success", true);
                            json.put("message", "Reclamo eliminado correctamente");
                        } else {
                            json.put("success", false);
                            json.put("message", "No se encontró el reclamo o no se pudo eliminar");
                        }
                    }
                } catch (NumberFormatException nfe) {
                    json.put("success", false);
                    json.put("message", "ID inválido");
                } catch (Exception e) {
                    e.printStackTrace();
                    json.put("success", false);
                    json.put("message", "Error al eliminar reclamo (BD)");
                }
                response.getWriter().print(json.toString());
                return;
            }

            // Si no hay acción, devolver error
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("message", "Acción no válida");
            response.getWriter().print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("message", "Error en el servidor");
            response.getWriter().print(json.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();

        // CUALQUIER usuario puede ENVIAR un reclamo
        try {
            LibroReclamacionesDTO reclamo = new LibroReclamacionesDTO();
            reclamo.setNombre(request.getParameter("nombre"));
            reclamo.setCorreo(request.getParameter("correo"));
            reclamo.setTelefono(request.getParameter("telefono"));
            reclamo.setDireccion(request.getParameter("direccion"));
            reclamo.setDocumento(request.getParameter("documento"));
            reclamo.setTipo(request.getParameter("tipo"));
            reclamo.setProducto(request.getParameter("producto"));
            reclamo.setDescripcion(request.getParameter("descripcion"));
            reclamo.setPedido(request.getParameter("pedido"));

            LibroReclamacionesDAO dao = new LibroReclamacionesDAO();
            dao.insertar(reclamo);

            json.put("success", true);
            json.put("message", "Reclamo enviado correctamente.");
            response.getWriter().print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            json.put("success", false);
            json.put("message", "Error al enviar reclamo.");
            response.getWriter().print(json.toString());
        }
    }
}
