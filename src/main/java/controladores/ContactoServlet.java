package controladores;

import dao.ContactoDAO;
import dto.ContactoDTO;
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

@WebServlet("/contacto")
public class ContactoServlet extends HttpServlet {

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

        try {
            ContactoDAO dao = new ContactoDAO();

            if ("listar".equals(accion)) {
                if (!esAdmin(request)) {
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    json.put("message", "Permiso denegado");
                    response.setContentType("application/json");
                    response.getWriter().print(json.toString());
                    return;
                }

                List<ContactoDTO> contactos = dao.listarTodos();
                JSONArray arr = new JSONArray();
                for (ContactoDTO c : contactos) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getId());
                    obj.put("nombre", c.getNombre());
                    obj.put("correo", c.getCorreo());
                    obj.put("telefono", c.getTelefono());
                    obj.put("asunto", c.getAsunto());
                    obj.put("mensaje", c.getMensaje());
                    arr.put(obj);
                }
                response.setContentType("application/json");
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
                        boolean eliminado = dao.eliminar(id); // tu DAO debe devolver true/false

                        if (eliminado) {
                            json.put("success", true);
                            json.put("message", "Contacto eliminado correctamente.");
                        } else {
                            json.put("success", false);
                            json.put("message", "No se encontró el contacto o no se pudo eliminar.");
                        }
                    }
                } catch (NumberFormatException nfe) {
                    json.put("success", false);
                    json.put("message", "ID inválido.");
                } catch (Exception e) {
                    e.printStackTrace();
                    json.put("success", false);
                    json.put("message", "Error al eliminar contacto (BD).");
                }
                response.setContentType("application/json");
                response.getWriter().print(json.toString());
                return;
            }
            request.getRequestDispatcher("/vistas/admin/listarContactos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("message", "Error en el servidor");
            response.setContentType("application/json");
            response.getWriter().print(json.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();

        try {
            ContactoDTO contacto = new ContactoDTO();
            contacto.setNombre(request.getParameter("nombre"));
            contacto.setCorreo(request.getParameter("correo"));
            contacto.setTelefono(request.getParameter("telefono"));
            contacto.setAsunto(request.getParameter("asunto"));
            contacto.setMensaje(request.getParameter("mensaje"));

            ContactoDAO dao = new ContactoDAO();
            dao.insertar(contacto);

            json.put("success", true);
            json.put("message", "Mensaje enviado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("success", false);
            json.put("message", "Error al enviar mensaje.");
        }

        response.getWriter().print(json.toString());
    }
}
