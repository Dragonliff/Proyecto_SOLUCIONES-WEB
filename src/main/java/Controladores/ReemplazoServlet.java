/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelo.SolicitudReemplazo;
import ModeloDAO.SolicitudReemplazoDAO;
import ModeloDAO.AsignacionConductorVehiculoDAO;
import Modelo.vehiculos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;


@WebServlet("/ReemplazoServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, 
        maxFileSize = 1024 * 1024 * 10,     
        maxRequestSize = 1024 * 1024 * 15   
)    
public class ReemplazoServlet extends HttpServlet {
    private SolicitudReemplazoDAO dao = new SolicitudReemplazoDAO();
    private AsignacionConductorVehiculoDAO asignacionDao = new AsignacionConductorVehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                mostrarFormulario(request, response);
                break;

            case "guardar":
                guardarSolicitud(request, response);
                break;

            default:
                listarSolicitudes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        guardarSolicitud(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idConductor = Integer.parseInt(request.getSession().getAttribute("idConductor").toString());

        List<vehiculos> vehiculos = asignacionDao.listarVehiculosPorConductor(idConductor);

        request.setAttribute("vehiculos", vehiculos);

        listarSolicitudes(request, response);
    }

    private void guardarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idConductor = Integer.parseInt(request.getParameter("idConductor"));
            int idVehiculoActual = Integer.parseInt(request.getParameter("idVehiculoActual"));
            String motivo = request.getParameter("motivo");
            String detalle = request.getParameter("detalle");

            // === 1. Procesar imagen adjunta ===
            Part filePart = request.getPart("imagen"); 
            String fileName = null;

            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                String uploadPath = getServletContext().getRealPath("/uploads");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                filePart.write(uploadPath + File.separator + fileName);
            }

            // === 2. Guardar datos en el modelo ===
            SolicitudReemplazo s = new SolicitudReemplazo();
            s.setIdConductor(idConductor);
            s.setIdVehiculoActual(idVehiculoActual);
            s.setMotivo(motivo);
            s.setDetalle(detalle);
            s.setImagen(fileName); // puede ser null

            boolean registrado = dao.crear(s);

            request.setAttribute("mensaje",
                    registrado ? "Solicitud enviada correctamente" : "Error al enviar solicitud");

        } catch (Exception e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
        }

        listarSolicitudes(request, response);
    }

    private void listarSolicitudes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idConductor = Integer.parseInt(request.getSession().getAttribute("idConductor").toString());

        List<SolicitudReemplazo> lista = dao.listarPorConductor(idConductor);

        request.setAttribute("lista", lista);

        request.getRequestDispatcher("vistasEmpleado/reemplazo.jsp").forward(request, response);
    }
}
