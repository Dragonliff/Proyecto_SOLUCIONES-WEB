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
                    registrarMecanico(idUsuarioGenerado);
                } else if (u.getIdRol() == 3) {
                    registrarConductor(idUsuarioGenerado);
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


    // --- Eliminar empleado correctamente
    public boolean eliminarEmpleado(int idUsuario) {
        String sqlRol = "SELECT idRol FROM usuarios WHERE idUsuario=?";
        String sqlEliminarMecanico = "DELETE FROM mecanicos WHERE idUsuario=?";
        String sqlEliminarConductor = "DELETE FROM conductores WHERE idUsuario=?";
        String sqlEliminarUsuario = "DELETE FROM usuarios WHERE idUsuario=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // 🔒 Iniciar transacción

            // 1️⃣ Verificar qué rol tiene el usuario
            ps = con.prepareStatement(sqlRol);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            int idRol = 0;
            if (rs.next()) {
                idRol = rs.getInt("idRol");
            }
            rs.close();
            ps.close();

            // 2️⃣ Eliminar primero de la tabla hija correspondiente
            if (idRol == 2) { // Mecánico
                ps = con.prepareStatement(sqlEliminarMecanico);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
                ps.close();
            } else if (idRol == 3) { // Conductor
                ps = con.prepareStatement(sqlEliminarConductor);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
                ps.close();
            }

            // 3️⃣ Luego eliminar de la tabla usuarios
            ps = con.prepareStatement(sqlEliminarUsuario);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();

            con.commit(); // ✅ Confirmar transacción
            return true;

        } catch (Exception e) {
            System.out.println("Error eliminarEmpleado: " + e.getMessage());
            try {
                if (con != null) con.rollback(); // ❌ Deshacer cambios si algo falla
            } catch (SQLException ex) {
                System.out.println("Error rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    // --- Registrar Conductor
    private void registrarConductor(int idUsuario) {
        String sql = "INSERT INTO conductores (idUsuario, licenciaConducir, categoriaLicencia, fechaVencimiento) VALUES (?, '', '', NULL)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error registrarConductor: " + e.getMessage());
        }
    }

    // --- Registrar Mecánico
    private void registrarMecanico(int idUsuario) {
        String sql = "INSERT INTO mecanicos (idUsuario, especialidad, experienciaAnios) VALUES (?, '', 0)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error registrarMecanico: " + e.getMessage());
        }
    }
}
