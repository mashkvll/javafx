module com.example.demonn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.demonn to javafx.fxml;
    exports com.example.demonn;
}