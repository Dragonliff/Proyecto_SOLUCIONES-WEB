package ModeloDAO;

import Modelo.usuarios;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public usuarios validarUsuarioPorCorreo(String correo, String contrasena) {
        usuarios u = null;
        String sql = "SELECT u.*, r.nombreRol FROM usuarios u " +
                     "INNER JOIN roles r ON u.idRol = r.idRol " +
                     "WHERE u.correo = ? AND u.contrasena = ? AND u.estado = 'Activo'";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasena);
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
            System.out.println("Error en validarUsuarioPorCorreo: " + e.getMessage());
        }

        return u;
    }
}
