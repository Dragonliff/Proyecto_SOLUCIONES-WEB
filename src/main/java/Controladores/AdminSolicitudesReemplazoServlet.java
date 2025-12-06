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

    private final SolicitudReemplazoDAO daoConductores = new SolicitudReemplazoDAO();
    private final ReemplazoHerramientaDAO daoMecanicos = new ReemplazoHerramientaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<SolicitudReemplazo> solicitudesConductores = daoConductores.listarTodas();
            req.setAttribute("solicitudesConductores", solicitudesConductores);

            List<solicitudes_reemplazo_herramienta> solicitudesMecanicos = daoMecanicos.listarTodas();
            req.setAttribute("solicitudesMecanicos", solicitudesMecanicos);
            
            HttpSession session = req.getSession();
            req.setAttribute("mensaje", session.getAttribute("mensaje"));
            req.setAttribute("error", session.getAttribute("error"));
            session.removeAttribute("mensaje");
            session.removeAttribute("error");

            req.getRequestDispatcher("vistasAdmin/solicitudes_reemplazo.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar las solicitudes: " + e.getMessage());
            req.getRequestDispatcher("vistasAdmin/solicitudes_reemplazo.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String accion = req.getParameter("accion");
        HttpSession session = req.getSession();

        if ("cambiarEstado".equals(accion)) {
            
            String tipo = req.getParameter("tipo"); 
            String idSolicitudStr = req.getParameter("idSolicitud");
            String nuevoEstado = req.getParameter("nuevoEstado");
            boolean actualizado = false;

            try {
                int idSolicitud = Integer.parseInt(idSolicitudStr);
                
                if ("conductor".equals(tipo)) {
                    actualizado = daoConductores.actualizarEstado(idSolicitud, nuevoEstado); 
                    
               } else if ("mecanico".equals(tipo)) {
                    actualizado = daoMecanicos.cambiarEstado(idSolicitud, nuevoEstado); 
                }

                if (actualizado) {
                    session.setAttribute("mensaje", "Solicitud ID " + idSolicitud + " de " + tipo + " actualizada a " + nuevoEstado + ".");
                } else {
                    session.setAttribute("error", "Error al actualizar la solicitud ID " + idSolicitud + ".");
                }

            } catch (NumberFormatException e) {
                session.setAttribute("error", "Error de formato: El ID de la solicitud no es válido.");
            } catch (Exception e) {
                session.setAttribute("error", "Error interno al procesar la solicitud.");
                e.printStackTrace();
            }
        }
        
        resp.sendRedirect("AdminSolicitudesReemplazoServlet");
    }
}