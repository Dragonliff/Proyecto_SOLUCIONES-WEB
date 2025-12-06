package ModeloDAO;

import Modelo.solicitudes_reemplazo_herramienta;
import Modelo.SolicitudReemplazo;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // =============================================
    //              MÉTODOS DE CONTEO
    // =============================================

    public int contarVehiculos() {
        return obtenerEntero("SELECT COUNT(*) FROM vehiculos");
    }

    public int contarSolicitudesPendientes() {
        return obtenerEntero("SELECT COUNT(*) FROM solicitudes_reemplazo_herramienta WHERE estado = 'Pendiente'");
    }

    public int contarHerramientasDisponibles() {
        return obtenerEntero("SELECT COUNT(*) FROM herramientas WHERE estado = 'Disponible'");
    }


    private int obtenerEntero(String sql) {
        try {
            con = Conexion.getConexion();  
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("Error obtenerEntero: " + e.getMessage());
        }
        return 0;
    }

    // =============================================
    //          LISTAR ÚLTIMAS SOLICITUDES
    // =============================================

    public List<solicitudes_reemplazo_herramienta> listarUltimasSolicitudes() {

        List<solicitudes_reemplazo_herramienta> lista = new ArrayList<>();

        String sql =
            "SELECT * " +
            "FROM solicitudes_reemplazo_herramienta " +
            "ORDER BY fechaSolicitud DESC " +
            "LIMIT 5";

        try {
            con = Conexion.getConexion();  
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                solicitudes_reemplazo_herramienta s = new solicitudes_reemplazo_herramienta();

                s.setIdSolicitud(rs.getInt("idSolicitud"));
                s.setIdMecanico(rs.getInt("idMecanico"));
                s.setIdHerramienta(rs.getInt("idHerramienta"));
                s.setMotivo(rs.getString("motivo"));
                s.setDetalle(rs.getString("detalle"));
                s.setEstado(rs.getString("estado"));
                s.setFechaSolicitud(rs.getTimestamp("fechaSolicitud"));

                lista.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error listar solicitudes: " + e.getMessage());
        }

        return lista;
    }
    
    // =============================================
    //      SOLICITUDES DEL CONDUCTOR
    // =============================================

    public int contarSolicitudesReemplazoConductor() {
        return obtenerEntero("SELECT COUNT(*) FROM solicitudes_reemplazo");
    }

    // Conteo por estado (Pendiente, Aprobada, Rechazada)
    public int contarSolicitudesReemplazoPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM solicitudes_reemplazo WHERE estado = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("Error contarSolicitudesReemplazoPorEstado: " + e.getMessage());
        }
        return 0;
    }

    public List<SolicitudReemplazo> listarUltimasSolicitudesConductor() {

        List<SolicitudReemplazo> lista = new ArrayList<>();

        String sql = 
            "SELECT * FROM solicitudes_reemplazo " +
            "ORDER BY fechaSolicitud DESC " +
            "LIMIT 5";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                SolicitudReemplazo s = new SolicitudReemplazo();

                s.setIdSolicitud(rs.getInt("idSolicitud"));
                s.setIdConductor(rs.getInt("idConductor"));
                s.setIdVehiculoActual(rs.getInt("idVehiculoActual"));
                s.setMotivo(rs.getString("motivo"));
                s.setDetalle(rs.getString("detalle"));
                s.setEstado(rs.getString("estado"));
                s.setFechaSolicitud(rs.getDate("fechaSolicitud"));
                s.setImagen(rs.getString("imagen"));

                lista.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error listarUltimasSolicitudesConductor: " + e.getMessage());
        }

        return lista;
    }

    public double calcularGastoCombustible(String fechaInicio, String fechaFin) {
        double total = 0;
        String sql = "SELECT SUM(litros * precioLitro) AS total FROM usos_vehiculos WHERE fecha BETWEEN ? AND ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return total;
    }
    
    public List<Double> gastoCombustiblePorMes(int anio) {
        List<Double> gastos = new ArrayList<>();
        String sql = "SELECT MONTH(fecha) AS mes, SUM(litros * precioLitro) AS total " +
                     "FROM usos_vehiculos " +
                     "WHERE YEAR(fecha) = ? " +
                     "GROUP BY MONTH(fecha) ORDER BY mes";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, anio);
            ResultSet rs = ps.executeQuery();

            for (int i = 0; i < 12; i++) gastos.add(0.0);

            while (rs.next()) {
                int mes = rs.getInt("mes");
                double total = rs.getDouble("total");
                gastos.set(mes - 1, total);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return gastos;
    }
    
}
