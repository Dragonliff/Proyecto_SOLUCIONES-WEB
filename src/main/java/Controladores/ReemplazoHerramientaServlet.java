/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.asignaciones_mecanico_herramientas;
import Modelo.solicitudes_reemplazo_herramienta;
import ModeloDAO.AsignacionHerramientaDAO;
import ModeloDAO.ReemplazoHerramientaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ReemplazoHerramientaServlet", urlPatterns = {"/ReemplazoHerramientaServlet"})
@MultipartConfig
public class ReemplazoHerramientaServlet extends HttpServlet {

    ReemplazoHerramientaDAO dao = new ReemplazoHerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idMecanicoObj = session.getAttribute("idMecanico");

        if (idMecanicoObj == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int idMecanico = Integer.parseInt(idMecanicoObj.toString());

        switch (accion) {

            case "listar":
                // 1. Listar solicitudes existentes
                List<solicitudes_reemplazo_herramienta> lista = dao.listarPorMecanico(idMecanico);
                request.setAttribute("listaSolicitudes", lista);

                // 2. Listar herramientas ASIGNADAS
                ModeloDAO.AsignacionHerramientaDAO daoAsign = new ModeloDAO.AsignacionHerramientaDAO();
                List<Modelo.asignaciones_mecanico_herramientas> herramientasAsignadas =
                        daoAsign.listarPorMecanico(idMecanico);

                request.setAttribute("herramientasAsignadas", herramientasAsignadas);

                // 3. Enviar al JSP
                request.getRequestDispatcher("vistasMecanico/reemplazoMecanico.jsp")
                       .forward(request, response);
                break;

            default:
                response.sendRedirect("vistasMecanico/reemplazoMecanico.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idMecanicoObj = session.getAttribute("idMecanico");

        if (idMecanicoObj == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        int idMecanico = Integer.parseInt(idMecanicoObj.toString());

        if ("crear".equals(accion)) {

            solicitudes_reemplazo_herramienta s = new solicitudes_reemplazo_herramienta();

            s.setIdMecanico(idMecanico);
            s.setIdHerramienta(Integer.parseInt(request.getParameter("idHerramienta")));
            s.setMotivo(request.getParameter("motivo"));
            s.setDetalle(request.getParameter("detalle"));

            Part archivo = request.getPart("imagen");
            String nombreArchivo = archivo.getSubmittedFileName();

            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {

                String ruta = request.getServletContext().getRealPath("/imagenes/reemplazos/");
                File carpeta = new File(ruta);
                if (!carpeta.exists()) carpeta.mkdirs();

                archivo.write(ruta + File.separator + nombreArchivo);

                s.setImagen("imagenes/reemplazos/" + nombreArchivo);
            } else {
                s.setImagen(null);
            }

            dao.registrarSolicitud(s);

            response.sendRedirect("ReemplazoHerramientaServlet?accion=listar");
        }
    }
}
