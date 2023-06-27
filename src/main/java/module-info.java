module com.example.dancingline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    //requires commons.math3;


    opens com.example.dancingline to javafx.fxml;
    exports com.example.dancingline;
}