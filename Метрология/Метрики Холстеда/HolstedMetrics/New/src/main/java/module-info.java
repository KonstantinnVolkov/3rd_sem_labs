module com.example.someshit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.someshit to javafx.fxml;
    exports com.example.someshit;
}