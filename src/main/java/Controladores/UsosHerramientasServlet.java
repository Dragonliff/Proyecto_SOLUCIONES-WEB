package Controladores; 

import Modelo.herramientas; 
import Modelo.usos_herramientas;
import ModeloDAO.HerramientaDAO; 
import ModeloDAO.UsosHerramientasDAO;

import Modelo.AlertaHerramientaService; // Asegúrate de que el paquete sea correcto

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "UsosHerramientasServlet", urlPatterns = {"/UsosHerramientasServlet"})
public class UsosHerramientasServlet extends HttpServlet {
    
    UsosHerramientasDAO usosDao = new UsosHerramientasDAO(); 
    HerramientaDAO herramientaDao = new HerramientaDAO(); 
    private final AlertaHerramientaService alertaService = new AlertaHerramientaService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Integer idMecanico = (Integer) request.getSession().getAttribute("idMecanico"); 
        
        if (idMecanico == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        if ("RegistrarUso".equals(accion)) {
            try {
                int idHerramienta = Integer.parseInt(request.getParameter("idHerramienta")); 
                double horasUso = Double.parseDouble(request.getParameter("horasUso"));
                String observaciones = request.getParameter("observaciones");

                usos_herramientas nuevoUso = new usos_herramientas();
                nuevoUso.setIdHerramienta(idHerramienta);
                nuevoUso.setIdMecanico(idMecanico); 
                nuevoUso.setHorasUso(horasUso);
                nuevoUso.setObservaciones(observaciones);

                boolean registrado = usosDao.registrarUso(nuevoUso);
                
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
            
            response.sendRedirect(request.getContextPath() + "/UsosHerramientasServlet?accion=Listar");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        Integer idMecanico = (Integer) request.getSession().getAttribute("idMecanico");

        if (idMecanico == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "Listar";
        }

        switch (accion) {
            case "obtenerAlerta": // 👈 NUEVO CASE PARA LA LLAMADA AJAX
                obtenerAlertaHerramienta(request, response);
                break;
                
            case "Listar":
                List<usos_herramientas> listaUsos = usosDao.listarUsosPorMecanico(idMecanico);
                request.setAttribute("registrosUso", listaUsos);

                List<herramientas> herramientasAsignadas = herramientaDao.listarPorMecanico(idMecanico);
                request.setAttribute("herramientasAsignadas", herramientasAsignadas);

                // Pasar mensajes
                if (request.getSession().getAttribute("mensaje") != null) {
                    request.setAttribute("mensaje", request.getSession().getAttribute("mensaje"));
                    request.getSession().removeAttribute("mensaje");
                }
                if (request.getSession().getAttribute("error") != null) {
                    request.setAttribute("error", request.getSession().getAttribute("error"));
                    request.getSession().removeAttribute("error");
                }

                request.getRequestDispatcher("/vistasMecanico/mecanicoInventario.jsp").forward(request, response);
                break;

            case "Historial":
                List<usos_herramientas> historial = usosDao.listarUsosPorMecanico(idMecanico);
                request.setAttribute("registrosUso", historial);

                request.getRequestDispatcher("vistasMecanico/historialderegistros.jsp").forward(request, response);
                break;
                
            case "Excel":
                generarExcel(response, idMecanico);
                break;

            case "PDF":
                generarPDF(response, idMecanico);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida.");
                break;
        }
    }
    
    // -------------------------------------------------------------------------
    // MÉTODOS AUXILIARES PARA LA ALERTA (Mover desde el otro Servlet)
    // -------------------------------------------------------------------------

    private void obtenerAlertaHerramienta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idHerramientaStr = request.getParameter("idHerramienta");

        try {
            if (idHerramientaStr == null || idHerramientaStr.isEmpty()) {
                response.getWriter().write(generarHTMLAlerta("N/D - ID no proporcionado", "alert-danger"));
                return;
            }

            // Usamos Integer.parseInt para obtener el ID de la herramienta
            int idHerramienta = Integer.parseInt(idHerramientaStr);

            // 1. Obtener las Horas Acumuladas (Requiere UsosHerramientasDAO.obtenerHorasAcumuladas)
            double horasAcumuladas = usosDao.obtenerHorasAcumuladas(idHerramienta);

            // 2. Determinar el Estado de Alerta usando la lógica centralizada (AlertaHerramientaService)
            String estadoAlerta = alertaService.calcularEstadoAlerta(horasAcumuladas); // <-- Asegúrate que el nombre del método sea 'determinarEstado'

            // 3. Generar el HTML de la alerta
            String htmlAlerta = generarHTMLAlerta(estadoAlerta, null);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(htmlAlerta);

        } catch (NumberFormatException e) {
            // Error si el ID no es un número
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(generarHTMLAlerta("Error de formato: ID de herramienta no válido.", "alert-danger"));
        } catch (Exception e) {
            // Captura otros errores (ej. de conexión a BD o DAO)
            e.printStackTrace(); // Imprime la traza en la consola del servidor (CRÍTICO)
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(generarHTMLAlerta("Error de procesamiento interno. Consulte logs del servidor.", "alert-danger"));
        }
    }
    
    private String generarHTMLAlerta(String estadoAlerta, String claseFuerza) {
        String clase;
        String icono;

        if (claseFuerza != null) {
            clase = claseFuerza;
            icono = "bi-x-octagon-fill";
        } else if (estadoAlerta.contains("MEDIA")) {
            clase = "alert-warning";
            icono = "bi-exclamation-triangle-fill";
        } else if (estadoAlerta.contains("ALTA") || estadoAlerta.contains("URGENTE")) {
            clase = "alert-danger";
            icono = "bi-exclamation-octagon-fill";
        } else { // BAJA o cualquier otro estado
            clase = "alert-success";
            icono = "bi-check-circle-fill";
        }

        return "<div class=\"alert " + clase + " d-flex align-items-center\" role=\"alert\">"
             + "<i class=\"bi " + icono + " me-2\"></i>"
             + "<div><strong>Alerta de Mantenimiento:</strong> " + estadoAlerta + "</div>"
             + "</div>";
    }
    
    private void generarExcel(HttpServletResponse response, int idMecanico) throws IOException {

        List<usos_herramientas> lista = usosDao.listarUsosPorMecanico(idMecanico);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Historial");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Historial_Usos.xlsx");

        // Encabezado
        Row header = sheet.createRow(0);
        String[] columnas = {"ID Uso", "ID Herramienta", "Horas Uso", "Observaciones", "Fecha Registro"};

        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        // Datos
        int fila = 1;
        for (usos_herramientas u : lista) {
            Row row = sheet.createRow(fila++);

            row.createCell(0).setCellValue(u.getIdUso());
            row.createCell(1).setCellValue(u.getIdHerramienta());
            row.createCell(2).setCellValue(u.getHorasUso());
            row.createCell(3).setCellValue(u.getObservaciones());
            row.createCell(4).setCellValue(u.getFecha().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    
    private void generarPDF(HttpServletResponse response, int idMecanico) throws IOException {

        List<usos_herramientas> lista = usosDao.listarUsosPorMecanico(idMecanico);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Historial_Usos.pdf");

        try {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, response.getOutputStream());
            pdf.open();

            // Título
            Paragraph titulo = new Paragraph("HISTORIAL DE USO DE HERRAMIENTAS\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            titulo.setAlignment(Element.ALIGN_CENTER);
            pdf.add(titulo);

            // Tabla
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell("ID Uso");
            table.addCell("ID Herramienta");
            table.addCell("Horas Uso");
            table.addCell("Observaciones");
            table.addCell("Fecha");

            for (usos_herramientas u : lista) {
                table.addCell(String.valueOf(u.getIdUso()));
                table.addCell(String.valueOf(u.getIdHerramienta()));
                table.addCell(String.valueOf(u.getHorasUso()));
                table.addCell(u.getObservaciones());
                table.addCell(u.getFecha().toString());
            }

            pdf.add(table);
            pdf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}