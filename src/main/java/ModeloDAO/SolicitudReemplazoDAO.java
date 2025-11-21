/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;
import Modelo.Conexion;
import Modelo.SolicitudReemplazo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudReemplazoDAO {

    private static final String SQL_INSERT =
        "INSERT INTO solicitudes_reemplazo (idConductor, idVehiculoActual, motivo, detalle, imagen) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_POR_CONDUCTOR =
        "SELECT * FROM solicitudes_reemplazo WHERE idConductor = ? ORDER BY fechaSolicitud DESC";

    private static final String SQL_SELECT_TODAS =
        "SELECT s.*, c.licenciaConducir, v.placa, v.marca, v.modelo " +
        "FROM solicitudes_reemplazo s " +
        "JOIN conductores c ON s.idConductor = c.idConductor " +
        "JOIN vehiculos v ON s.idVehiculoActual = v.idVehiculo " +
        "ORDER BY fechaSolicitud DESC";

    private static final String SQL_UPDATE_ESTADO =
        "UPDATE solicitudes_reemplazo SET estado = ? WHERE idSolicitud = ?";

    // Crear nueva solicitud
    public boolean crear(SolicitudReemplazo s) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setInt(1, s.getIdConductor());
            ps.setInt(2, s.getIdVehiculoActual());
            ps.setString(3, s.getMotivo());
            ps.setString(4, s.getDetalle());
            ps.setString(5, s.getImagen()); 

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear solicitud: " + e.getMessage());
            return false;
        }
    }

    // Lista de solicitudes por conductor
    public List<SolicitudReemplazo> listarPorConductor(int idConductor) {
        List<SolicitudReemplazo> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_POR_CONDUCTOR)) {

            ps.setInt(1, idConductor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar solicitudes por conductor: " + e.getMessage());
        }

        return lista;
    }

    // Lista completa (Administrador)
    public List<SolicitudReemplazo> listarTodas() {
        List<SolicitudReemplazo> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_TODAS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar todas las solicitudes: " + e.getMessage());
        }

        return lista;
    }

    // Cambiar estado
    public boolean actualizarEstado(int idSolicitud, String nuevoEstado) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_ESTADO)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idSolicitud);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizando estado: " + e.getMessage());
            return false;
        }
    }

    // Mapeo de datos
    private SolicitudReemplazo map(ResultSet rs) throws SQLException {
        SolicitudReemplazo s = new SolicitudReemplazo();

        s.setIdSolicitud(rs.getInt("idSolicitud"));
        s.setIdConductor(rs.getInt("idConductor"));
        s.setIdVehiculoActual(rs.getInt("idVehiculoActual"));
        s.setMotivo(rs.getString("motivo"));
        s.setDetalle(rs.getString("detalle"));
        s.setEstado(rs.getString("estado"));
        s.setFechaSolicitud(rs.getTimestamp("fechaSolicitud"));
        s.setImagen(rs.getString("imagen")); 

        return s;
    }
}
