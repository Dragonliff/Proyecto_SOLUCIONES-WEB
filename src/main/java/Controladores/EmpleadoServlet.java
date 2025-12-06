package Controladores;

import Modelo.usuarios;
import ModeloDAO.EmpleadoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/EmpleadoServlet")
public class EmpleadoServlet extends HttpServlet {

    EmpleadoDAO dao = new EmpleadoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("action");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                request.setAttribute("accion", "Registrar");
                request.getRequestDispatcher("vistasAdmin/empleadoForm.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                usuarios emp = dao.obtenerPorId(idEditar);
                request.setAttribute("empleado", emp);
                request.setAttribute("accion", "Actualizar");
                request.getRequestDispatcher("vistasAdmin/empleadoForm.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminarEmpleado(idEliminar);
                response.sendRedirect("EmpleadoServlet");
                break;

            default:
                List<usuarios> lista = dao.listarEmpleados();
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("vistasAdmin/Empleados.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("action"); 

        String idUsuarioStr = request.getParameter("idUsuario");
        int idUsuario = (idUsuarioStr != null && !idUsuarioStr.isEmpty()) ? Integer.parseInt(idUsuarioStr) : 0;

        String idRolStr = request.getParameter("idRol");
        int idRol = (idRolStr != null && !idRolStr.isEmpty()) ? Integer.parseInt(idRolStr) : 0;

        usuarios u = new usuarios(
                idUsuario,
                idRol,
                request.getParameter("nombreCompleto"),
                request.getParameter("usuario"),
                request.getParameter("contrasena"),
                request.getParameter("correo"),
                request.getParameter("telefono"),
                request.getParameter("estado"),
                null
        );

        if ("guardar".equals(accion)) {
            if (u.getIdUsuario() == 0) {
                dao.agregarEmpleado(u);
            } else {
                dao.actualizarEmpleado(u);
            }
        }

        response.sendRedirect("EmpleadoServlet");
    }
}
