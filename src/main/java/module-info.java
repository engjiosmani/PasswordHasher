module com.example.saltedhashauthapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.saltedhashauthapp to javafx.fxml;
    exports com.example.saltedhashauthapp;
}