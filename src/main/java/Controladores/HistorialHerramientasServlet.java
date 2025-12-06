package Controladores;

import Modelo.herramientas;
import ModeloDAO.HerramientaDAO;
import ModeloDAO.UsosHerramientasDAO; 
import Modelo.AlertaHerramientaService; 

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/HistorialHerramientasServlet")
public class HistorialHerramientasServlet extends HttpServlet {

    private final HerramientaDAO dao = new HerramientaDAO();
    private final UsosHerramientasDAO usoDAO = new UsosHerramientasDAO(); 
    private final AlertaHerramientaService alertaService = new AlertaHerramientaService();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "realizarMantenimiento":
                realizarMantenimientoHerramienta(request, response);
                break;
            case "listar":
            default:
                listarHerramientasParaMantenimiento(request, response);
                break;
        }
    }
    
    private void listarHerramientasParaMantenimiento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<herramientas> lista = dao.listarHerramientas();
        
        for (herramientas herramienta : lista) {
            double horasAcumuladas = usoDAO.obtenerHorasAcumuladas(herramienta.getIdHerramienta());
            String estadoAlerta = alertaService.calcularEstadoAlerta(horasAcumuladas);
            
            herramienta.setHorasAcumuladas(horasAcumuladas);
            herramienta.setEstadoAlerta(estadoAlerta);
        }
        
        request.setAttribute("lista", lista); 
        request.getRequestDispatcher("/vistasAdmin/mantenimientosHerramientas.jsp").forward(request, response);
    }
    
    private void realizarMantenimientoHerramienta(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int idHerramienta = 0;
        double horasAcumuladas = 0.0;
        double horasTotalesActuales = 0.0;

        try {
            idHerramienta = Integer.parseInt(request.getParameter("id"));
            horasAcumuladas = Double.parseDouble(request.getParameter("horasAcumuladas"));
            horasTotalesActuales = Double.parseDouble(request.getParameter("horasTotales")); 

        } catch (NumberFormatException e) {
            System.err.println("ERROR de formato al realizar mantenimiento de herramienta: " + e.getMessage());
            response.sendRedirect("HistorialHerramientasServlet?accion=mantenimientosHerramienta&error=formatoInvalido");
            return;
        }

        double nuevasHorasTotales = horasTotalesActuales + horasAcumuladas;

        if (dao.registrarMantenimiento(idHerramienta, nuevasHorasTotales)) {
            response.sendRedirect("HistorialHerramientasServlet?accion=mantenimientosHerramienta&exito=mantenimientoRealizado");
        } else {
            response.sendRedirect("HistorialHerramientasServlet?accion=mantenimientosHerramienta&error=falloMantenimiento");
        }
    }
}