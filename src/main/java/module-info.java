module com.example.livemusicvenuematchmakerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.livemusicvenuematchmakerapp to javafx.basemanag;
    opens com.example.livemusicvenuematchmakerapp.model to javafx.base;
    exports com.example.livemusicvenuematchmakerapp;
}