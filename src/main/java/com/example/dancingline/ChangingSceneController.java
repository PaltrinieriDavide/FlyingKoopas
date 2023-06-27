package com.example.dancingline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangingSceneController {
    @FXML
    private QuadCurve quadCurve;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dancingline-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setMaximized(true);
        stage.setTitle("DancingLine");
        stage.show();
    }

}
