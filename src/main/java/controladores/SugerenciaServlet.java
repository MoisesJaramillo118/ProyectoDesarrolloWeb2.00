package controladores;

import dao.SugerenciaDAO;
import dto.SugerenciaDTO;
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

@WebServlet("/sugerencia")
public class SugerenciaServlet extends HttpServlet {

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
        boolean esAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        // Solo admin puede VER la lista
        try {
            if ("listar".equals(accion)) {
                if (!esAdmin(request)) {
                    if (esAjax) {
                        JSONObject json = new JSONObject();
                        json.put("success", false);
                        json.put("message", "Permiso denegado");
                        response.setContentType("application/json");
                        response.getWriter().print(json.toString());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/?error=permission");
                    }
                    return;
                }

                SugerenciaDAO dao = new SugerenciaDAO();
                List<SugerenciaDTO> sugerencias = dao.listarTodos();

                if (esAjax) {
                    JSONArray arr = new JSONArray();
                    for (SugerenciaDTO s : sugerencias) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getId());
                        obj.put("nombre", s.getNombre());
                        obj.put("correo", s.getCorreo());
                        obj.put("asunto", s.getAsunto());
                        obj.put("mensaje", s.getMensaje());
                        obj.put("fecha", s.getFecha().toString());
                        arr.put(obj);
                    }
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(arr.toString());
                } else {
                    request.setAttribute("sugerencias", sugerencias);
                    request.getRequestDispatcher("/vistas/admin/listarSugerencias.jsp").forward(request, response);
                }

            } else if ("eliminar".equals(accion)) {
                if (!esAdmin(request)) {
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    json.put("message", "Permiso denegado");
                    response.setContentType("application/json");
                    response.getWriter().print(json.toString());
                    return;
                }

                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    SugerenciaDAO dao = new SugerenciaDAO();
                    boolean eliminado = dao.eliminar(id);

                    JSONObject json = new JSONObject();
                    json.put("success", eliminado);
                    json.put("message", eliminado ? "Sugerencia eliminada correctamente"
                            : "No se encontró la sugerencia");

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(json.toString());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    json.put("message", "Error al eliminar la sugerencia: " + e.getMessage());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(json.toString());
                }

            } else {
                // Mostrar formulario
                response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (esAjax) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "Error en el servidor");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
            } else {
                response.sendRedirect(request.getContextPath() + "/vistas/error.jsp");
            }
        }
    }

            @Override
            protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                boolean esAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
                JSONObject json = new JSONObject();

                // CUALQUIER usuario puede ENVIAR una sugerencia
                try {
                    SugerenciaDTO sugerencia = new SugerenciaDTO();
                    sugerencia.setNombre(request.getParameter("nombre"));
                    sugerencia.setCorreo(request.getParameter("correo"));
                    sugerencia.setAsunto(request.getParameter("asunto"));
                    sugerencia.setMensaje(request.getParameter("mensaje"));

                    SugerenciaDAO dao = new SugerenciaDAO();
                    dao.insertar(sugerencia);

                    if (esAjax) {
                        json.put("success", true);
                        json.put("message", "Sugerencia enviada correctamente.");
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().print(json.toString());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp?success=1");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (esAjax) {
                        json.put("success", false);
                        json.put("message", "Error al enviar la sugerencia.");
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().print(json.toString());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/vistas/formulario-sugerencias.jsp?error=1");
                    }
                }
            }
        
    
}
