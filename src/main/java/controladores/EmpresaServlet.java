package controladores;

import dao.EmpresaDAO;
import dto.EmpresaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/empresa")
public class EmpresaServlet extends HttpServlet {

    private EmpresaDAO dao = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            EmpresaDTO empresa = dao.obtenerEmpresa();
            request.setAttribute("empresaEditar", empresa);
            request.getRequestDispatcher("/vistas/empresa/empresaForm.jsp").forward(request, response);
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

        try {
            String accion = request.getParameter("accion");
            EmpresaDTO emp = new EmpresaDTO();

            if ("actualizar".equals(accion)) {
                // Lógica para actualizar empresa existente
                emp.setId(Integer.parseInt(request.getParameter("id")));
                emp.setRuc(request.getParameter("ruc"));
                emp.setRazonSocial(request.getParameter("razonSocial"));
                emp.setNombreComercial(request.getParameter("nombreComercial"));
                emp.setDireccion(request.getParameter("direccion"));
                emp.setTelefono(request.getParameter("telefono"));
                emp.setCorreo(request.getParameter("correo"));
            }

            boolean ok = false;
            if ("insertar".equals(accion)) {
                ok = dao.insertar(emp);
                json.put("success", ok);
                json.put("message", ok ? "Empresa registrada correctamente." : "No se pudo registrar la empresa.");
            } else if ("actualizar".equals(accion)) {
                ok = dao.actualizar(emp);
                json.put("success", ok);
                json.put("message", ok ? "Empresa actualizada correctamente." : "No se pudo actualizar la empresa.");
            } else {
                json.put("success", false);
                json.put("message", "Acción inválida.");
            }

            if (esAjax) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
                return; // 👈 importante
            } else {
                if (ok) {
                    response.sendRedirect("empresa?success=1");
                } else {
                    response.sendRedirect("empresa?error=1");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            if (esAjax) {
                json.put("success", false);
                json.put("message", "Error al procesar la empresa.");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
            } else {
                response.sendRedirect("vistas/error.jsp");
            }
        }
    }
}
