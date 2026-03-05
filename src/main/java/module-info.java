module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires net.sf.jasperreports.core;
    requires java.sql;
    requires net.sf.jasperreports.pdf;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;

    opens com.example to javafx.fxml, net.sf.jasperreports.core;
    opens com.example.Controllers to javafx.fxml, net.sf.jasperreports.core;
    opens com.example.Reports to javafx.fxml, net.sf.jasperreports.core;
    opens com.example.Model to javafx.base;

    exports com.example;
    exports com.example.Controllers;
    exports com.example.Reports;
}
