package Controladores;

import Modelo.herramientas;
import Modelo.proveedores;
import ModeloDAO.HerramientaDAO;
import ModeloDAO.ProveedorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/HerramientaServlet")
public class HerramientaServlet extends HttpServlet {

    HerramientaDAO dao = new HerramientaDAO();
    ProveedorDAO daoProveedor = new ProveedorDAO();

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

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                herramientas h = dao.obtenerPorId(idEditar);
                List<proveedores> listaProveedoresEditar = daoProveedor.listar();
                request.setAttribute("herramienta", h);
                request.setAttribute("proveedores", listaProveedoresEditar);
                request.getRequestDispatcher("vistasAdmin/herramientas.jsp").forward(request, response);
                break;
                
            default: // listar
                List<herramientas> lista = dao.listarHerramientas();
                List<proveedores> listaProveedores = daoProveedor.listar(); 
                request.setAttribute("lista", lista);
                request.setAttribute("proveedores", listaProveedores);
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

        int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        String estado = request.getParameter("estado");

        // --- Lógica de Horas Totales ---
        double horasTotales = 0.0;

        if (idHerramienta > 0) {
            // Al editar, recuperamos las horas acumuladas para no perder el historial
            herramientas hExistente = dao.obtenerPorId(idHerramienta);
            if (hExistente != null) {
                horasTotales = hExistente.getHorasTotales();
            } 
            // Si hExistente es null, horasTotales se mantiene en 0.0 (lo cual podría ser un bug lógico,
            // pero la actualización debería fallar en el DAO si el ID no existe).
        }

        // Usamos el constructor de 6 argumentos para AGREGAR (ID=0, Horas=0.0) o ACTUALIZAR (ID>0, Horas > 0)
        herramientas h = new herramientas(
            idHerramienta, 
            nombre,
            tipo,
            estado,
            idProveedor,
            horasTotales 
        );

        if ("guardar".equals(accion)) {
            boolean exito;
            if (idHerramienta == 0) {
                exito = dao.agregarHerramienta(h);
            } else {
                exito = dao.actualizarHerramienta(h);
            }

            // 🌟 AGREGAR LÓGICA DE MENSAJE PARA SABER SI FALLÓ
            if (exito) {
                request.getSession().setAttribute("mensaje", "✅ Operación realizada con éxito.");
            } else {
                // Esto se mostrará si el DAO devuelve 'false'
                request.getSession().setAttribute("error", "❌ Error al guardar la herramienta. Revise los logs del servidor.");
            }
        }

        response.sendRedirect("HerramientaServlet");
    }
}
