package ModeloDAO;

import Modelo.Conexion;
import Modelo.proveedorvehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorVehiculoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<proveedorvehiculo> listar() {
        List<proveedorvehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedorvehiculos";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                proveedorvehiculo p = new proveedorvehiculo();
                p.setId_proveedorVehiculo(rs.getInt("id_proveedorVehiculo"));
                p.setNombre(rs.getString("nombre"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion"));
                p.setCotizacion(rs.getDouble("cotizacion"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int guardar(proveedorvehiculo p) {
        String sql = "INSERT INTO proveedorvehiculos(nombre, contacto, telefono, email, direccion, cotizacion, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }

    public int actualizar(proveedorvehiculo p) {
        String sql = "UPDATE proveedorvehiculos SET nombre=?, contacto=?, telefono=?, email=?, direccion=?, cotizacion=?, estado=? "
                   + "WHERE id_proveedorVehiculo=?";

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
            ps.setInt(8, p.getId_proveedorVehiculo());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM proveedorvehiculos WHERE id_proveedorVehiculo=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
