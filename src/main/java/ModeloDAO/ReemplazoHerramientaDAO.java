/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Modelo.solicitudes_reemplazo_herramienta;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReemplazoHerramientaDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Registrar nueva solicitud
    public boolean registrarSolicitud(solicitudes_reemplazo_herramienta s) {
        String sql = "INSERT INTO solicitudes_reemplazo_herramienta "
                + "(idMecanico, idHerramienta, motivo, detalle, imagen, estado, fechaSolicitud) "
                + "VALUES (?, ?, ?, ?, ?, 'Pendiente', NOW())";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, s.getIdMecanico());
            ps.setInt(2, s.getIdHerramienta());
            ps.setString(3, s.getMotivo());
            ps.setString(4, s.getDetalle());
            ps.setString(5, s.getImagen());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR al registrar solicitud: " + e.getMessage());
            return false;
        }
    }

    // Listar solicitudes del mecánico
    public List<solicitudes_reemplazo_herramienta> listarPorMecanico(int idMecanico) {
        List<solicitudes_reemplazo_herramienta> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_reemplazo_herramienta "
                   + "WHERE idMecanico = ? ORDER BY fechaSolicitud DESC";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMecanico);
            rs = ps.executeQuery();

            while (rs.next()) {
                solicitudes_reemplazo_herramienta s = new solicitudes_reemplazo_herramienta(
                        rs.getInt("idSolicitud"),
                        rs.getInt("idMecanico"),
                        rs.getInt("idHerramienta"),
                        rs.getString("motivo"),
                        rs.getString("detalle"),
                        rs.getString("imagen"),
                        rs.getString("estado"),
                        rs.getTimestamp("fechaSolicitud")
                );
                lista.add(s);
            }

        } catch (SQLException e) {
            System.out.println("ERROR al listar solicitudes mecánico: " + e.getMessage());
        }

        return lista;
    }

    // Listar todas para administrador
    public List<solicitudes_reemplazo_herramienta> listarTodas() {
        List<solicitudes_reemplazo_herramienta> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_reemplazo_herramienta ORDER BY fechaSolicitud DESC";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                solicitudes_reemplazo_herramienta s = new solicitudes_reemplazo_herramienta(
                        rs.getInt("idSolicitud"),
                        rs.getInt("idMecanico"),
                        rs.getInt("idHerramienta"),
                        rs.getString("motivo"),
                        rs.getString("detalle"),
                        rs.getString("imagen"),
                        rs.getString("estado"),
                        rs.getTimestamp("fechaSolicitud")
                );
                lista.add(s);
            }

        } catch (SQLException e) {
            System.out.println("ERROR al listar todas las solicitudes: " + e.getMessage());
        }

        return lista;
    }

    // Cambiar estado (Aprobar o Rechazar)
    public boolean cambiarEstado(int idSolicitud, String nuevoEstado) {
        String sql = "UPDATE solicitudes_reemplazo_herramienta SET estado = ? WHERE idSolicitud = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idSolicitud);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR al cambiar estado: " + e.getMessage());
            return false;
        }
    }
}
