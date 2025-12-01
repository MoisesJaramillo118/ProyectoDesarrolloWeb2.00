package api;

import dao.CarroDAO;
import dto.CarroDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * API REST v1 para carros
 * 
 * Endpoints:
 * GET    /api/v1/carros       - Lista todos los carros
 * GET    /api/v1/carros/{id}  - Obtiene un carro por ID
 * POST   /api/v1/carros       - Crea un nuevo carro
 * PUT    /api/v1/carros/{id}  - Actualiza un carro por ID
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

    // GET
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
                List<CarroDTO> carros = dao.listarCarros();
                JSONArray arr = new JSONArray();
                for (CarroDTO c : carros) {
                    arr.put(carroToJson(c));
                }
                out.print(arr.toString(2));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                CarroDTO carro = dao.obtenerPorId(id);
                if (carro != null) {
                    out.print(carroToJson(carro).toString(2));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(errorJson("Carro no encontrado"));
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Error: " + e.getMessage()));
        }
    }

    // POST → crear carro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            JSONObject json = readJson(request);
            CarroDTO carro = new CarroDTO();
            carro.setNombre(json.getString("nombre"));
            carro.setDescripcion(json.getString("descripcion"));
            carro.setImagen(json.getString("imagen"));
            carro.setPrecio(json.getDouble("precio"));

            dao.insertar(carro);
            out.print(new JSONObject().put("success", true).put("message", "Carro creado"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Error al crear: " + e.getMessage()));
        }
    }

    // PUT → actualizar carro
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            JSONObject json = readJson(request);

            CarroDTO carro = new CarroDTO();
            carro.setId(id);
            carro.setNombre(json.getString("nombre"));
            carro.setDescripcion(json.optString("descripcion", ""));
            carro.setImagen(json.optString("imagen", ""));
            carro.setPrecio(json.getDouble("precio"));

            dao.actualizar(carro);
            out.print(new JSONObject().put("success", true).put("message", "Carro actualizado"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("Error al actualizar: " + e.getMessage()));
        }
    }

    // Helpers
    private JSONObject carroToJson(CarroDTO c) {
        JSONObject obj = new JSONObject();
        obj.put("id", c.getId());
        obj.put("nombre", c.getNombre());
        obj.put("descripcion", c.getDescripcion());
        obj.put("imagen", c.getImagen());
        obj.put("precio", c.getPrecio());
        return obj;
    }

    private JSONObject errorJson(String msg) {
        return new JSONObject().put("success", false).put("message", msg);
    }

    private JSONObject readJson(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        return new JSONObject(sb.toString());
    }
}