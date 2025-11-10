/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.asignaciones_mecanico_herramientas;
import ModeloDAO.AsignacionHerramientaDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;


@WebServlet("/AsignacionHerramientaServlet")
public class AsignacionHerramientaServlet extends HttpServlet {
    AsignacionHerramientaDAO dao = new AsignacionHerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            if ("finalizar".equalsIgnoreCase(accion)) {
                String idAsignacionStr = request.getParameter("idAsignacion");

                if (idAsignacionStr != null && !idAsignacionStr.isBlank()) {
                    int idAsignacion = Integer.parseInt(idAsignacionStr);
                    dao.finalizarAsignacion(idAsignacion);
                } else {
                    System.err.println("⚠ No se recibió idAsignacion válido.");
                }

                // Redirigir siempre al listar después de finalizar
                response.sendRedirect("AsignacionHerramientaServlet?accion=listar");
                return;
            }

            // Mostrar lista normalmente
            request.setAttribute("listaAsignaciones", dao.listar());
            request.setAttribute("listaMecanicos", dao.listarMecanicos());
            request.setAttribute("listaHerramientas", dao.listarHerramientas());
            request.getRequestDispatcher("vistasAdmin/asignacionesHerramientas.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error en el servlet: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String accion = request.getParameter("accion");

            // 🔹 1. Si la acción es finalizar
            if ("finalizar".equalsIgnoreCase(accion)) {
                String idAsignacionStr = request.getParameter("idAsignacion");
                if (idAsignacionStr != null && !idAsignacionStr.isBlank()) {
                    int idAsignacion = Integer.parseInt(idAsignacionStr);
                    dao.finalizarAsignacion(idAsignacion);
                    response.sendRedirect("AsignacionHerramientaServlet?accion=listar");
                    return;
                } else {
                    throw new IllegalArgumentException("ID de asignación no válido para finalizar.");
                }
            }

            // 🔹 2. Si la acción es agregar una nueva asignación
            String idMecStr = request.getParameter("idMecanico");
            String idHerStr = request.getParameter("idHerramienta");

            if (idMecStr == null || idHerStr == null || idMecStr.isBlank() || idHerStr.isBlank()) {
                throw new IllegalArgumentException("ID de mecánico o herramienta no válido");
            }

            int idMecanico = Integer.parseInt(idMecStr);
            int idHerramienta = Integer.parseInt(idHerStr);
            Date fechaInicio = new Date();

            asignaciones_mecanico_herramientas a = new asignaciones_mecanico_herramientas();
            a.setIdMecanico(idMecanico);
            a.setIdHerramienta(idHerramienta);
            a.setFechaInicio(fechaInicio);
            a.setEstado("Activa");

            dao.agregar(a);
            response.sendRedirect("AsignacionHerramientaServlet?accion=listar");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al guardar o finalizar asignación: " + e.getMessage());
        }
    }
}
