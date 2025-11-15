/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Modelo.usuarios;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    Connection con;
    CallableStatement cs;
    ResultSet rs;

    public List<usuarios> listarEmpleados() {
        List<usuarios> lista = new ArrayList<>();

        try {
            con = Conexion.getConexion();
            cs = con.prepareCall("{CALL sp_listarEmpleados()}");
            rs = cs.executeQuery();

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

     public usuarios obtenerPorId(int id) {
        usuarios u = null;

        try {
            con = Conexion.getConexion();
            cs = con.prepareCall("{CALL sp_obtenerEmpleado(?)}");
            cs.setInt(1, id);
            rs = cs.executeQuery();

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

    public boolean agregarEmpleado(usuarios u) {
        try {
            con = Conexion.getConexion();
            cs = con.prepareCall("{CALL sp_agregarEmpleado(?,?,?,?,?,?,?)}");

            cs.setInt(1, u.getIdRol());
            cs.setString(2, u.getNombreCompleto());
            cs.setString(3, u.getUsuario());
            cs.setString(4, u.getContrasena());
            cs.setString(5, u.getCorreo());
            cs.setString(6, u.getTelefono());
            cs.setString(7, u.getEstado());

            rs = cs.executeQuery();

            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt("idGenerado");
            }

            if (idUsuario > 0) {
                if (u.getIdRol() == 2) {
                    registrarMecanico(idUsuario);
                } else if (u.getIdRol() == 3) {
                    registrarConductor(idUsuario);
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error agregarEmpleado: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEmpleado(usuarios u) {
        try {
            con = Conexion.getConexion();
            cs = con.prepareCall("{CALL sp_actualizarEmpleado(?,?,?,?,?,?,?,?)}");

            cs.setInt(1, u.getIdUsuario());
            cs.setInt(2, u.getIdRol());
            cs.setString(3, u.getNombreCompleto());
            cs.setString(4, u.getUsuario());
            cs.setString(5, u.getContrasena());
            cs.setString(6, u.getCorreo());
            cs.setString(7, u.getTelefono());
            cs.setString(8, u.getEstado());

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizarEmpleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int idUsuario) {
        try {
            con = Conexion.getConexion();
            cs = con.prepareCall("{CALL sp_eliminarEmpleado(?)}");
            cs.setInt(1, idUsuario);
            cs.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error eliminarEmpleado: " + e.getMessage());
            return false;
        }
    }

    private void registrarConductor(int idUsuario) {
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_registrarConductor(?)}")) {

            cs.setInt(1, idUsuario);
            cs.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error registrarConductor: " + e.getMessage());
        }
    }
    
    private void registrarMecanico(int idUsuario) {
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_registrarMecanico(?)}")) {

            cs.setInt(1, idUsuario);
            cs.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error registrarMecanico: " + e.getMessage());
        }
    }
}