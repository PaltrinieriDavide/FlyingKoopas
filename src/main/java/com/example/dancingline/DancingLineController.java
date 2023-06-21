package com.example.dancingline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.scene.shape.QuadCurve;

public class DancingLineController {

    @FXML
    private QuadCurve quadCurve;

    @FXML
    private Button prova;

    protected void updateCurve(double x1, double y1){
        quadCurve.setControlX(x1);
        quadCurve.setControlY(y1);
    }

    @FXML
    void onButtunClick(ActionEvent event) {
        updateCurve(-100,-100);
    }

}
