package Controladores;

import ModeloDAO.VehiculoDAO;
import Modelo.vehiculos;
import ModeloDAO.UsoVehiculoDAO;
import Modelo.AlertaService; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/historialGlobalServlet")
public class historialGlobalServlet extends HttpServlet {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private final UsoVehiculoDAO usoDAO = new UsoVehiculoDAO(); 
    private final AlertaService alertaService = new AlertaService(); 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar"; 
        }

        switch (accion) {
            case "listar":
            default:
                listarVehiculos(request, response);
                break;

            case "eliminar":
                eliminarFisicamente(request, response); 
                break;

            case "activar":
 
                cambiarEstadoVehiculo(request, response, "Operativo"); 
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            guardarVehiculo(request, response);
        } else {
            response.sendRedirect("historialGlobalServlet?accion=listar");
        }
    }

    private void listarVehiculos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<vehiculos> lista = vehiculoDAO.leerTodos();

        for (vehiculos vehiculo : lista) {
            
            double kmAcumulado = usoDAO.obtenerKilometrajeAcumulado(vehiculo.getIdVehiculo());
            
            String estadoAlerta = alertaService.calcularEstadoAlerta(kmAcumulado);
            
            vehiculo.setKmAcumulado(kmAcumulado);
            vehiculo.setEstadoAlerta(estadoAlerta);
        }
        
        request.setAttribute("vehiculos", lista);
        
        request.getRequestDispatcher("/vistasAdmin/mantenimientos.jsp").forward(request, response);
    }

    private void guardarVehiculo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("idVehiculo");
        int anio = 0;
        double kilometrajeActual = 0.0;
        boolean resultado = false;
        
        try {
            anio = Integer.parseInt(request.getParameter("anio"));
            String kmStr = request.getParameter("kilometrajeActual");
            kilometrajeActual = Double.parseDouble(kmStr.replace(',', '.')); 

        } catch (NumberFormatException e) {
            System.err.println("ERROR de formato numérico en vehículo: " + e.getMessage());
            response.sendRedirect("historialGlobalServlet?error=formatoInvalido");
            return; 
        }

        String placa = request.getParameter("placa");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String tipoVehiculo = request.getParameter("tipoVehiculo");
        String estado = request.getParameter("estado");
        
        vehiculos vehiculo = new vehiculos();
        vehiculo.setPlaca(placa);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setAnio(anio);
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setKilometrajeActual(kilometrajeActual);
        vehiculo.setEstado(estado);

        if (idParam != null && !idParam.isEmpty()) {

            try {
                int idVehiculo = Integer.parseInt(idParam);
                vehiculo.setIdVehiculo(idVehiculo);
                resultado = vehiculoDAO.actualizar(vehiculo);
            } catch (NumberFormatException e) {
                System.err.println("ERROR de formato ID: " + e.getMessage());
                resultado = false;
            }
        } else {
            resultado = vehiculoDAO.crear(vehiculo);
        }

        if (resultado) {
            response.sendRedirect("historialGlobalServlet?exito=" + (idParam != null && !idParam.isEmpty() ? "actualizado" : "creado"));
        } else {
            response.sendRedirect("historialGlobalServlet?error=guardar");
        }
    }

    private void eliminarFisicamente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idVehiculo = 0;
        
        try {
            idVehiculo = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            System.err.println(" ERROR al eliminar: ID de vehículo no válido.");
            response.sendRedirect("historialGlobalServlet?error=idInvalido");
            return;
        }

        if (vehiculoDAO.eliminarFisico(idVehiculo)) {
            System.out.println("Vehículo ID " + idVehiculo + " ELIMINADO FÍSICAMENTE.");
            response.sendRedirect("historialGlobalServlet?exito=eliminado");
        } else {
            response.sendRedirect("historialGlobalServlet?error=eliminar");
        }
    }
    
    private void cambiarEstadoVehiculo(HttpServletRequest request, HttpServletResponse response, String nuevoEstado)
            throws ServletException, IOException {

        int idVehiculo = 0;
        
        try {
            idVehiculo = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            System.err.println(" ERROR al cambiar estado: ID de vehículo no válido.");
            response.sendRedirect("historialGlobalServlet?error=idInvalido");
            return;
        }

        if (vehiculoDAO.cambiarEstado(idVehiculo, nuevoEstado)) {
            response.sendRedirect("historialGlobalServlet?exito=estadoCambiado");
        } else {
            response.sendRedirect("historialGlobalServlet?error=cambiarEstado");
        }
    }
}
