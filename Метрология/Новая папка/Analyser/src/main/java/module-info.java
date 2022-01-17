module com.example.analyser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.analyser to javafx.fxml;
    exports com.example.analyser;
}