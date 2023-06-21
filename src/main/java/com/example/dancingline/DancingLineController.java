package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.QuadCurve;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.random.RandomGenerator;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 20;
    @FXML
    private QuadCurve quadCurve;

    @FXML
    private AnchorPane root;
    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();

    public void initialize() {
        onReset();
    }

    void onReset() {
        initializeTimer();
        initializeObjects();
    }

    private void initializeObjects() {
        bouncingSprites.clear();
        for (int i = 0; i < 7; i++) {
            bouncingSprites.add(generateBoncingSprite());
        }
        //root.getChildren().clear();
        root.getChildren().addAll(bouncingSprites);
    }

    private SpriteBouncing generateBoncingSprite() {
        Circle view = new Circle(30);
        view.setStroke(Color.ORANGE);
        view.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.3));

        view.setTranslateX(30);
        view.setTranslateY(30);

        RandomGenerator rnd = RandomGenerator.getDefault();
        PVector location = new PVector(rnd.nextDouble() * root.getHeight(), rnd.nextDouble() * root.getHeight());
        PVector velocity = new PVector(rnd.nextDouble() * SPRITE_MAX_SPEED, rnd.nextDouble() * SPRITE_MAX_SPEED);
        PVector acceleration = new PVector(0, 5);

        System.out.println(location.toString());
        System.out.println(velocity.toString());

        return new SpriteBouncing(view, location, velocity, acceleration);
    }

    private void initializeTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                mainLoop();
            }
        };
        timer.start();
    }

    private void mainLoop() {
        // update bouncing sprites
        bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update());
    }

    protected void updateCurve(double x1, double y1){
        quadCurve.setControlX(x1);
        quadCurve.setControlY(y1);
    }

}
