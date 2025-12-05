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
            System.out.println(" Error al registrar inicio de uso: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registrarFinUso(int idUso, double kmRecorridos, String descripcion,
                                   String tipoCombustible, double litros, double precioLitro) {

        String sql = "UPDATE usos_vehiculos "
                   + "SET kmRecorridos = ?, "
                   + "descripcion = ?, "
                   + "tipoCombustible = ?, "
                   + "litros = ?, "
                   + "precioLitro = ?, "
                   + "costoTotal = ?, "
                   + "horasUso = ROUND(TIMESTAMPDIFF(MINUTE, fecha, NOW()) / 60, 2) "
                   + "WHERE idUso = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);

            ps.setDouble(1, kmRecorridos);
            ps.setString(2, descripcion);
            ps.setString(3, tipoCombustible);
            ps.setDouble(4, litros);
            ps.setDouble(5, precioLitro);
            ps.setDouble(6, litros * precioLitro); // 👈 AQUÍ se calcula el costo total
            ps.setInt(7, idUso);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR al registrar fin de uso: " + e.getMessage());
            return false;
        }
    }

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
                    rs.getString("descripcion"),
                    rs.getString("tipoCombustible"),
                    rs.getDouble("litros"),
                    rs.getDouble("precioLitro"),
                    rs.getDouble("costoTotal")
                );
                return u;
            }
        } catch (SQLException e) {
            System.out.println(" Error al obtener uso activo: " + e.getMessage());
        }
        return null;
    }

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
                    rs.getString("descripcion"),
                    rs.getString("tipoCombustible"),
                    rs.getDouble("litros"),
                    rs.getDouble("precioLitro"),
                    rs.getDouble("costoTotal")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println(" Error al listar usos: " + e.getMessage());
        }
        return lista;
    }

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
            System.err.println(" Error al listar vehículos por conductor: " + e.getMessage());
        }

        return listaVehiculos;
    }

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
                    rs.getString("descripcion"),
                    rs.getString("tipoCombustible"),
                    rs.getDouble("litros"),
                    rs.getDouble("precioLitro"),
                    rs.getDouble("costoTotal")
                );

                mapa.put(uso.getIdVehiculo(), uso);
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener usos activos: " + e.getMessage());
        }

        return mapa;
    }
    
    // Nuevo método en UsoVehiculoDAO.java
    public double obtenerKilometrajeAcumulado(int idVehiculo) {
        double kilometrajeTotal = 0.0;

        // Consulta SQL que suma kmRecorridos. (Ver nota sobre mantenimientos)
        String sql = "SELECT SUM(kmRecorridos) AS KilometrajeAcumulado "
                   + "FROM usos_vehiculos "
                   + "WHERE idVehiculo = ? AND kmRecorridos IS NOT NULL";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            rs = ps.executeQuery();

            if (rs.next()) {
                // El alias "KilometrajeAcumulado" se usa para obtener el resultado
                kilometrajeTotal = rs.getDouble("KilometrajeAcumulado");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener kilometraje acumulado: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar los recursos (rs, ps, con) aquí si tu clase Conexion no lo hace automáticamente.
        }
        return kilometrajeTotal;
    }
}