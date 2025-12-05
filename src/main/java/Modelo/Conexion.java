package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/mantenimiento_predictivo2";
    private static final String USER = "root"; 
    private static final String PASSWORD = "admin123"; 

    public static Connection getConexion() {
        Connection conexion = null; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con la base de datos.");
            
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontró la clase del Driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {

            System.err.println("ERROR SQL: Falló la conexión a la BD.");
            System.err.println("Mensaje de error: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
}