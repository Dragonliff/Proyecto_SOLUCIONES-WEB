/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;
import Modelo.Conexion;
import Modelo.asignaciones_mecanico_herramientas;
import Modelo.herramientas;
import java.sql.*;
import java.util.*;

public class AsignacionHerramientaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();

    // ✅ Listar asignaciones con nombres de mecánicos y herramientas
    public List<Map<String, Object>> listar() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = 
            "SELECT a.idAsignacion, " +
                        "       u.nombreCompleto AS mecanico, " +
                        "       h.nombre AS herramienta, " +
                        "       a.fechaInicio, " +
                        "       a.fechaFin, " +
                        "       a.estado " +
                        "FROM asignaciones_mecanico_herramienta a " +
                        "INNER JOIN mecanicos m ON a.idMecanico = m.idMecanico " +
                        "INNER JOIN usuarios u ON m.idUsuario = u.idUsuario " +
                        "INNER JOIN herramientas h ON a.idHerramienta = h.idHerramienta " +
                        "ORDER BY a.idAsignacion DESC";
                    try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("idAsignacion", rs.getInt("idAsignacion"));
                fila.put("mecanico", rs.getString("mecanico"));
                fila.put("herramienta", rs.getString("herramienta"));
                fila.put("fechaInicio", rs.getDate("fechaInicio"));
                fila.put("fechaFin", rs.getDate("fechaFin"));
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ✅ Agregar nueva asignación
    public boolean agregar(asignaciones_mecanico_herramientas a) {
        String sql = "INSERT INTO asignaciones_mecanico_herramienta (idMecanico, idHerramienta, fechaInicio, estado) VALUES (?, ?, ?, ?)";
        String sqlActualizarHerramienta = "UPDATE herramientas SET estado='En Uso' WHERE idHerramienta=?";
        try {
            con = cn.getConexion();
            con.setAutoCommit(false);

            // Registrar la asignación
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdMecanico());
            ps.setInt(2, a.getIdHerramienta());
            ps.setDate(3, new java.sql.Date(a.getFechaInicio().getTime()));
            ps.setString(4, a.getEstado());
            ps.executeUpdate();

            // Actualizar estado de la herramienta
            ps = con.prepareStatement(sqlActualizarHerramienta);
            ps.setInt(1, a.getIdHerramienta());
            ps.executeUpdate();

            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
        return false;
    }

    // ✅ Listar mecánicos disponibles
    public List<Map<String, Object>> listarMecanicos() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql =
            "SELECT m.idMecanico, u.nombreCompleto, m.especialidad " +
            "FROM mecanicos m " +
            "INNER JOIN usuarios u ON m.idUsuario = u.idUsuario " +
            "WHERE u.estado = 'Activo'";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("idMecanico", rs.getInt("idMecanico"));
                fila.put("nombreCompleto", rs.getString("nombreCompleto"));
                fila.put("especialidad", rs.getString("especialidad"));
                lista.add(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ✅ Listar herramientas disponibles
    public List<Map<String, Object>> listarHerramientas() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT idHerramienta, nombre, tipo, estado FROM herramientas WHERE estado='Disponible'";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("idHerramienta", rs.getInt("idHerramienta"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("tipo", rs.getString("tipo"));
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    private static final String SQL_SELECT_POR_MECANICO = 
        "SELECT a.*, h.nombre, h.tipo, h.estado AS estadoHerramienta " +
        "FROM asignaciones_mecanico_herramienta a " +
        "INNER JOIN herramientas h ON a.idHerramienta = h.idHerramienta " +
        "WHERE a.idMecanico = ? AND a.estado = 'Activa'";

    // 🔹 Listar herramientas asignadas a un mecánico
    public List<asignaciones_mecanico_herramientas> listarPorMecanico(int idMecanico) {
        List<asignaciones_mecanico_herramientas> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_POR_MECANICO)) {

            ps.setInt(1, idMecanico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                asignaciones_mecanico_herramientas a = new asignaciones_mecanico_herramientas();
                a.setIdAsignacion(rs.getInt("idAsignacion"));
                a.setIdMecanico(rs.getInt("idMecanico"));
                a.setIdHerramienta(rs.getInt("idHerramienta"));
                a.setFechaInicio(rs.getDate("fechaInicio"));
                a.setFechaFin(rs.getDate("fechaFin"));
                a.setEstado(rs.getString("estado"));

                // Objeto herramienta
                herramientas h = new herramientas();
                h.setIdHerramienta(rs.getInt("idHerramienta"));
                h.setNombre(rs.getString("nombre"));
                h.setTipo(rs.getString("tipo"));
                h.setEstado(rs.getString("estado"));

                // Registrar dentro de la asignación
                a.setHerramienta(h);

                lista.add(a);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar herramientas del mecánico: " + e.getMessage());
        }
        return lista;
    }
    
    // ✅ Finalizar una asignación (corrigido y seguro)
    public boolean finalizarAsignacion(int idAsignacion) {
        String sqlFinalizar = 
        "UPDATE asignaciones_mecanico_herramienta SET estado = 'Finalizada', fechaFin = NOW() WHERE idAsignacion = ?";

        String sqlLiberarHerramienta = 
        "UPDATE herramientas SET estado = 'Disponible' WHERE idHerramienta = (SELECT idHerramienta FROM asignaciones_mecanico_herramienta WHERE idAsignacion = ?)";


        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            con = cn.getConexion();
            con.setAutoCommit(false);

            // 🔹 Finalizar asignación
            ps1 = con.prepareStatement(sqlFinalizar);
            ps1.setInt(1, idAsignacion);
            ps1.executeUpdate();

            // 🔹 Liberar herramienta asociada
            ps2 = con.prepareStatement(sqlLiberarHerramienta);
            ps2.setInt(1, idAsignacion);
            ps2.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (con != null) con.close();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
        return false;
    }
}
