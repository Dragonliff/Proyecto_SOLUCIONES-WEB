/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.herramientas;
import ModeloDAO.HerramientaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/HerramientaServlet")
public class HerramientaServlet extends HttpServlet {

    HerramientaDAO dao = new HerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminarHerramienta(idEliminar);
                response.sendRedirect("HerramientaServlet");
                break;

            default:
                List<herramientas> lista = dao.listarHerramientas();
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("vistasAdmin/herramientas.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "guardar";

        String idStr = request.getParameter("idHerramienta");
        int idHerramienta = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;

        herramientas h = new herramientas(
            idHerramienta,
            request.getParameter("nombre"),
            request.getParameter("tipo"),
            request.getParameter("estado")
        );

        if ("guardar".equals(accion)) {
            if (idHerramienta == 0) {
                dao.agregarHerramienta(h);
            } else {
                dao.actualizarHerramienta(h);
            }
        }

        response.sendRedirect("HerramientaServlet");
    }
}
