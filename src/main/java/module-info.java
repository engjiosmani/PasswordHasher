module com.example.passwordhasher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports application;
    opens application to javafx.fxml;

    exports controller;
    opens controller to javafx.fxml;
}

