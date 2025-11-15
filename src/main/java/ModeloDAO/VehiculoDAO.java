package ModeloDAO;

import Modelo.Conexion;
import Modelo.vehiculos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public VehiculoDAO() {
    }

    private static final String SQL_INSERT = 
            "INSERT INTO vehiculos (placa, marca, modelo, anio, tipoVehiculo, kilometrajeActual, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_ALL = 
            "SELECT idVehiculo, placa, marca, modelo, anio, tipoVehiculo, kilometrajeActual, estado FROM vehiculos ORDER BY idVehiculo DESC";
    
    private static final String SQL_UPDATE = 
            "UPDATE vehiculos SET placa = ?, marca = ?, modelo = ?, anio = ?, tipoVehiculo = ?, kilometrajeActual = ?, estado = ? WHERE idVehiculo = ?";
    
    private static final String SQL_UPDATE_ESTADO = 
            "UPDATE vehiculos SET estado = ? WHERE idVehiculo = ?";
    
    private static final String SQL_SELECT_BY_ID = 
        "SELECT idVehiculo, placa, marca, modelo, anio, tipoVehiculo, kilometrajeActual, estado FROM vehiculos WHERE idVehiculo = ?";
    
    private static final String SQL_DELETE = "DELETE FROM vehiculos WHERE idVehiculo = ?";


    public boolean crear(vehiculos vehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean creado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setInt(4, vehiculo.getAnio());
            stmt.setString(5, vehiculo.getTipoVehiculo());
            stmt.setDouble(6, vehiculo.getKilometrajeActual());
            stmt.setString(7, vehiculo.getEstado());
            
            creado = stmt.executeUpdate() > 0;
            
            if (creado) {
                System.out.println(" Inserción exitosa.");
            } else {
                System.err.println("Fallo en la inserción.");
            }
            
        } catch (SQLException ex) {
            System.err.println(" ERROR SQL al crear vehículo: " + ex.getMessage());
            ex.printStackTrace(); 
        } finally {
 
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return creado;
    }

    public List<vehiculos> leerTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<vehiculos> listaVehiculos = new ArrayList<>();
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return listaVehiculos;
            
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
     
                listaVehiculos.add(mapResultSetToVehiculo(rs));
            }
            System.out.println("Lectura exitosa. Vehículos encontrados: " + listaVehiculos.size());

        } catch (SQLException ex) {
            System.err.println(" ERROR SQL al listar vehículos: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return listaVehiculos;
    }
    
    public List<vehiculos> listarOperativos() {
        List<vehiculos> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM vehiculos WHERE estado = 'Operativo'";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
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
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println(" Error al listar vehículos operativos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return lista;
    }
    
    public vehiculos leerPorId(int idVehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        vehiculos vehiculo = null; 

        try {
            conn = Conexion.getConexion();
            if (conn == null) return null;
            
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID); 
            stmt.setInt(1, idVehiculo); 
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                vehiculo = mapResultSetToVehiculo(rs);
                System.out.println(" Vehículo ID " + idVehiculo + " encontrado.");
            } else {
                System.out.println("️ Vehículo con ID " + idVehiculo + " no encontrado.");
            }
        } catch (SQLException ex) {
            System.err.println("ERROR SQL al buscar vehículo por ID: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return vehiculo;
    }

    public boolean actualizar(vehiculos vehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(SQL_UPDATE); 
            
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setInt(4, vehiculo.getAnio());
            stmt.setString(5, vehiculo.getTipoVehiculo());
            stmt.setDouble(6, vehiculo.getKilometrajeActual());
            stmt.setString(7, vehiculo.getEstado());
            stmt.setInt(8, vehiculo.getIdVehiculo()); 
            
            actualizado = stmt.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.err.println(" ERROR SQL al actualizar vehículo: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return actualizado;
    }


    public boolean cambiarEstado(int idVehiculo, String nuevoEstado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean estadoCambiado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(SQL_UPDATE_ESTADO); 
            
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idVehiculo);
            
            estadoCambiado = stmt.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.err.println("ERROR SQL al cambiar estado del vehículo: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return estadoCambiado;
    }
    

    public boolean eliminarFisico(int idVehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean eliminado = false;

        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idVehiculo);
            
            eliminado = stmt.executeUpdate() > 0;
            
            if (eliminado) {
                System.out.println("Vehículo ID " + idVehiculo + " eliminado FÍSICAMENTE de la base de datos.");
            }
        } catch (SQLException ex) {
            System.err.println(" ERROR SQL al eliminar vehículo (FÍSICO): " + ex.getMessage());
            ex.printStackTrace();
        } finally {

            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return eliminado;
    }

    private vehiculos mapResultSetToVehiculo(ResultSet rs) throws SQLException {
        vehiculos v = new vehiculos();
        

        v.setIdVehiculo(rs.getInt("idVehiculo"));
        v.setPlaca(rs.getString("placa"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setAnio(rs.getInt("anio"));
        v.setTipoVehiculo(rs.getString("tipoVehiculo"));
        v.setEstado(rs.getString("estado"));

        try {
            v.setKilometrajeActual(rs.getDouble("kilometrajeActual"));
        } catch (SQLException e) {
            System.err.println(" Error de formato en KilometrajeActual para ID: " + v.getIdVehiculo() + ". Estableciendo a 0.0. Detalles: " + e.getMessage());
            v.setKilometrajeActual(0.0); 
        }
        
        return v;
    }
}