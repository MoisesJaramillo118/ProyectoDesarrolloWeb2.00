package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 */
public class ConectaDB {
    
    // Configuración de la base de datos
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/simcardb";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Cambia si tienes contraseña en MySQL
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error de conexión
     */
    public static Connection getConexion() throws SQLException {
        Connection conexion = null;
        
        try {
            // 1. Cargar el driver de MySQL
            Class.forName(DRIVER);
            
            // 2. Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 3. Log de éxito (solo para debug)
            if (conexion != null) {
                System.out.println("✅ Conexión exitosa a la base de datos: simcardb");
            }
            
            return conexion;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: Driver MySQL no encontrado");
            System.err.println("   Asegúrate de tener el JAR mysql-connector-java en las librerías");
            System.err.println("   Detalle: " + e.getMessage());
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo conectar a la base de datos");
            System.err.println("   URL: " + URL);
            System.err.println("   Usuario: " + USER);
            System.err.println("   Verifica que:");
            System.err.println("   1. MySQL esté corriendo (XAMPP/Workbench)");
            System.err.println("   2. La base de datos 'simcardb' exista");
            System.err.println("   3. El usuario y contraseña sean correctos");
            System.err.println("   Detalle: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Cierra una conexión de forma segura
     * @param conexion la conexión a cerrar
     */
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✅ Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método de prueba para verificar la conexión
     */
    public static void main(String[] args) {
        System.out.println("=== TEST DE CONEXIÓN ===");
        try {
            Connection conn = getConexion();
            if (conn != null) {
                System.out.println("✅ ¡Conexión exitosa!");
                System.out.println("   Base de datos: " + conn.getCatalog());
                cerrarConexion(conn);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en la conexión");
            e.printStackTrace();
        }
    }
}