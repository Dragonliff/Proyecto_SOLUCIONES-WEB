package ModeloDAO;

import Modelo.Conexion;
import Modelo.herramientas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HerramientaDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List<herramientas> listarPorMecanico(int idMecanico) {
        List<herramientas> lista = new ArrayList<>();
        String sql = "SELECT h.idHerramienta, h.nombre, h.tipo, h.estado " +
                     "FROM herramientas h INNER JOIN asignaciones_mecanico_herramienta a " +
                     "ON h.idHerramienta = a.idHerramienta " +
                     "WHERE a.idMecanico = ? AND a.estado = 'Activa'"; 
        
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMecanico);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                herramientas h = new herramientas(
                    rs.getInt("idHerramienta"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("estado")
                );
                lista.add(h);
            }
        } catch (Exception e) {
            System.err.println("Error al listar herramientas por mecánico: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return lista;
    }
    
    public List<herramientas> listarHerramientas() {
        List<herramientas> lista = new ArrayList<>();
        String sql = "SELECT * FROM herramientas";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                herramientas h = new herramientas(
                    rs.getInt("idHerramienta"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("estado")
                );
                lista.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    public boolean agregarHerramienta(herramientas h) {
        String sql = "INSERT INTO herramientas (nombre, tipo, estado) VALUES (?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getTipo());
            ps.setString(3, h.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    public boolean actualizarHerramienta(herramientas h) {
        String sql = "UPDATE herramientas SET nombre=?, tipo=?, estado=? WHERE idHerramienta=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getTipo());
            ps.setString(3, h.getEstado());
            ps.setInt(4, h.getIdHerramienta());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    public boolean eliminarHerramienta(int idHerramienta) {
        String sql = "DELETE FROM herramientas WHERE idHerramienta=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idHerramienta);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }

    public herramientas obtenerPorId(int idHerramienta) {
        herramientas h = null;
        String sql = "SELECT * FROM herramientas WHERE idHerramienta=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idHerramienta);
            rs = ps.executeQuery();
            if (rs.next()) {
                h = new herramientas(
                    rs.getInt("idHerramienta"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return h;
    }

    private void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}