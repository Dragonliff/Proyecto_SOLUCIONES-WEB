/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;
import Modelo.usos_vehiculos;
import Modelo.Conexion;
import Modelo.vehiculos;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsoVehiculoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Registrar inicio de uso
    public boolean registrarInicioUso(int idVehiculo, int idConductor) {
        String sql = "INSERT INTO usos_vehiculos (idVehiculo, idConductor, fecha) VALUES (?, ?, NOW())";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idConductor);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar inicio de uso: " + e.getMessage());
            return false;
        }
    }

    // Registrar fin de uso (update)
    public boolean registrarFinUso(int idUso, double kmRecorridos, String descripcion) {
        String sql = "UPDATE usos_vehiculos "
                   + "SET kmRecorridos = ?, descripcion = ?, "
                   + "horasUso = ROUND(TIMESTAMPDIFF(MINUTE, fecha, NOW()) / 60, 2) "
                   + "WHERE idUso = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, kmRecorridos);
            ps.setString(2, descripcion);
            ps.setInt(3, idUso);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar fin de uso: " + e.getMessage());
            return false;
        }
    }

    // Verificar si hay uso activo (sin km registrados)
    public usos_vehiculos obtenerUsoActivo(int idVehiculo, int idConductor) {
        String sql = "SELECT * FROM usos_vehiculos WHERE idVehiculo = ? AND idConductor = ? AND kmRecorridos IS NULL";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idConductor);
            rs = ps.executeQuery();
            if (rs.next()) {
                usos_vehiculos u = new usos_vehiculos(
                    rs.getInt("idUso"),
                    rs.getInt("idVehiculo"),
                    rs.getInt("idConductor"),
                    rs.getTimestamp("fecha"),
                    rs.getDouble("horasUso"),
                    rs.getDouble("kmRecorridos"),
                    rs.getString("descripcion")
                );
                return u;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener uso activo: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los usos de un conductor
    public List<usos_vehiculos> listarUsosPorConductor(int idConductor) {
        List<usos_vehiculos> lista = new ArrayList<>();
        String sql = "SELECT * FROM usos_vehiculos WHERE idConductor = ? ORDER BY fecha DESC";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();
            while (rs.next()) {
                usos_vehiculos u = new usos_vehiculos(
                    rs.getInt("idUso"),
                    rs.getInt("idVehiculo"),
                    rs.getInt("idConductor"),
                    rs.getTimestamp("fecha"),
                    rs.getDouble("horasUso"),
                    rs.getDouble("kmRecorridos"),
                    rs.getString("descripcion")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar usos: " + e.getMessage());
        }
        return lista;
    }

    // Listar vehículos por conductor activo
    public List<vehiculos> listarVehiculosPorConductor(int idConductor) {
        List<vehiculos> listaVehiculos = new ArrayList<>();
        String sql = "SELECT v.* "
                   + "FROM asignaciones_conductor_vehiculo a "
                   + "INNER JOIN vehiculos v ON a.idVehiculo = v.idVehiculo "
                   + "WHERE a.idConductor = ? AND a.estado = 'Activa'";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();

            while (rs.next()) {
                vehiculos v = new vehiculos();
                v.setIdVehiculo(rs.getInt("idVehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setTipoVehiculo(rs.getString("tipoVehiculo"));
                v.setEstado(rs.getString("estado"));
                listaVehiculos.add(v);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar vehículos por conductor: " + e.getMessage());
        }

        return listaVehiculos;
    }

    // Obtener todos los usos activos por conductor (para mostrar botón rojo)
    public Map<Integer, usos_vehiculos> obtenerUsosActivosPorConductor(int idConductor) {
        Map<Integer, usos_vehiculos> mapa = new HashMap<>();

        String sql = "SELECT * FROM usos_vehiculos WHERE idConductor = ? AND kmRecorridos IS NULL";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();

            while (rs.next()) {
                usos_vehiculos uso = new usos_vehiculos(
                    rs.getInt("idUso"),
                    rs.getInt("idVehiculo"),
                    rs.getInt("idConductor"),
                    rs.getTimestamp("fecha"),
                    rs.getDouble("horasUso"),
                    rs.getDouble("kmRecorridos"),
                    rs.getString("descripcion")
                );

                mapa.put(uso.getIdVehiculo(), uso);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener usos activos: " + e.getMessage());
        }

        return mapa;
    }
}