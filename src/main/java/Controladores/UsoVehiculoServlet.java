/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.usos_vehiculos;
import ModeloDAO.UsoVehiculoDAO;
import Modelo.vehiculos;
import Modelo.AlertaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UsoVehiculoServlet", urlPatterns = {"/UsoVehiculoServlet"})
public class UsoVehiculoServlet extends HttpServlet {

    UsoVehiculoDAO dao = new UsoVehiculoDAO();
    private final AlertaService alertaService = new AlertaService(); // ¡Asegúrate de tener esta línea!

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idConductorObj = session.getAttribute("idConductor");

        if (idConductorObj == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int idConductor = Integer.parseInt(idConductorObj.toString());

        if ("reporte".equals(accion)) {

            List<vehiculos> vehiculosAsignados = dao.listarVehiculosPorConductor(idConductor);
            Map<Integer, usos_vehiculos> usosActivos = dao.obtenerUsosActivosPorConductor(idConductor);

            request.setAttribute("vehiculosAsignados", vehiculosAsignados);
            request.setAttribute("usosActivos", usosActivos);

            request.getRequestDispatcher("vistasEmpleado/empleadoReporte.jsp")
                   .forward(request, response);
            return;
        }

        else if ("historial".equals(accion)) {

            List<usos_vehiculos> historial = dao.listarUsosPorConductor(idConductor);

            request.setAttribute("historial", historial);

            request.getRequestDispatcher("vistasEmpleado/historialUsoConductor.jsp")
                   .forward(request, response);
            return;
        }

        else {
            response.sendRedirect("vistasEmpleado/empleadoReporte.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idConductorObj = session.getAttribute("idConductor");

        if (idConductorObj == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int idConductor = Integer.parseInt(idConductorObj.toString());

        if ("iniciar".equals(accion)) {
            int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));
            // 1. CALCULAR LA ALERTA
            double kmAcumulado = dao.obtenerKilometrajeAcumulado(idVehiculo);
            String estadoAlerta = alertaService.calcularEstadoAlerta(kmAcumulado);
            dao.registrarInicioUso(idVehiculo, idConductor);
            
            // 3. CREAR EL MENSAJE Y ALMACENAR EN SESIÓN
            String mensajeAlerta = "";
            mensajeAlerta += "🚨 Estado de Mantenimiento del Vehículo (Km: " + String.format("%,.0f", kmAcumulado) + " km):\n";
            mensajeAlerta += estadoAlerta;
            
            // Guardamos en la Sesión, ya que haremos una redirección (Post-Redirect-Get pattern)
            session.setAttribute("alertaMantenimiento", mensajeAlerta);
        } else if ("finalizar".equals(accion)) {
            int idUso = Integer.parseInt(request.getParameter("idUso"));
            double kmRec = Double.parseDouble(request.getParameter("kmRecorridos"));
            String obs = request.getParameter("observaciones");
            String tipoComb = request.getParameter("tipoCombustible");
            double litros = Double.parseDouble(request.getParameter("litros"));
            double precioLitro = Double.parseDouble(request.getParameter("precioLitro"));

            dao.registrarFinUso(idUso, kmRec, obs, tipoComb, litros, precioLitro);
            session.removeAttribute("alertaMantenimiento");
        }

        response.sendRedirect(request.getContextPath() + "/UsoVehiculoServlet?accion=reporte");
    }
}