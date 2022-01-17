module com.example.programm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.programm to javafx.fxml;
    exports com.example.programm;
}