package com.example;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaFX application that lets the user choose a .jrxml template and an
 * output path, then exports a PDF via {@link JasperReportService}.
 *
 * Run with:  javafx:run  (Maven plugin)  or supply --module-path and --add-modules
 */
public class ReportExporterApp extends Application {

    private static final String DEFAULT_REPORT_PATH =
            "src/main/resources/reports/your_report.jrxml";

    private final JasperReportService reportService = new JasperReportService();

    private TextField  jrxmlField;
    private TextField  outputField;
    private Button     exportButton;
    private ProgressIndicator spinner;
    private Label      statusLabel;

    // ── Entry point ─────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        launch(args);
    }

    // ── JavaFX lifecycle ─────────────────────────────────────────────────────────

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jasper PDF Exporter – PostgreSQL");

        VBox root = buildUI(primaryStage);
        Scene scene = new Scene(root, 600, 280);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ── UI construction ──────────────────────────────────────────────────────────

    private VBox buildUI(Stage stage) {
        // ── JRXML row ──
        jrxmlField = new TextField(DEFAULT_REPORT_PATH);
        jrxmlField.setPrefWidth(400);
        Button browseJrxml = new Button("Browse…");
        browseJrxml.setOnAction(e -> browseForFile(stage, jrxmlField,
                new FileChooser.ExtensionFilter("JasperReport template", "*.jrxml")));

        HBox jrxmlRow = row("Report template (.jrxml):", jrxmlField, browseJrxml);

        // ── Output PDF row ──
        outputField = new TextField(defaultOutputPath());
        outputField.setPrefWidth(400);
        Button browseOutput = new Button("Browse…");
        browseOutput.setOnAction(e -> saveFileDialog(stage, outputField));

        HBox outputRow = row("Output PDF path:", outputField, browseOutput);

        // ── Export button + spinner ──
        exportButton = new Button("Export PDF");
        exportButton.setDefaultButton(true);
        exportButton.setStyle("-fx-font-size:14; -fx-padding:8 20;");
        exportButton.setOnAction(e -> runExport());

        spinner = new ProgressIndicator();
        spinner.setVisible(false);
        spinner.setPrefSize(28, 28);

        HBox buttonRow = new HBox(12, exportButton, spinner);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        // ── Status label ──
        statusLabel = new Label("Ready.");
        statusLabel.setWrapText(true);

        // ── Layout ──
        VBox root = new VBox(14, jrxmlRow, outputRow, buttonRow, new Separator(), statusLabel);
        root.setPadding(new Insets(20));
        return root;
    }

    // ── Export logic (runs on a background thread to keep the UI responsive) ────

    private void runExport() {
        String jrxmlPath  = jrxmlField.getText().trim();
        String outputPath = outputField.getText().trim();

        if (jrxmlPath.isEmpty() || outputPath.isEmpty()) {
            statusLabel.setText("⚠  Please fill in both paths.");
            return;
        }

        // Build any report parameters you need here
        Map<String, Object> params = new HashMap<>();
        // params.put("START_DATE", LocalDate.now().minusMonths(1));
        // params.put("REPORT_TITLE", "Monthly Summary");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                reportService.exportToPdf(jrxmlPath, outputPath, params);
                return null;
            }
        };

        task.setOnRunning(e -> {
            exportButton.setDisable(true);
            spinner.setVisible(true);
            statusLabel.setText("Generating PDF, please wait…");
        });

        task.setOnSucceeded(e -> {
            spinner.setVisible(false);
            exportButton.setDisable(false);
            statusLabel.setText("✔  PDF exported successfully → " + outputPath);
        });

        task.setOnFailed(e -> {
            spinner.setVisible(false);
            exportButton.setDisable(false);
            Throwable ex = task.getException();
            statusLabel.setText("✘  Error: " + ex.getMessage());
            ex.printStackTrace();
        });

        new Thread(task, "jasper-export-thread").start();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────────

    private static HBox row(String labelText, TextField field, Button button) {
        Label label = new Label(labelText);
        label.setMinWidth(180);
        HBox row = new HBox(8, label, field, button);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private void browseForFile(Stage stage, TextField target, FileChooser.ExtensionFilter filter) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(stage);
        if (file != null) target.setText(file.getAbsolutePath());
    }

    private void saveFileDialog(Stage stage, TextField target) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        chooser.setInitialFileName("report.pdf");
        File file = chooser.showSaveDialog(stage);
        if (file != null) target.setText(file.getAbsolutePath());
    }

    private static String defaultOutputPath() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return System.getProperty("user.home") + File.separator + "report_" + ts + ".pdf";
    }
}
