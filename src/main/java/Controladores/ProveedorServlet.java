package Controladores;

import Modelo.proveedores;
import ModeloDAO.ProveedorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO dao = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {

                case "editar":
                
                    String idEd = req.getParameter("id");
                    if (idEd != null && !idEd.isEmpty()) {
                        int id = Integer.parseInt(idEd);
                        proveedores p = dao.obtenerPorId(id);
                        req.setAttribute("proveedor", p); 
                    }
                    break;

                case "eliminar":
                    String idDel = req.getParameter("id");
                    if (idDel != null && !idDel.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idDel);
                            dao.eliminar(id);
                        } catch (NumberFormatException ignored) {}
                    }
                    
                    resp.sendRedirect("ProveedorServlet?accion=listar");
                    return;

                case "listar":
                default:
                    
                    break;
            }

            
            List<proveedores> lista = dao.listar();
            req.setAttribute("lista", lista);                
            req.getRequestDispatcher("vistasAdmin/proveedores.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        String accion = req.getParameter("accion");
        if (accion == null) accion = "guardar";

        try {
            if ("guardar".equalsIgnoreCase(accion)) {

                String idStr = req.getParameter("id_proveedor");
                int id = (idStr == null || idStr.isEmpty()) ? 0 : Integer.parseInt(idStr);

                proveedores p = new proveedores();
                p.setId_proveedor(id);
                p.setNombre(nullable(req.getParameter("nombre")));
                p.setContacto(nullable(req.getParameter("contacto")));
                p.setTelefono(nullable(req.getParameter("telefono")));
                p.setEmail(nullable(req.getParameter("email")));
                p.setDireccion(nullable(req.getParameter("direccion")));

                String cot = req.getParameter("cotizacion");
                double cotVal = 0.0;
                if (cot != null && !cot.isEmpty()) {
                    try { cotVal = Double.parseDouble(cot); } catch (NumberFormatException ex) { cotVal = 0.0; }
                }
                p.setCotizacion(cotVal);

                String estado = req.getParameter("estado");
                p.setEstado(estado == null ? "Activo" : estado);

                if (id == 0) {
                    dao.agregar(p);
                } else {
                    dao.actualizar(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        resp.sendRedirect("ProveedorServlet?accion=listar");
    }

    
    private String nullable(String s) {
        return s == null ? "" : s;
    }
}
