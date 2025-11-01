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

        request.setAttribute("listaAsignaciones", dao.listar());
        request.setAttribute("listaMecanicos", dao.listarMecanicos());
        request.setAttribute("listaHerramientas", dao.listarHerramientas());

        request.getRequestDispatcher("vistasAdmin/asignacionesHerramientas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idMecanico = Integer.parseInt(request.getParameter("idMecanico"));
        int idHerramienta = Integer.parseInt(request.getParameter("idHerramienta"));
        Date fechaInicio = new Date();

        asignaciones_mecanico_herramientas a = new asignaciones_mecanico_herramientas();
        a.setIdMecanico(idMecanico);
        a.setIdHerramienta(idHerramienta);
        a.setFechaInicio(fechaInicio);
        a.setEstado("Activa");

        dao.agregar(a);
        response.sendRedirect("AsignacionHerramientaServlet");
    }
}
