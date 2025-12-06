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
    
    public List<usos_herramientas> listarUsosPorMecanico(int idMecanico) {
        List<usos_herramientas> lista = new ArrayList<>();
        String sql = "SELECT * FROM usos_herramientas WHERE idMecanico=? ORDER BY fecha DESC";

        try {
            con = Conexion.getConexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idMecanico);
            rs = ps.executeQuery();

            while (rs.next()) {
                usos_herramientas u = new usos_herramientas();
                u.setIdUso(rs.getInt("idUso"));
                u.setIdHerramienta(rs.getInt("idHerramienta"));
                u.setHorasUso(rs.getDouble("horasUso"));
                u.setObservaciones(rs.getString("observaciones"));
                u.setFecha(rs.getTimestamp("fecha"));

                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public double obtenerHorasAcumuladas(int idHerramienta) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double horasAcumuladas = 0.0;

        String sql = "SELECT COALESCE(SUM(uh.horasUso), 0) AS HorasAcumuladas " +
                     "FROM usos_herramientas uh " +
                     "WHERE uh.idHerramienta = ? " +
                     // Filtra por usos posteriores a la última entrada en mantenimientos_herramientas
                     "AND uh.fecha > COALESCE(( " +
                     "    SELECT MAX(mh.fecha_mantenimiento) " + 
                     "    FROM mantenimientos_herramientas mh " +
                     "    WHERE mh.idHerramienta = ? " +
                     "), '1900-01-01')";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idHerramienta);
            ps.setInt(2, idHerramienta);

            rs = ps.executeQuery();

            if (rs.next()) {
                horasAcumuladas = rs.getDouble("HorasAcumuladas");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener horas acumuladas de herramienta: " + e.getMessage());
        } finally {
            // Asegura el cierre de recursos
        }

        return horasAcumuladas;
    }
}