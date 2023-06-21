module com.example.dancingline {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dancingline to javafx.fxml;
    exports com.example.dancingline;
}