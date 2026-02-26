package com.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service that compiles a .jrxml template, fills it with data from a remote
 * PostgreSQL database, and exports the result as a PDF file.
 *
 * Compatible with JasperStudio 7.0.3 / JasperReports 7.x.
 */
public class JasperReportService {

    private static final Logger LOGGER = Logger.getLogger(JasperReportService.class.getName());

    /**
     * Generates a PDF report.
     *
     * @param jrxmlPath       absolute path to the .jrxml template file
     * @param outputPdfPath   destination path for the generated PDF
     * @param reportParams    optional report parameters (may be {@code null})
     * @throws JRException    if JasperReports encounters an error
     * @throws Exception      for any other failure (DB, IO, …)
     */
    public void exportToPdf(String jrxmlPath,
                            String outputPdfPath,
                            Map<String, Object> reportParams) throws Exception {

        Connection connection = null;

        try {
            // 1. Compile the JRXML template → JasperReport object
            LOGGER.info("Compiling report template: " + jrxmlPath);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

            // 2. Open a connection to the remote PostgreSQL database
            connection = DatabaseConnection.getConnection();

            // 3. Merge template + DB data → JasperPrint (in-memory document)
            Map<String, Object> params = (reportParams != null)
                    ? reportParams
                    : new HashMap<>();

            LOGGER.info("Filling report with data from database…");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            // 4. Export to PDF
            LOGGER.info("Exporting PDF to: " + outputPdfPath);
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(outputPdfPath)));

            // Optional PDF export configuration
            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
            config.setCreatingBatchModeBookmarks(true); // handy for multi-page reports
            exporter.setConfiguration(config);

            exporter.exportReport();
            LOGGER.info("PDF exported successfully.");

        } finally {
            DatabaseConnection.close(connection);
        }
    }

    // ── Convenience overload – no extra parameters ──────────────────────────────
    public void exportToPdf(String jrxmlPath, String outputPdfPath) throws Exception {
        exportToPdf(jrxmlPath, outputPdfPath, null);
    }
}
