module com.example.programmering2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.programmering2 to javafx.fxml;
    exports com.example.programmering2;
}