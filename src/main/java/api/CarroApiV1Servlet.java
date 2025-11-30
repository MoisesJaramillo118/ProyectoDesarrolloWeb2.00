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
 * PUT    /api/v1/carros/{id}  - Actualiza un carro existente
 * DELETE /api/v1/carros/{id}  - Elimina un carro
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

    // ==================== CONFIGURACIÓN CORS ====================
    private void configurarCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // ==================== GET - LECTURA ====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        configurarCORS(response);
        
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
                    out.print(errorJson("Carro no encontrado"));
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson("Error interno del servidor: " + e.getMessage()));
        }
    }

    // ==================== POST - CREAR ====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        configurarCORS(response);
        
        PrintWriter out = response.getWriter();
        
        try {
            // Leer el body JSON
            String jsonBody = leerBody(request);
            JSONObject jsonInput = new JSONObject(jsonBody);
            
            // Validar campos requeridos
            if (!jsonInput.has("nombre") || !jsonInput.has("precio")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(errorJson("Campos requeridos: nombre, precio"));
                return;
            }
            
            // Crear el DTO
            CarroDTO carro = new CarroDTO();
            carro.setNombre(jsonInput.getString("nombre"));
            carro.setDescripcion(jsonInput.optString("descripcion", ""));
            carro.setImagen(jsonInput.optString("imagen", "default.jpg"));
            carro.setPrecio(jsonInput.getDouble("precio"));
            
            // Insertar en BD
            dao.insertar(carro);
            
            // Respuesta exitosa
            response.setStatus(HttpServletResponse.SC_CREATED);
            JSONObject respuesta = new JSONObject();
            respuesta.put("success", true);
            respuesta.put("message", "Carro creado correctamente");
            out.print(respuesta.toString(2));
            
        } catch (org.json.JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("JSON inválido: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson("Error al crear carro: " + e.getMessage()));
        }
    }

    // ==================== PUT - ACTUALIZAR ====================
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        configurarCORS(response);
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            // Validar que venga el ID en la URL
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(errorJson("Debe especificar el ID: PUT /api/v1/carros/{id}"));
                return;
            }
            
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Verificar que el carro exista
            CarroDTO existente = dao.obtenerPorId(id);
            if (existente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(errorJson("Carro con ID " + id + " no encontrado"));
                return;
            }
            
            // Leer el body JSON
            String jsonBody = leerBody(request);
            JSONObject jsonInput = new JSONObject(jsonBody);
            
            // Actualizar solo los campos que vienen en el JSON
            CarroDTO carro = new CarroDTO();
            carro.setId(id);
            carro.setNombre(jsonInput.optString("nombre", existente.getNombre()));
            carro.setDescripcion(jsonInput.optString("descripcion", existente.getDescripcion()));
            carro.setImagen(jsonInput.optString("imagen", existente.getImagen()));
            carro.setPrecio(jsonInput.optDouble("precio", existente.getPrecio()));
            
            // Actualizar en BD
            dao.actualizar(carro);
            
            // Respuesta exitosa
            JSONObject respuesta = new JSONObject();
            respuesta.put("success", true);
            respuesta.put("message", "Carro actualizado correctamente");
            respuesta.put("data", carroToJson(carro));
            out.print(respuesta.toString(2));
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("ID inválido"));
        } catch (org.json.JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("JSON inválido: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson("Error al actualizar carro: " + e.getMessage()));
        }
    }

    // ==================== DELETE - ELIMINAR ====================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        configurarCORS(response);
        
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        try {
            // Validar que venga el ID en la URL
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(errorJson("Debe especificar el ID: DELETE /api/v1/carros/{id}"));
                return;
            }
            
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Verificar que el carro exista
            CarroDTO existente = dao.obtenerPorId(id);
            if (existente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(errorJson("Carro con ID " + id + " no encontrado"));
                return;
            }
            
            // Eliminar de BD
            dao.eliminar(id);
            
            // Respuesta exitosa
            JSONObject respuesta = new JSONObject();
            respuesta.put("success", true);
            respuesta.put("message", "Carro eliminado correctamente");
            respuesta.put("id", id);
            out.print(respuesta.toString(2));
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(errorJson("ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(errorJson("Error al eliminar carro: " + e.getMessage()));
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================
    
    private JSONObject carroToJson(CarroDTO c) {
        JSONObject obj = new JSONObject();
        obj.put("id", c.getId());
        obj.put("nombre", c.getNombre());
        obj.put("descripcion", c.getDescripcion());
        obj.put("imagen", c.getImagen());
        obj.put("precio", c.getPrecio());
        return obj;
    }
    
    private String errorJson(String mensaje) {
        JSONObject error = new JSONObject();
        error.put("success", false);
        error.put("message", mensaje);
        return error.toString();
    }
    
    private String leerBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
