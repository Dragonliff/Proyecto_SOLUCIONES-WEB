/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import ModeloDAO.AsignacionHerramientaDAO;
import Modelo.asignaciones_mecanico_herramientas;
import Modelo.AlertaHerramientaService; // 👈 NUEVO IMPORT
import ModeloDAO.UsosHerramientasDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HerramientasMecanicoServlet", urlPatterns = {"/HerramientasMecanicoServlet"})
public class HerramientasMecanicoServlet extends HttpServlet {

    private final AsignacionHerramientaDAO dao = new AsignacionHerramientaDAO();
    private final UsosHerramientasDAO usosDao = new UsosHerramientasDAO(); 
    private final AlertaHerramientaService alertaService = new AlertaHerramientaService(); // 👈 NUEVA INSTANCIA
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion != null && accion.equals("obtenerAlerta")) {
            obtenerAlertaHerramienta(request, response);
            return; 
        }
        
        HttpSession sesion = request.getSession();
        Integer idMecanico = (Integer) sesion.getAttribute("idMecanico");  

        if (idMecanico == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        List<asignaciones_mecanico_herramientas> herramientasAsignadas = dao.listarPorMecanico(idMecanico);
        request.setAttribute("herramientasAsignadas", herramientasAsignadas);
        request.getRequestDispatcher("vistasMecanico/mecanicoAsignacion.jsp").forward(request, response);
    }
    private void obtenerAlertaHerramienta(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idHerramientaStr = request.getParameter("idHerramienta");
        if (idHerramientaStr == null || idHerramientaStr.isEmpty()) {
            response.getWriter().write(generarHTMLAlerta("N/D - ID no proporcionado", "alert-danger"));
            return;
        }
        
        int idHerramienta = Integer.parseInt(idHerramientaStr);
        
        // 1. Obtener las Horas Acumuladas
        double horasAcumuladas = usosDao.obtenerHorasAcumuladas(idHerramienta); 
        
        // 2. Determinar el Estado de Alerta usando la lógica centralizada
        String estadoAlerta = alertaService.calcularEstadoAlerta(horasAcumuladas);
        
        // 3. Generar el HTML de la alerta
        String htmlAlerta = generarHTMLAlerta(estadoAlerta, null); 
        
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(htmlAlerta);
    }
    
    private String generarHTMLAlerta(String estadoAlerta, String claseFuerza) {
        String clase;
        String icono;

        if (claseFuerza != null) {
            clase = claseFuerza;
            icono = "bi-x-octagon-fill";
        } else if (estadoAlerta.contains("MEDIA")) {
            clase = "alert-warning";
            icono = "bi-exclamation-triangle-fill";
        } else if (estadoAlerta.contains("ALTA") || estadoAlerta.contains("URGENTE")) {
            clase = "alert-danger";
            icono = "bi-exclamation-octagon-fill";
        } else { // BAJA o cualquier otro estado
            clase = "alert-success"; 
            icono = "bi-check-circle-fill";
        }

        return "<div class=\"alert " + clase + " d-flex align-items-center\" role=\"alert\">"
             + "<i class=\"bi " + icono + " me-2\"></i>"
             + "<div><strong>Alerta de Mantenimiento:</strong> " + estadoAlerta + "</div>"
             + "</div>";
    }
}
