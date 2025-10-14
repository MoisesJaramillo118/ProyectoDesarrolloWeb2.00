package controladores;

import dao.EmpresaDAO;
import dto.EmpresaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        try {
String accion = request.getParameter("accion");

if ("insertar".equals(accion)) {
    // Lógica para insertar nueva empresa
    EmpresaDTO emp = new EmpresaDTO();
    emp.setRuc(request.getParameter("ruc"));
    emp.setRazonSocial(request.getParameter("razonSocial"));
    emp.setNombreComercial(request.getParameter("nombreComercial"));
    emp.setDireccion(request.getParameter("direccion"));
    emp.setTelefono(request.getParameter("telefono"));
    emp.setCorreo(request.getParameter("correo"));


    dao.insertar(emp);
    response.sendRedirect("empresa"); // luego lista o muestra

} else if ("actualizar".equals(accion)) {
    // Lógica para actualizar empresa existente
    EmpresaDTO emp = new EmpresaDTO();
    emp.setId(Integer.parseInt(request.getParameter("id")));
    emp.setRuc(request.getParameter("ruc"));
    emp.setRazonSocial(request.getParameter("razonSocial"));
    emp.setNombreComercial(request.getParameter("nombreComercial"));
    emp.setDireccion(request.getParameter("direccion"));
    emp.setTelefono(request.getParameter("telefono"));
    emp.setCorreo(request.getParameter("correo"));


    dao.actualizar(emp);
    response.sendRedirect("empresa");
}

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("vistas/error.jsp");
        }
    }
}
