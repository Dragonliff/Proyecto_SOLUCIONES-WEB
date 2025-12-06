package Controladores;

import ModeloDAO.DashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datos KPI
        request.setAttribute("totalVehiculos", dashboardDAO.contarVehiculos());
        request.setAttribute("solicitudesPendientes", dashboardDAO.contarSolicitudesPendientes());
        request.setAttribute("herramientasDisponibles", dashboardDAO.contarHerramientasDisponibles());
        
        
        request.setAttribute("totalSolicitudesConductor",dashboardDAO.contarSolicitudesReemplazoConductor());
        request.setAttribute("solicitudesPendientesConductor",dashboardDAO.contarSolicitudesReemplazoPorEstado("Pendiente"));
        request.setAttribute("solicitudesAprobadasConductor",dashboardDAO.contarSolicitudesReemplazoPorEstado("Aprobada"));
        request.setAttribute("solicitudesRechazadasConductor",dashboardDAO.contarSolicitudesReemplazoPorEstado("Rechazada"));
        
        // Tablas
        request.setAttribute("ultimasSolicitudes", dashboardDAO.listarUltimasSolicitudes());
        request.setAttribute("ultimasSolicitudesConductor",dashboardDAO.listarUltimasSolicitudesConductor());
        
        

        
        request.getRequestDispatcher("vistasAdmin/inicio.jsp").forward(request, response);
    }
}