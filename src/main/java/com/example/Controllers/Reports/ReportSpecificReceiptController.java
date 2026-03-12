package com.example.Controllers.Reports;

import com.example.Database.DatabaseConnection;
import com.example.Database.JasperReportService;
import com.example.Model.Receipt;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
        import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportSpecificReceiptController implements Initializable {
    private static final String REPORT_PATH = "src/main/resources/reports/R3.jrxml";
    private final JasperReportService reportService = new JasperReportService();

    @FXML
    private TextField outputField;
    @FXML private Button exportButton;
    @FXML private ProgressIndicator spinner;
    @FXML private Label statusLabel;
    @FXML public VBox root;
    @FXML private ComboBox<String> receiptsCombo;

    @FXML private VBox pdfPagesBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outputField.setText(defaultOutputPath());
        loadReceipts();
        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                root.prefWidthProperty().bind(newScene.widthProperty());
                root.prefHeightProperty().bind(Bindings.subtract(newScene.heightProperty(),30));
            }
        });
    }
    private void loadReceipts() {
        ObservableList<Receipt> receipts = DatabaseConnection.receiptTable;
        if (receipts == null || receipts.isEmpty()) {
            statusLabel.setText("No receipts found.");
            return;
        }
        receiptsCombo.getItems().clear();
        for (Receipt r : receipts) {
            receiptsCombo.getItems().add(r.getDocumentNumber());
        }
        receiptsCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void browseOutput() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        chooser.setInitialFileName("report.pdf");
        File file = chooser.showSaveDialog(getWindow());
        if (file != null) {
            outputField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void runExport() {
        String outputPath = outputField.getText().trim();

        if (outputPath.isEmpty()) {
            statusLabel.setText("Please fill in output path.");
            return;
        }

        // Build any report parameters you need here
        Map<String, Object> params = new HashMap<>();

        String workingDir = System.getProperty("user.dir");
        File imgFile = new File(workingDir, "src/main/resources/reports/images/forklift.png");
        System.out.println("Looking for image at: " + imgFile.getAbsolutePath());
        System.out.println("File exists: " + imgFile.exists());
        if (imgFile.exists()) {
            try {
                params.put("FORKLIFT_IMAGE", new FileInputStream(imgFile));
            } catch (FileNotFoundException e) {
                System.err.println("Image not found at: " + imgFile.getAbsolutePath());
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Image not found at: " + imgFile.getAbsolutePath());
        }
        params.put("DocumentNumber", receiptsCombo.getValue());
        // params.put("START_DATE", LocalDate.now().minusMonths(1));
        // params.put("REPORT_TITLE", "Monthly Summary");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                reportService.exportToPdf(REPORT_PATH, outputPath, params);
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
            statusLabel.setText("PDF exported successfully → " + outputPath);
            try {
                loadPdfPreview(outputPath);
            } catch (IOException ex) {
                statusLabel.setText("Preview error: " + ex.getMessage());
            }
        });

        task.setOnFailed(e -> {
            spinner.setVisible(false);
            exportButton.setDisable(false);
            Throwable ex = task.getException();
            statusLabel.setText("Error: " + (ex != null ? ex.getMessage() : "Unknown error"));
            if (ex != null) ex.printStackTrace();
        });

        new Thread(task, "jasper-export-thread").start();
    }

    private void loadPdfPreview(String pdfPath) throws IOException {
        pdfPagesBox.getChildren().clear();
        try (PDDocument doc = Loader.loadPDF(new File(pdfPath))) {
            PDFRenderer renderer = new PDFRenderer(doc);
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                BufferedImage img = renderer.renderImageWithDPI(i, 150);
                ImageView view = new ImageView(SwingFXUtils.toFXImage(img, null));
                view.setFitWidth(pdfPagesBox.getWidth());
                view.setPreserveRatio(true);
                pdfPagesBox.getChildren().add(view);
            }
        }
    }

    /** Resolves the owning {@link Window} from any injected control. */
    private Window getWindow() {
        return outputField.getScene().getWindow();
    }

    private static String defaultOutputPath() {
        String ts = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return System.getProperty("user.home") + File.separator + "Downloads"+ File.separator + "report_" + ts + ".pdf";
    }
}
