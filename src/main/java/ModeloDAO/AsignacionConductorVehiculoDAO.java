/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Modelo.Conexion;
import Modelo.asignaciones_conductor_vehiculo;
import java.sql.*;
import java.util.*;

public class AsignacionConductorVehiculoDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO asignaciones_conductor_vehiculo (idConductor, idVehiculo, fechaInicio, fechaFin, estado) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = 
        "SELECT * FROM asignaciones_conductor_vehiculo ORDER BY idAsignacion DESC";

    private static final String SQL_SELECT_BY_ID = 
        "SELECT * FROM asignaciones_conductor_vehiculo WHERE idAsignacion = ?";

    private static final String SQL_UPDATE = 
        "UPDATE asignaciones_conductor_vehiculo SET idConductor=?, idVehiculo=?, fechaInicio=?, fechaFin=?, estado=? WHERE idAsignacion=?";

    private static final String SQL_FINALIZAR_ASIGNACION = 
        "UPDATE asignaciones_conductor_vehiculo SET estado='Finalizada', fechaFin=? WHERE idAsignacion=?";

    private static final String SQL_DELETE = 
        "DELETE FROM asignaciones_conductor_vehiculo WHERE idAsignacion=?";

    /**
     * Consulta que lista las asignaciones ACTIVAS de un conductor y hace JOIN con la tabla vehiculos.
     */
    private static final String SQL_SELECT_BY_CONDUCTOR_JOIN = 
        "SELECT a.*, v.placa, v.marca, v.modelo, v.anio, v.tipoVehiculo " +
        "FROM asignaciones_conductor_vehiculo a " +
        "JOIN vehiculos v ON a.idVehiculo = v.idVehiculo " +
        "WHERE a.idConductor = ? AND a.estado = 'Activa'";


    public boolean crear(asignaciones_conductor_vehiculo asignacion) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean creado = false;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setInt(1, asignacion.getIdConductor());
            ps.setInt(2, asignacion.getIdVehiculo());
            ps.setDate(3, new java.sql.Date(asignacion.getFechaInicio().getTime()));
            
            if (asignacion.getFechaFin() != null) {
                ps.setDate(4, new java.sql.Date(asignacion.getFechaFin().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            ps.setString(5, asignacion.getEstado());
            creado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(" Error al crear asignación: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return creado;
    }

    public List<asignaciones_conductor_vehiculo> listarTodas() {
        List<asignaciones_conductor_vehiculo> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Usamos el mapeo básico, ya que esta consulta no tiene JOINs
                lista.add(mapResultSetToAsignacion(rs));
            }

        } catch (SQLException e) {
            System.err.println(" Error al listar asignaciones: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return lista;
    }

    public asignaciones_conductor_vehiculo obtenerPorId(int idAsignacion) {
        asignaciones_conductor_vehiculo asignacion = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, idAsignacion);
            rs = ps.executeQuery();

            if (rs.next()) {
                asignacion = mapResultSetToAsignacion(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener asignación por ID: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return asignacion;
    }

    public boolean actualizar(asignaciones_conductor_vehiculo asignacion) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean actualizado = false;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setInt(1, asignacion.getIdConductor());
            ps.setInt(2, asignacion.getIdVehiculo());
            ps.setDate(3, new java.sql.Date(asignacion.getFechaInicio().getTime()));
            
            if (asignacion.getFechaFin() != null) {
                ps.setDate(4, new java.sql.Date(asignacion.getFechaFin().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            ps.setString(5, asignacion.getEstado());
            ps.setInt(6, asignacion.getIdAsignacion());

            actualizado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar asignación: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return actualizado;
    }


    public boolean finalizarAsignacion(int idAsignacion, java.util.Date fechaFin) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean finalizado = false;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_FINALIZAR_ASIGNACION);
            ps.setDate(1, new java.sql.Date(fechaFin.getTime()));
            ps.setInt(2, idAsignacion);
            finalizado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("🔴 Error al finalizar asignación: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return finalizado;
    }


    public boolean eliminar(int idAsignacion) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean eliminado = false;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, idAsignacion);
            eliminado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("🔴 Error al eliminar asignación: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return eliminado;
    }
    
    /**
     * Obtiene las asignaciones activas de un conductor, incluyendo los datos del vehículo mediante JOIN.
     * Esto soluciona que la vista del empleado no muestre los detalles del vehículo.
     * @param idConductor El ID del conductor.
     * @return Lista de asignaciones_conductor_vehiculo con detalles del vehículo.
     */
    public List<asignaciones_conductor_vehiculo> listarPorConductor(int idConductor) {
        List<asignaciones_conductor_vehiculo> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            // 📢 Usamos la consulta con JOIN definida arriba
            ps = con.prepareStatement(SQL_SELECT_BY_CONDUCTOR_JOIN); 
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 📢 Ahora el mapeo debe manejar los campos extra
                lista.add(mapResultSetToAsignacion(rs)); 
            }

        } catch (SQLException e) {
            System.err.println(" Error al listar asignaciones por conductor: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return lista;
    }


    /**
     * Mapea un ResultSet a un objeto asignaciones_conductor_vehiculo.
     * Este método se ha modificado para intentar mapear los campos del vehículo.
     */
    private asignaciones_conductor_vehiculo mapResultSetToAsignacion(ResultSet rs) throws SQLException {
        asignaciones_conductor_vehiculo a = new asignaciones_conductor_vehiculo();
        
        // --- Campos base de la asignación ---
        a.setIdAsignacion(rs.getInt("idAsignacion"));
        a.setIdConductor(rs.getInt("idConductor"));
        a.setIdVehiculo(rs.getInt("idVehiculo"));
        a.setFechaInicio(rs.getDate("fechaInicio"));
        a.setFechaFin(rs.getDate("fechaFin"));
        a.setEstado(rs.getString("estado"));
        
        // --- Campos del vehículo (Solo si existen en el ResultSet del JOIN) ---
        // Usamos un try/catch interno o metaData para verificar si las columnas existen. 
        // Para simplificar, asumimos que si la consulta fue SQL_SELECT_BY_CONDUCTOR_JOIN,
        // estas columnas estarán disponibles. Si la consulta fue SQL_SELECT_ALL, rs.getString("placa")
        // lanzará una SQLException, por lo que usamos un try-catch.
        
        try {
            // Intentamos obtener los campos de vehículo. 
            // Si la consulta no los incluye, lanza una excepción (que ignoramos).
            a.setPlaca(rs.getString("placa"));
            a.setMarca(rs.getString("marca"));
            a.setModelo(rs.getString("modelo"));
            a.setAnio(rs.getInt("anio"));
            a.setTipoVehiculo(rs.getString("tipoVehiculo"));
        } catch (SQLException ignore) {
            // Esto ocurre en listarTodas() u obtenerPorId() donde no hay JOIN.
            // Es seguro ignorarlo, ya que la asignación base está completa.
        }
        
        return a;
    }
}