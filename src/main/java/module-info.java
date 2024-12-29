module com.example.demo10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.demo10 to javafx.fxml;
    exports com.example.demo10;
}