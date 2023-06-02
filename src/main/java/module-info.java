module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    opens com.application.S2_dev to javafx.fxml;
    exports com.application.S2_dev;
    opens com.application.S2_dev.controlleur to javafx.fxml;
    exports com.application.S2_dev.controlleur;
}