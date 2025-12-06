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
        String sql = "SELECT h.idHerramienta, h.nombre, h.tipo, h.estado, h.id_proveedor, h.horas_totales " +
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
                    rs.getString("estado"),
                    rs.getInt("id_proveedor"),
                    rs.getDouble("horas_totales")
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
                    rs.getString("estado"),
                    rs.getInt("id_proveedor"),
                    rs.getDouble("horas_totales") 
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

    // Agregar herramienta con proveedor
    public boolean agregarHerramienta(herramientas h) {
        String sql = "INSERT INTO herramientas (nombre, tipo, estado, id_proveedor, horas_totales) VALUES (?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getTipo());
            ps.setString(3, h.getEstado());
            ps.setInt(4, h.getIdProveedor()); 
            ps.setDouble(5, h.getHorasTotales()); 
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
        String sql = "UPDATE herramientas SET nombre=?, tipo=?, estado=?, id_proveedor=?, horas_totales=? WHERE idHerramienta=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getTipo());
            ps.setString(3, h.getEstado());
            ps.setInt(4, h.getIdProveedor()); 
            ps.setDouble(5, h.getHorasTotales());
            ps.setInt(6, h.getIdHerramienta());
            
            int filasAfectadas = ps.executeUpdate(); 
            return filasAfectadas > 0; 
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
                    rs.getString("estado"),
                    rs.getInt("id_proveedor"), 
                    rs.getDouble("horas_totales") 
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
    
    private static final String SQL_UPDATE_HORAS_TOTALES = 
        "UPDATE herramientas SET horas_totales = ? WHERE idHerramienta = ?";

    private static final String SQL_INSERT_MANTENIMIENTO_HERRAMIENTA = 
        "INSERT INTO mantenimientos_herramientas (idHerramienta, horas_al_mantenimiento) VALUES (?, ?)";
    
    public boolean registrarMantenimiento(int idHerramienta, double nuevasHorasTotales) {
        Connection conn = null;
        PreparedStatement psHoras = null;
        PreparedStatement psLog = null;
        boolean resultado = false;

        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;

            conn.setAutoCommit(false); 

            psHoras = conn.prepareStatement(SQL_UPDATE_HORAS_TOTALES);
            psHoras.setDouble(1, nuevasHorasTotales);
            psHoras.setInt(2, idHerramienta);

            if (psHoras.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            psLog = conn.prepareStatement(SQL_INSERT_MANTENIMIENTO_HERRAMIENTA);
            psLog.setInt(1, idHerramienta);
            psLog.setDouble(2, nuevasHorasTotales); 

            if (psLog.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            conn.commit(); 
            resultado = true;

        } catch (SQLException e) {
            System.err.println("ERROR SQL en transacción de mantenimiento de herramienta: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { }
            }
        } finally {
            try { if (psHoras != null) psHoras.close(); } catch (SQLException e) {}
            try { if (psLog != null) psLog.close(); } catch (SQLException e) {}
            try { 
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                } 
            } catch (SQLException e) {}
        }
        return resultado;
    }
}
