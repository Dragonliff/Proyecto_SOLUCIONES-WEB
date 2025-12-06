package ModeloDAO;
import Modelo.Conexion;
import Modelo.conductores;
import java.sql.*;
import java.util.*;


public class ConductorDAO {

    public List<Map<String, Object>> listarConductores() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT c.idConductor, u.nombreCompleto " +
                     "FROM conductores c " +
                     "INNER JOIN usuarios u ON c.idUsuario = u.idUsuario";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("idConductor", rs.getInt("idConductor"));
                fila.put("nombreCompleto", rs.getString("nombreCompleto"));
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.err.println(" Error al listar conductores: " + e.getMessage());
        }
        return lista;
    }
    
    public List<conductores> listarTodos() {
        List<conductores> lista = new ArrayList<>();
        String sql = "SELECT idConductor, idUsuario, licenciaConducir, categoriaLicencia, fechaVencimiento AS fechaInicio, 'Activo' AS estado FROM conductores ORDER BY idConductor DESC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                conductores c = new conductores(
                    rs.getInt("idConductor"),
                    rs.getInt("idUsuario"),
                    rs.getString("licenciaConducir"),
                    rs.getString("categoriaLicencia"),
                    rs.getDate("fechaInicio"),
                    rs.getString("estado")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar conductores: " + e.getMessage());
        }

        return lista;
    }
    
    public int obtenerIdPorUsuario(int idUsuario) {
        int idConductor = -1;
        String sql = "SELECT idConductor FROM conductores WHERE idUsuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idConductor = rs.getInt("idConductor");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener idConductor: " + e.getMessage());
        }
        return idConductor;
    }
}
