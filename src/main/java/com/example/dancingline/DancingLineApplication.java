package com.example.dancingline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DancingLineApplication extends Application {

    public static Circle circle;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(root.load());
        stage.setScene(scene);
        stage.setTitle("DancingLine");
        stage.show();
    }
}
