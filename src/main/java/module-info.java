module com.example.dancingline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.dancingline to javafx.fxml;
    exports com.example.dancingline;
}