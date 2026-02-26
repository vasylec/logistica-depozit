module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires net.sf.jasperreports.core;
    requires java.sql;
    requires net.sf.jasperreports.pdf;
    requires java.desktop;

    opens com.example to javafx.fxml, net.sf.jasperreports.core;
    exports com.example;
}
