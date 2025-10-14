/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ConectaDB {
    public static Connection getConexion(){
        Connection cnx = null;
        
        String url = "jdbc:mysql://localhost:3306/simcardb?useTimeZone=true&"
                + "serverTimezone=UTC&autoReconnect=true";
        
        String user = "root";
        String password = "root";
        String Driver = "com.mysql.cj.jdbc.Driver";
        
        try {
            Class.forName(Driver);
            cnx = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConectaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cnx;       
    }
}
