package Controladores;

import Modelo.proveedorvehiculo;
import ModeloDAO.ProveedorVehiculoDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProveedorVehiculoServlet", value = "/ProveedorVehiculoServlet")
public class ProveedorVehiculoServlet extends HttpServlet {

    ProveedorVehiculoDAO dao = new ProveedorVehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("lista", dao.listar());
        request.getRequestDispatcher("vistasAdmin/proveedorvehiculo.jsp")
        .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion.equals("guardar")) {

            int id = request.getParameter("id_proveedorVehiculo").isEmpty() ?
                     0 : Integer.parseInt(request.getParameter("id_proveedorVehiculo"));

            proveedorvehiculo p = new proveedorvehiculo();
            p.setNombre(request.getParameter("nombre"));
            p.setContacto(request.getParameter("contacto"));
            p.setTelefono(request.getParameter("telefono"));
            p.setEmail(request.getParameter("email"));
            p.setDireccion(request.getParameter("direccion"));
            p.setCotizacion(Double.parseDouble(request.getParameter("cotizacion")));
            p.setEstado(request.getParameter("estado"));

            if (id == 0) {
                dao.guardar(p);
            } else {
                p.setId_proveedorVehiculo(id);
                dao.actualizar(p);
            }

            response.sendRedirect("ProveedorVehiculoServlet");
        }

        if (accion.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("ProveedorVehiculoServlet");
        }
    }
}