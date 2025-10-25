/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import ModeloDAO.AsignacionConductorVehiculoDAO;
import Modelo.asignaciones_conductor_vehiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "EmpleadoAsignacionesServlet", urlPatterns = {"/EmpleadoAsignacionesServlet"})
public class EmpleadoAsignacionesServlet extends HttpServlet {

    private final AsignacionConductorVehiculoDAO dao = new AsignacionConductorVehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Integer idConductor = (Integer) sesion.getAttribute("idConductor"); // asegúrate que se guarda al iniciar sesión

        if (idConductor == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        List<asignaciones_conductor_vehiculo> lista = dao.listarPorConductor(idConductor);
        request.setAttribute("listaAsignaciones", lista);
        request.getRequestDispatcher("vistasEmpleado/empleadoMaquinas.jsp").forward(request, response);
    }
}
