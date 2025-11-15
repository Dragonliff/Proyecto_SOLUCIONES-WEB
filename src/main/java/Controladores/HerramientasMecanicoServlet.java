/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import ModeloDAO.AsignacionHerramientaDAO;
import Modelo.asignaciones_mecanico_herramientas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HerramientasMecanicoServlet", urlPatterns = {"/HerramientasMecanicoServlet"})
public class HerramientasMecanicoServlet extends HttpServlet {

    private final AsignacionHerramientaDAO dao = new AsignacionHerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
}
