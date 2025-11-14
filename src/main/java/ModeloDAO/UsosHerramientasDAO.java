package ModeloDAO;

import Modelo.usos_herramientas;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsosHerramientasDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarUso(usos_herramientas uso) {

        String sql = "INSERT INTO usos_herramientas (idHerramienta, idMecanico, horasUso, observaciones) VALUES (?, ?, ?, ?)";
        
        try {
            con = Conexion.getConexion(); 
            if (con == null) return false;
            
            ps = con.prepareStatement(sql);
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
            
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en registrarUso: " + e.getMessage());
            }
        }
    }

    public List<usos_herramientas> listarUsos() {
        List<usos_herramientas> lista = new ArrayList<>();
        String sql = "SELECT * FROM usos_herramientas ORDER BY fecha DESC";
        
        try {

            con = Conexion.getConexion();
            if (con == null) return lista;
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                usos_herramientas uso = new usos_herramientas();
                uso.setIdUso(rs.getInt("idUso"));
                uso.setIdHerramienta(rs.getInt("idHerramienta"));
                uso.setIdMecanico(rs.getInt("idMecanico"));
                uso.setFecha(rs.getTimestamp("fecha"));
                uso.setHorasUso(rs.getDouble("horasUso"));
                uso.setObservaciones(rs.getString("observaciones"));
                lista.add(uso);
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL al listar usos: " + e.getMessage());
            e.printStackTrace();
        } finally {
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