module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires mail;

    opens com.example.app to javafx.fxml;
    opens com.example.coffee to javafx.fxml;
    opens com.example.trucks to javafx.fxml;

    exports com.example.app;
    exports com.example.trucks;
}
