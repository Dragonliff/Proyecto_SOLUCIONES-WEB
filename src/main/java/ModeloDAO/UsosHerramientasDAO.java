package ModeloDAO; // Asumiendo que la DAO está en ModeloDAO

import Modelo.usos_herramientas; // Importa el POJO
import Modelo.Conexion; // <<-- AHORA SE IMPORTA DESDE EL PAQUETE 'Modelo'
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsosHerramientasDAO {
    
    // Objetos para la operación JDBC
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // **********************************************
    // MÉTODO DE REGISTRO (CREATE)
    // **********************************************

    /**
     * Registra un nuevo uso de herramienta en la base de datos (Tabla: usos_herramientas).
     * @param uso El objeto usos_herramientas con los datos a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarUso(usos_herramientas uso) {
        // La fecha (columna 'fecha') la base de datos debería generarla automáticamente 
        String sql = "INSERT INTO usos_herramientas (idHerramienta, idMecanico, horasUso, observaciones) VALUES (?, ?, ?, ?)";
        
        try {
            // Utiliza el método estático de tu clase Conexion
            con = Conexion.getConexion(); 
            if (con == null) return false; // Verifica si la conexión falló
            
            ps = con.prepareStatement(sql);
            
            // Mapeo de valores del POJO al SQL
            ps.setInt(1, uso.getIdHerramienta());
            ps.setInt(2, uso.getIdMecanico());
            ps.setDouble(3, uso.getHorasUso());
            ps.setString(4, uso.getObservaciones());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error de SQL al registrar uso: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en registrarUso: " + e.getMessage());
            }
        }
    }

    // **********************************************
    // MÉTODO DE LISTADO (READ)
    // **********************************************
    
    /**
     * Obtiene una lista de todos los registros de uso de herramientas.
     * @return Una lista de objetos usos_herramientas.
     */
    public List<usos_herramientas> listarUsos() {
        List<usos_herramientas> lista = new ArrayList<>();
        String sql = "SELECT * FROM usos_herramientas ORDER BY fecha DESC";
        
        try {
            // Utiliza el método estático de tu clase Conexion
            con = Conexion.getConexion();
            if (con == null) return lista; // Si la conexión falla, devuelve una lista vacía
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                // Se necesita un constructor vacío o inicializar el objeto campo por campo
                usos_herramientas uso = new usos_herramientas(); 
                
                // Mapeo de columnas de la BD al POJO
                uso.setIdUso(rs.getInt("idUso"));
                uso.setIdHerramienta(rs.getInt("idHerramienta"));
                uso.setIdMecanico(rs.getInt("idMecanico"));
                uso.setFecha(rs.getTimestamp("fecha")); // Usa java.sql.Timestamp
                uso.setHorasUso(rs.getDouble("horasUso"));
                uso.setObservaciones(rs.getString("observaciones"));
                
                lista.add(uso);
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL al listar usos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en listarUsos: " + e.getMessage());
            }
        }
        return lista;
    }
}