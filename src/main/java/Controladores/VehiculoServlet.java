package Controladores;

import ModeloDAO.VehiculoDAO;
import Modelo.vehiculos;
import ModeloDAO.UsoVehiculoDAO;
import Modelo.AlertaService; // Asumiendo que AlertaService está en el paquete Modelo
import Modelo.proveedorvehiculo;
import ModeloDAO.ProveedorVehiculoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/VehiculoServlet")
public class VehiculoServlet extends HttpServlet {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private final UsoVehiculoDAO usoDAO = new UsoVehiculoDAO(); // ¡NUEVO!
    private final AlertaService alertaService = new AlertaService(); // ¡NUEVO!
    
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
                
            case "mantenimientos": // <--- NUEVA ACCIÓN CLAVE
                listarVehiculosParaMantenimiento(request, response);
                break;
                
            case "realizarMantenimiento": // <--- ACCIÓN DEL BOTÓN
                realizarMantenimiento(request, response);
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
            response.sendRedirect("VehiculoServlet?accion=listar");
        }
    }

    private void listarVehiculos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la lista de vehículos
        List<vehiculos> lista = vehiculoDAO.leerTodos();
        
        ProveedorVehiculoDAO proveedorDAO = new ProveedorVehiculoDAO();


        // 2. Iterar sobre cada vehículo para calcular su estado de alerta
        for (vehiculos vehiculo : lista) {
            
            // a. Obtener el kilometraje acumulado usando el DAO
            double kmAcumulado = usoDAO.obtenerKilometrajeAcumulado(vehiculo.getIdVehiculo());
            
            // b. Determinar el estado de alerta usando el Service
            String estadoAlerta = alertaService.calcularEstadoAlerta(kmAcumulado);
            
            // c. Asignar los resultados al objeto vehículo (Gracias a la modificación del POJO)
            vehiculo.setKmAcumulado(kmAcumulado);
            vehiculo.setEstadoAlerta(estadoAlerta);
        }
        
        List<proveedorvehiculo> proveedores = proveedorDAO.listar();
        request.setAttribute("proveedores", proveedores);
        
        // 3. Enviar la lista de vehículos (ahora enriquecida con alertas) a la vista
        request.setAttribute("vehiculos", lista);
        
        request.getRequestDispatcher("/vistasAdmin/maquinas.jsp").forward(request, response);
    }
    
    // --------------------------------------------------------------------------
    // MÉTODO AÑADIDO: Carga la vista de mantenimiento y calcula el acumulado (¡CLAVE!)
    // --------------------------------------------------------------------------
    private void listarVehiculosParaMantenimiento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la lista de vehículos (misma lógica)
        List<vehiculos> lista = vehiculoDAO.leerTodos();

        // 2. Iterar sobre cada vehículo para calcular su estado de alerta
        // ESTE BUCLE ES EL QUE CARGA kmAcumulado EN EL OBJETO VEHICULO PARA QUE EL JSP LO RECIBA
        for (vehiculos vehiculo : lista) {
            
            // a. Obtener el kilometraje acumulado usando el DAO modificado (UsoVehiculoDAO)
            double kmAcumulado = usoDAO.obtenerKilometrajeAcumulado(vehiculo.getIdVehiculo());
            
            // b. Determinar el estado de alerta (opcional, pero útil para la vista)
            String estadoAlerta = alertaService.calcularEstadoAlerta(kmAcumulado);
            
            // c. Asignar los resultados al objeto vehículo
            vehiculo.setKmAcumulado(kmAcumulado); // <--- AHORA EL JSP TIENE EL VALOR CORRECTO
            vehiculo.setEstadoAlerta(estadoAlerta);
        }
        
        // 3. Enviar la lista a la vista de MANTENIMIENTO
        request.setAttribute("vehiculos", lista);
        request.getRequestDispatcher("/vistasAdmin/mantenimientos.jsp").forward(request, response); // <--- DIRIGE AL JSP DE MANTENIMIENTO
    }
    // --------------------------------------------------------------------------
    
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
            response.sendRedirect("VehiculoServlet?error=formatoInvalido");
            return; 
        }

        int idProveedor = 0;
        try {
            idProveedor = Integer.parseInt(request.getParameter("idProveedorVehiculo"));
        } catch (Exception e) {
            idProveedor = 0; // por si viene null
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

        vehiculo.setIdProveedorVehiculo(idProveedor);

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
            // CREACIÓN
            resultado = vehiculoDAO.crear(vehiculo);
        }

        if (resultado) {
            response.sendRedirect("VehiculoServlet?exito=" + (idParam != null && !idParam.isEmpty() ? "actualizado" : "creado"));
        } else {
            response.sendRedirect("VehiculoServlet?error=guardar");
        }
    }

    private void eliminarFisicamente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idVehiculo = 0;
        
        try {
            idVehiculo = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            System.err.println(" ERROR al eliminar: ID de vehículo no válido.");
            response.sendRedirect("VehiculoServlet?error=idInvalido");
            return;
        }

        if (vehiculoDAO.eliminarFisico(idVehiculo)) {
            System.out.println("Vehículo ID " + idVehiculo + " ELIMINADO FÍSICAMENTE.");
            response.sendRedirect("VehiculoServlet?exito=eliminado");
        } else {
            response.sendRedirect("VehiculoServlet?error=eliminar");
        }
    }
    
    private void cambiarEstadoVehiculo(HttpServletRequest request, HttpServletResponse response, String nuevoEstado)
            throws ServletException, IOException {

        int idVehiculo = 0;
        
        try {
            idVehiculo = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            System.err.println(" ERROR al cambiar estado: ID de vehículo no válido.");
            response.sendRedirect("VehiculoServlet?error=idInvalido");
            return;
        }

        if (vehiculoDAO.cambiarEstado(idVehiculo, nuevoEstado)) {
            response.sendRedirect("VehiculoServlet?exito=estadoCambiado");
        } else {
            response.sendRedirect("VehiculoServlet?error=cambiarEstado");
        }
    }
    
    private void realizarMantenimiento(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int idVehiculo = 0;
        double kmAcumulado = 0.0;
        double kmActual = 0.0;

        try {
            idVehiculo = Integer.parseInt(request.getParameter("id"));
            kmAcumulado = Double.parseDouble(request.getParameter("kmAcumulado"));
            kmActual = Double.parseDouble(request.getParameter("kmActual")); 

        } catch (NumberFormatException e) {
            // ... manejo de error ...
            return;
        }

        // Calcular el nuevo kilometraje total que se guardará en la base de datos
        double nuevoKmActual = kmActual + kmAcumulado;

        if (vehiculoDAO.registrarMantenimiento(idVehiculo, nuevoKmActual)) {
            response.sendRedirect("VehiculoServlet?accion=mantenimientos&exito=mantenimientoRealizado");
        } else {
            response.sendRedirect("VehiculoServlet?accion=mantenimientos&error=falloMantenimiento");
        }
    }
}