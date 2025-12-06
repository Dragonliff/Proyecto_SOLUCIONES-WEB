package Controladores;

import ModeloDAO.DashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener fechas del filtro
        String fechaInicio = request.getParameter("inicio");
        String fechaFin = request.getParameter("fin");

        if (fechaInicio == null || fechaFin == null) {
            fechaInicio = "2025-01-01";
            fechaFin = "2025-12-31";
        }

        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);   

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
        
        double gasto = dashboardDAO.calcularGastoCombustible(fechaInicio, fechaFin);
        request.setAttribute("gastoCombustible", gasto);
        
        
        
        String anioParam = request.getParameter("anio");
        int anio = (anioParam != null) ? Integer.parseInt(anioParam) : 2025;

        List<Double> gastosMes = dashboardDAO.gastoCombustiblePorMes(anio);

        request.setAttribute("anio", anio);
        request.setAttribute("gastosMes", gastosMes);
        
        

        // Fechas en atributos (para que el JSP las mantenga)
        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);

        // Tabla solicitudes
        request.setAttribute("ultimasSolicitudes", dashboardDAO.listarUltimasSolicitudes());

        
        request.getRequestDispatcher("vistasAdmin/inicio.jsp").forward(request, response);
    }
}