package Controladores;

import ModeloDAO.UsuarioDAO;
import Modelo.usuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("passworda");

        UsuarioDAO dao = new UsuarioDAO();
        usuarios u = dao.validarUsuarioPorCorreo(correo, contrasena);

        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", u);
            session.setAttribute("idRol", u.getIdRol());
            session.setAttribute("nombreUsuario", u.getNombreCompleto());
            
            if (u.getIdRol() == 3) {
                ModeloDAO.ConductorDAO cdao = new ModeloDAO.ConductorDAO();
                int idConductor = cdao.obtenerIdPorUsuario(u.getIdUsuario());
                session.setAttribute("idConductor", idConductor);
            }

            int rol = u.getIdRol();

            switch (rol) {
                case 1:
                    response.sendRedirect("vistasAdmin/inicio.jsp");
                    break;
                case 2:
                    response.sendRedirect("vistasMecanico/mecanicoFallas.jsp");
                    break;
                case 3:
                    response.sendRedirect("vistasEmpleado/empleadoMaquinas.jsp");
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }

        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
