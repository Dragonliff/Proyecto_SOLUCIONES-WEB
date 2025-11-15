/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDAO;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MecanicoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public int obtenerIdPorUsuario(int idUsuario) {
        int idMecanico = -1;
        String sql = "SELECT idMecanico FROM mecanicos WHERE idUsuario = ?";

        try {
            con = Conexion.getConexion(); 
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                idMecanico = rs.getInt("idMecanico");
            }

        } catch (Exception e) {
            System.out.println(" Error al obtener idMecanico: " + e.getMessage());
        }
        return idMecanico;
    }
}
