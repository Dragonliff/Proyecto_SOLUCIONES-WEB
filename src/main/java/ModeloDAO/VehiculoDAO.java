package ModeloDAO;

import Modelo.Conexion; // Aún necesitamos esta clase para getConnection()
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

    // ----------------------------------------------------
    // CONSTANTES SQL
    // ----------------------------------------------------
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

    // ----------------------------------------------------
    // 1. CREATE
    // ----------------------------------------------------
    public boolean crear(vehiculos vehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean creado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(SQL_INSERT);
            
            // Setear parámetros (orden: 1 a 7)
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setInt(4, vehiculo.getAnio());
            stmt.setString(5, vehiculo.getTipoVehiculo());
            stmt.setDouble(6, vehiculo.getKilometrajeActual());
            stmt.setString(7, vehiculo.getEstado());
            
            int filasAfectadas = stmt.executeUpdate();
            creado = filasAfectadas > 0;
            
            if (creado) {
                 System.out.println("✅ Inserción exitosa. Filas afectadas: " + filasAfectadas);
            } else {
                 System.err.println("❌ Fallo en la inserción. executeUpdate devolvió 0. Verificar restricciones SQL.");
            }
            
        } catch (SQLException ex) {
            System.err.println("🔴 ERROR SQL al crear vehículo: " + ex.getMessage());
            ex.printStackTrace(); 
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return creado;
    }

    // ----------------------------------------------------
    // 2. READ: Listar Todos
    // ----------------------------------------------------
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
            System.out.println("✅ Lectura exitosa. Vehículos encontrados: " + listaVehiculos.size());

        } catch (SQLException ex) {
            System.err.println("🔴 ERROR SQL al listar vehículos: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return listaVehiculos;
    }
    
    public vehiculos leerPorId(int idVehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        vehiculos vehiculo = null; 

        try {
            // 1. Obtener Conexión
            conn = Modelo.Conexion.getConexion();
            if (conn == null) return null;
            
            // 2. Preparar la Sentencia (¡Esta es la línea corregida!)
            // La constante SQL_SELECT_BY_ID DEBE estar definida arriba.
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID); 
            stmt.setInt(1, idVehiculo); 
            
            // 3. Ejecutar y Mapear
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                vehiculo = mapResultSetToVehiculo(rs);
                System.out.println("✅ Vehículo ID " + idVehiculo + " encontrado.");
            } else {
                System.out.println("⚠️ Vehículo con ID " + idVehiculo + " no encontrado.");
            }
        } catch (SQLException ex) {
            System.err.println("🔴 ERROR SQL al buscar vehículo por ID: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // 4. Cierre de recursos
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return vehiculo;
    }
    
    // ----------------------------------------------------
    // UTILIDADES
    // ----------------------------------------------------
    private vehiculos mapResultSetToVehiculo(ResultSet rs) throws SQLException {
        vehiculos v = new vehiculos();
        v.setIdVehiculo(rs.getInt("idVehiculo"));
        v.setPlaca(rs.getString("placa"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setAnio(rs.getInt("anio"));
        v.setTipoVehiculo(rs.getString("tipoVehiculo"));
        v.setKilometrajeActual(rs.getDouble("kilometrajeActual"));
        v.setEstado(rs.getString("estado"));
        return v;
    }
    
        // La constante SQL_UPDATE debe estar definida arriba
    public boolean actualizar(vehiculos vehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            // ¡ESTA ES LA LÍNEA QUE DABA ERROR! Debe usar la constante global.
            stmt = conn.prepareStatement(SQL_UPDATE); 
            
            // Setear parámetros (orden: 1 a 7, luego ID)
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
            System.err.println("🔴 ERROR SQL al actualizar vehículo: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return actualizado;
    }
// Dentro de VehiculoDAO.java

    public boolean cambiarEstado(int idVehiculo, String nuevoEstado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean estadoCambiado = false;
        
        try {
            conn = Conexion.getConexion();
            if (conn == null) return false;
            
            // ¡ESTA ES LA LÍNEA QUE DABA ERROR! Debe usar la constante global.
            stmt = conn.prepareStatement(SQL_UPDATE_ESTADO); 
            
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idVehiculo);
            
            estadoCambiado = stmt.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.err.println("🔴 ERROR SQL al cambiar estado del vehículo: " + ex.getMessage());
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
            conn = Modelo.Conexion.getConexion();
            if (conn == null) return false;
            
            // Usamos la constante para la eliminación física
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idVehiculo);
            
            eliminado = stmt.executeUpdate() > 0;
            
            if (eliminado) {
                System.out.println("✅ Vehículo ID " + idVehiculo + " eliminado FÍSICAMENTE de la base de datos.");
            }
        } catch (SQLException ex) {
            System.err.println("🔴 ERROR SQL al cambiar estado del vehículo: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return eliminado;
    }
}