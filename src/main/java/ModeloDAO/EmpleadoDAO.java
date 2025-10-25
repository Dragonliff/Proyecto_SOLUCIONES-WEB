/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Modelo.usuarios;
import Modelo.Conexion;
import java.sql.*;
import java.util.*;

public class EmpleadoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // --- Listar empleados (solo mecánicos y conductores)
    public List<usuarios> listarEmpleados() {
        List<usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE idRol IN (2, 3)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios u = new usuarios(
                        rs.getInt("idUsuario"),
                        rs.getInt("idRol"),
                        rs.getString("nombreCompleto"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("estado"),
                        rs.getTimestamp("fechaRegistro")
                );
                lista.add(u);
            }
        } catch (Exception e) {
            System.out.println("Error listarEmpleados: " + e.getMessage());
        }
        return lista;
    }

    // --- Obtener por ID
    public usuarios obtenerPorId(int id) {
        usuarios u = null;
        String sql = "SELECT * FROM usuarios WHERE idUsuario=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                u = new usuarios(
                        rs.getInt("idUsuario"),
                        rs.getInt("idRol"),
                        rs.getString("nombreCompleto"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("estado"),
                        rs.getTimestamp("fechaRegistro")
                );
            }
        } catch (Exception e) {
            System.out.println("Error obtenerPorId: " + e.getMessage());
        }
        return u;
    }

    // --- Agregar empleado
    public boolean agregarEmpleado(usuarios u) {
        String sql = "INSERT INTO usuarios (idRol, nombreCompleto, usuario, contrasena, correo, telefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, u.getIdRol());
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getEstado());
            ps.executeUpdate();

            // 🔹 Obtener el ID generado del usuario
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idUsuarioGenerado = rs.getInt(1);

                // 🔹 Insertar en tabla respectiva según el rol
                if (u.getIdRol() == 2) {
                    registrarMecanico(idUsuarioGenerado, u.getTelefono());
                } else if (u.getIdRol() == 3) {
                    registrarConductor(idUsuarioGenerado, u.getTelefono());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error agregarEmpleado: " + e.getMessage());
            return false;
        }
    }

    // --- Actualizar empleado
    public boolean actualizarEmpleado(usuarios u) {
        String sql = "UPDATE usuarios SET idRol=?, nombreCompleto=?, usuario=?, contrasena=?, correo=?, telefono=?, estado=? WHERE idUsuario=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getIdRol());
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getEstado());
            ps.setInt(8, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error actualizarEmpleado: " + e.getMessage());
            return false;
        }
    }

    // --- Eliminar empleado
    public boolean eliminarEmpleado(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE idUsuario=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error eliminarEmpleado: " + e.getMessage());
            return false;
        }
    }
    
        // --- Registrar Conductor
    private void registrarConductor(int idUsuario, String telefono) {
        String sql = "INSERT INTO conductores (idUsuario, licenciaConducir, categoriaLicencia, fechaVencimiento, telefono) VALUES (?, '', '', NULL, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, telefono);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error registrarConductor: " + e.getMessage());
        }
    }

    // --- Registrar Mecánico
    private void registrarMecanico(int idUsuario, String telefono) {
        String sql = "INSERT INTO mecanicos (idUsuario, especialidad, experienciaAnios, telefono) VALUES (?, '', 0, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, telefono);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error registrarMecanico: " + e.getMessage());
        }
    }
}
