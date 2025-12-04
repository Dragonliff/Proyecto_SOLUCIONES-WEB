/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;

import Modelo.proveedores;
import Modelo.Conexion; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<proveedores> listar() {
        List<proveedores> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores ORDER BY nombre";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedores p = new proveedores();
                p.setId_proveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion"));
                p.setCotizacion(rs.getDouble("cotizacion"));
                p.setEstado(rs.getString("estado"));
                p.setFechaRegistro(rs.getString("fechaRegistro"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error listar proveedores: " + e.getMessage());
        } finally {
            // si tu Conexion.getConnection() no maneja el cierre, ciérralo aquí
        }
        return lista;
    }

    public proveedores obtenerPorId(int id) {
        proveedores p = null;
        String sql = "SELECT * FROM proveedores WHERE id_proveedor = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new proveedores();
                p.setId_proveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion"));
                p.setCotizacion(rs.getDouble("cotizacion"));
                p.setEstado(rs.getString("estado"));
                p.setFechaRegistro(rs.getString("fechaRegistro"));
            }
        } catch (Exception e) {
            System.out.println("Error obtener proveedor: " + e.getMessage());
        }
        return p;
    }

    public int agregar(proveedores p) {
        int r = 0;
        String sql = "INSERT INTO proveedores(nombre, contacto, telefono, email, direccion, cotizacion, estado) VALUES(?,?,?,?,?,?,?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getDireccion());
            ps.setDouble(6, p.getCotizacion());
            ps.setString(7, p.getEstado() == null ? "Activo" : p.getEstado());
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error agregar proveedor: " + e.getMessage());
        }
        return r;
    }

    public int actualizar(proveedores p) {
        int r = 0;
        String sql = "UPDATE proveedores SET nombre=?, contacto=?, telefono=?, email=?, direccion=?, cotizacion=?, estado=? WHERE id_proveedor=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getDireccion());
            ps.setDouble(6, p.getCotizacion());
            ps.setString(7, p.getEstado());
            ps.setInt(8, p.getId_proveedor());
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error actualizar proveedor: " + e.getMessage());
        }
        return r;
    }

    public int eliminar(int id) {
        int r = 0;
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error eliminar proveedor: " + e.getMessage());
        }
        return r;
    }

    public void guardar(proveedores p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object obtener(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
