/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.SolicitudReemplazo;
import Modelo.solicitudes_reemplazo_herramienta;
import ModeloDAO.SolicitudReemplazoDAO;
import ModeloDAO.ReemplazoHerramientaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminSolicitudesReemplazoServlet")
public class AdminSolicitudesReemplazoServlet extends HttpServlet {

    private SolicitudReemplazoDAO daoConductores = new SolicitudReemplazoDAO();
    private ReemplazoHerramientaDAO daoMecanicos = new ReemplazoHerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {

            case "listar":
                // Solicitudes de Conductores
                List<SolicitudReemplazo> solicitudesConductores = daoConductores.listarTodas();
                request.setAttribute("solicitudesConductores", solicitudesConductores);

                // Solicitudes de Mecánicos
                List<solicitudes_reemplazo_herramienta> solicitudesMecanicos = daoMecanicos.listarTodas();
                request.setAttribute("solicitudesMecanicos", solicitudesMecanicos);

                // Ir al JSP
                request.getRequestDispatcher("vistasAdmin/solicitudes_reemplazo.jsp")
                        .forward(request, response);
                break;

            case "aprobarConductor":
                daoConductores.actualizarEstado(
                        Integer.parseInt(request.getParameter("id")),
                        "Aprobado"
                );
                response.sendRedirect("AdminSolicitudesReemplazoServlet?accion=listar");
                break;

            case "rechazarConductor":
                daoConductores.actualizarEstado(
                        Integer.parseInt(request.getParameter("id")),
                        "Rechazado"
                );
                response.sendRedirect("AdminSolicitudesReemplazoServlet?accion=listar");
                break;

            case "aprobarMecanico":
                daoMecanicos.cambiarEstado(
                        Integer.parseInt(request.getParameter("id")),
                        "Aprobado"
                );
                response.sendRedirect("AdminSolicitudesReemplazoServlet?accion=listar");
                break;

            case "rechazarMecanico":
                daoMecanicos.cambiarEstado(
                        Integer.parseInt(request.getParameter("id")),
                        "Rechazado"
                );
                response.sendRedirect("AdminSolicitudesReemplazoServlet?accion=listar");
                break;
        }
    }
}
