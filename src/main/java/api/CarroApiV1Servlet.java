package api;

import dao.CarroDAO;
import dto.CarroDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * API REST v1 para carros
 * 
 * Endpoints:
 * GET /api/v1/carros     - Lista todos los carros en JSON
 * GET /api/v1/carros/1   - Obtiene el carro con ID 1
 */

@WebServlet("/api/v1/carros/*")
public class CarroApiV1Servlet extends HttpServlet {

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
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/v1/carros → Listar todos
                List<CarroDTO> carros = dao.listarCarros();
                JSONArray arr = new JSONArray();
                
                for (CarroDTO c : carros) {
                    arr.put(carroToJson(c));
                }
                
                out.print(arr.toString(2));
            } else {
                // GET /api/v1/carros/{id} → Obtener por ID
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                CarroDTO carro = dao.obtenerPorId(id);
                
                if (carro != null) {
                    out.print(carroToJson(carro).toString(2));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JSONObject error = new JSONObject();
                    error.put("success", false);
                    error.put("message", "Carro no encontrado");
                    out.print(error.toString());
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "ID inválido");
            out.print(error.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "Error interno del servidor: " + e.getMessage());
            out.print(error.toString());
        }
    }

    private JSONObject carroToJson(CarroDTO c) {
        JSONObject obj = new JSONObject();
        obj.put("id", c.getId());
        obj.put("nombre", c.getNombre());
        obj.put("descripcion", c.getDescripcion());
        obj.put("imagen", c.getImagen());
        obj.put("precio", c.getPrecio());
        return obj;
    }
}
