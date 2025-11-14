package Controladores; 

import Modelo.herramientas; 
import Modelo.usos_herramientas;
import ModeloDAO.HerramientaDAO; 
import ModeloDAO.UsosHerramientasDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsosHerramientasServlet", urlPatterns = {"/UsosHerramientasServlet"})
public class UsosHerramientasServlet extends HttpServlet {
    
    UsosHerramientasDAO usosDao = new UsosHerramientasDAO(); 
    HerramientaDAO herramientaDao = new HerramientaDAO(); 

    /**
     * Maneja las peticiones POST (registro de formulario).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener y verificar el ID del mecánico desde la sesión (CLAVE CORREGIDA: "idMecanico")
        Integer idMecanico = (Integer) request.getSession().getAttribute("idMecanico"); 
        
        if (idMecanico == null) {
            // Si no hay ID de usuario en sesión, redirigir a login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        if ("RegistrarUso".equals(accion)) {
            try {
                // 2. Capturar y convertir parámetros
                int idHerramienta = Integer.parseInt(request.getParameter("idHerramienta")); 
                double horasUso = Double.parseDouble(request.getParameter("horasUso"));
                String observaciones = request.getParameter("observaciones");

                // 3. Crear y llenar objeto Modelo
                usos_herramientas nuevoUso = new usos_herramientas();
                nuevoUso.setIdHerramienta(idHerramienta);
                nuevoUso.setIdMecanico(idMecanico); 
                nuevoUso.setHorasUso(horasUso);
                nuevoUso.setObservaciones(observaciones);

                // 4. Registrar en BD
                boolean registrado = usosDao.registrarUso(nuevoUso);
                
                // 5. Establecer mensaje de feedback
                if (registrado) {
                    request.getSession().setAttribute("mensaje", "✅ Registro de uso exitoso.");
                } else {
                    request.getSession().setAttribute("error", "❌ Error al guardar el uso de la herramienta. Revise la conexión.");
                }
                
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "❌ Error de formato: Asegúrese que ID y Horas de Uso sean números válidos.");
            } catch (Exception e) {
                request.getSession().setAttribute("error", "❌ Ocurrió un error inesperado al procesar el formulario.");
            }
            
            // 6. Redirigir a la acción 'Listar'
            response.sendRedirect(request.getContextPath() + "/UsosHerramientasServlet?accion=Listar");
        }
    }

    /**
     * Maneja las peticiones GET (mostrar el listado y la vista).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener y verificar el ID del mecánico desde la sesión (CLAVE CORREGIDA: "idMecanico")
        Integer idMecanico = (Integer) request.getSession().getAttribute("idMecanico"); 
        
        if (idMecanico == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        if (accion == null || "Listar".equals(accion)) {
            
            // 2. Obtener la lista de registros de uso (se lista todo, no se filtra por mecánico)
            List<usos_herramientas> listaUsos = usosDao.listarUsos(); 
            request.setAttribute("registrosUso", listaUsos);
            
            // 3. Cargar la lista de herramientas ASIGNADAS usando el ID REAL de la sesión
            List<herramientas> herramientasAsignadas = herramientaDao.listarPorMecanico(idMecanico);
            request.setAttribute("herramientasAsignadas", herramientasAsignadas); 
            
            // 4. Mover los mensajes de la sesión a la petición
            if (request.getSession().getAttribute("mensaje") != null) {
                request.setAttribute("mensaje", request.getSession().getAttribute("mensaje"));
                request.getSession().removeAttribute("mensaje");
            }
            if (request.getSession().getAttribute("error") != null) {
                request.setAttribute("error", request.getSession().getAttribute("error"));
                request.getSession().removeAttribute("error");
            }

            // 5. Redirigir al JSP de la vista
            request.getRequestDispatcher("/vistasMecanico/mecanicoInventario.jsp").forward(request, response);
            
        } else {
            // Manejar otras acciones GET
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida.");
        }
    }
}