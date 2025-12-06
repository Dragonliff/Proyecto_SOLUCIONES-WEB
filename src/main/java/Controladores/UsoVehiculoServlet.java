package Controladores;

import Modelo.usos_vehiculos;
import ModeloDAO.UsoVehiculoDAO;
import Modelo.vehiculos;
import Modelo.AlertaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "UsoVehiculoServlet", urlPatterns = {"/UsoVehiculoServlet"})
public class UsoVehiculoServlet extends HttpServlet {

    UsoVehiculoDAO dao = new UsoVehiculoDAO();
    private final AlertaService alertaService = new AlertaService(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idConductorObj = session.getAttribute("idConductor");

        if (idConductorObj == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int idConductor = Integer.parseInt(idConductorObj.toString());
        
        
        if ("exportarPDF".equals(accion)) {
            exportarPDF(idConductor, response);
            return;
        }

        if ("exportarExcel".equals(accion)) {
            exportarExcel(idConductor, response);
            return;
        }

        if ("reporte".equals(accion)) {

            List<vehiculos> vehiculosAsignados = dao.listarVehiculosPorConductor(idConductor);
            Map<Integer, usos_vehiculos> usosActivos = dao.obtenerUsosActivosPorConductor(idConductor);

            request.setAttribute("vehiculosAsignados", vehiculosAsignados);
            request.setAttribute("usosActivos", usosActivos);

            request.getRequestDispatcher("vistasEmpleado/empleadoReporte.jsp")
                   .forward(request, response);
            return;
        }

        else if ("historial".equals(accion)) {

            List<usos_vehiculos> historial = dao.listarUsosPorConductor(idConductor);

            request.setAttribute("historial", historial);

            request.getRequestDispatcher("vistasEmpleado/historialUsoConductor.jsp")
                   .forward(request, response);
            return;
        }

        else {
            response.sendRedirect("vistasEmpleado/empleadoReporte.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Object idConductorObj = session.getAttribute("idConductor");

        if (idConductorObj == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int idConductor = Integer.parseInt(idConductorObj.toString());

        if ("iniciar".equals(accion)) {
            int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));
            
            double kmAcumulado = dao.obtenerKilometrajeAcumulado(idVehiculo);
            String estadoAlerta = alertaService.calcularEstadoAlerta(kmAcumulado);
            
            dao.registrarInicioUso(idVehiculo, idConductor);
            
            String mensajeAlerta = "";
            mensajeAlerta += "🚨 Estado de Mantenimiento del Vehículo (Km: " + String.format("%,.0f", kmAcumulado) + " km):\n";
            mensajeAlerta += estadoAlerta;
            
            session.setAttribute("alertaMantenimiento", mensajeAlerta);
        } else if ("finalizar".equals(accion)) {
            int idUso = Integer.parseInt(request.getParameter("idUso"));
            double kmRec = Double.parseDouble(request.getParameter("kmRecorridos"));
            String obs = request.getParameter("observaciones");
            String tipoComb = request.getParameter("tipoCombustible");
            double litros = Double.parseDouble(request.getParameter("litros"));
            double precioLitro = Double.parseDouble(request.getParameter("precioLitro"));

            dao.registrarFinUso(idUso, kmRec, obs, tipoComb, litros, precioLitro);
            session.removeAttribute("alertaMantenimiento");
        }

        response.sendRedirect(request.getContextPath() + "/UsoVehiculoServlet?accion=reporte");
    }
    
        private void exportarPDF(int idConductor, HttpServletResponse response) throws IOException {

        List<usos_vehiculos> historial = dao.listarUsosPorConductor(idConductor);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=HistorialUsoVehiculos.pdf");

        try {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, response.getOutputStream());

            pdf.open();
            pdf.add(new Paragraph("HISTORIAL DE USO DE VEHÍCULOS\n\n"));

            PdfPTable table = new PdfPTable(10);
            table.addCell("ID Uso");
            table.addCell("ID Vehículo");
            table.addCell("Fecha");
            table.addCell("Horas");
            table.addCell("KM");
            table.addCell("Combustible");
            table.addCell("Litros");
            table.addCell("Precio");
            table.addCell("Costo Total");
            table.addCell("Descripción");

            for (usos_vehiculos u : historial) {
                table.addCell(String.valueOf(u.getIdUso()));
                table.addCell(String.valueOf(u.getIdVehiculo()));
                table.addCell(String.valueOf(u.getFecha()));
                table.addCell(String.valueOf(u.getHorasUso()));
                table.addCell(String.valueOf(u.getKmRecorridos()));
                table.addCell(u.getTipoCombustible());
                table.addCell(String.valueOf(u.getLitros()));
                table.addCell(String.valueOf(u.getPrecioLitro()));
                table.addCell(String.valueOf(u.getCostoTotal()));
                table.addCell(u.getDescripcion());
            }

            pdf.add(table);
            pdf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        private void exportarExcel(int idConductor, HttpServletResponse response) throws IOException {

        List<usos_vehiculos> historial = dao.listarUsosPorConductor(idConductor);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=HistorialUsoVehiculos.xlsx");

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Historial");

        Row header = sheet.createRow(0);
        String[] columnas = {
            "ID Uso", "ID Vehículo", "Fecha", "Horas", "KM", "Combustible",
            "Litros", "Precio/Litro", "Costo Total", "Descripción"
        };

        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowNum = 1;
        for (usos_vehiculos u : historial) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getIdUso());
            row.createCell(1).setCellValue(u.getIdVehiculo());
            row.createCell(2).setCellValue(String.valueOf(u.getFecha()));
            row.createCell(3).setCellValue(u.getHorasUso());
            row.createCell(4).setCellValue(u.getKmRecorridos());
            row.createCell(5).setCellValue(u.getTipoCombustible());
            row.createCell(6).setCellValue(u.getLitros());
            row.createCell(7).setCellValue(u.getPrecioLitro());
            row.createCell(8).setCellValue(u.getCostoTotal());
            row.createCell(9).setCellValue(u.getDescripcion());
        }

        wb.write(response.getOutputStream());
        wb.close();
    }
    
}