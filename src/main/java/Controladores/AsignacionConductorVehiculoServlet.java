package Controladores;

import Modelo.asignaciones_conductor_vehiculo;
import ModeloDAO.AsignacionConductorVehiculoDAO;
import ModeloDAO.ConductorDAO;
import ModeloDAO.VehiculoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;


@WebServlet(name = "AsignacionConductorVehiculoServlet", urlPatterns = {"/AsignacionConductorVehiculoServlet"})
public class AsignacionConductorVehiculoServlet extends HttpServlet {

    private final AsignacionConductorVehiculoDAO dao = new AsignacionConductorVehiculoDAO();
    private final ConductorDAO conductorDAO = new ConductorDAO();
    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "listar":

                    request.setAttribute("listaAsignaciones", dao.listarTodas());
                    request.setAttribute("listaConductores", conductorDAO.listarTodos());
                    request.setAttribute("listaVehiculos", vehiculoDAO.listarOperativos());
                    request.getRequestDispatcher("vistasAdmin/asignaciones.jsp").forward(request, response);
                    break;

                case "finalizar":
                    int idFinalizar = Integer.parseInt(request.getParameter("id"));
                    asignaciones_conductor_vehiculo asignacion = dao.obtenerPorId(idFinalizar);

                    if (asignacion != null) {
                        dao.finalizarAsignacion(idFinalizar, new java.sql.Date(new Date().getTime()));
                        vehiculoDAO.cambiarEstado(asignacion.getIdVehiculo(), "Operativo");
                    }

                    response.sendRedirect("AsignacionConductorVehiculoServlet?accion=listar");
                    break;

                case "eliminar":
                    int idEliminar = Integer.parseInt(request.getParameter("id"));
                    dao.eliminar(idEliminar);
                    response.sendRedirect("AsignacionConductorVehiculoServlet?accion=listar");
                    break;

                default:
                    response.sendRedirect("AsignacionConductorVehiculoServlet?accion=listar");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMensaje", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("crear".equalsIgnoreCase(accion)) {
            try {
                int idConductor = Integer.parseInt(request.getParameter("idConductor"));
                int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));
                Date fechaInicio = new Date();

                if (dao.existeAsignacionActiva(idConductor, idVehiculo)) {
                    request.setAttribute("errorMensaje", "Este conductor ya tiene asignado este vehículo actualmente.");

                    request.setAttribute("listaAsignaciones", dao.listarTodas());
                    request.setAttribute("listaConductores", conductorDAO.listarTodos());
                    request.setAttribute("listaVehiculos", vehiculoDAO.listarOperativos());

                    request.getRequestDispatcher("vistasAdmin/asignaciones.jsp").forward(request, response);
                    return;
                }

                asignaciones_conductor_vehiculo asignacion = new asignaciones_conductor_vehiculo();
                asignacion.setIdConductor(idConductor);
                asignacion.setIdVehiculo(idVehiculo);
                asignacion.setFechaInicio(fechaInicio);
                asignacion.setEstado("Activa");

                boolean creada = dao.crear(asignacion);

                if (creada) {
                    vehiculoDAO.cambiarEstado(idVehiculo, "Ocupado");
                    System.out.println(" Asignación creada correctamente.");
                } else {
                    System.out.println(" No se pudo crear la asignación.");
                }

                response.sendRedirect("AsignacionConductorVehiculoServlet?accion=listar");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMensaje", "Error al crear asignación: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }
}