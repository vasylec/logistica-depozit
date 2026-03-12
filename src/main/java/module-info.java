module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;

    requires java.sql;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.pdf;
    requires org.apache.pdfbox;

    opens com.example to javafx.fxml, net.sf.jasperreports.core;
    opens com.example.Controllers to javafx.fxml, net.sf.jasperreports.core;
    opens com.example.Model to javafx.base;

    exports com.example;
    exports com.example.Controllers;
    exports com.example.Controllers.Reports;
    opens com.example.Controllers.Reports to javafx.fxml, net.sf.jasperreports.core;
    exports com.example.Database;
    opens com.example.Database to javafx.fxml, net.sf.jasperreports.core;
}
