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

    @FXML
    private Button prova;

    @FXML
    private Circle circle;


    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();

    void onReset() {
        initializeObjects();
        initializeTimer();
    }

    private void initializeObjects() {
        bouncingSprites.clear();
        for (int i = 0; i < 20; i++) {
            bouncingSprites.add(generateBoncingSprite());
        }
        root.getChildren().clear();
        root.getChildren().addAll(bouncingSprites);
    }

    private SpriteBouncing generateBoncingSprite() {
        Rectangle view = new Rectangle(100, 100);
        view.setStroke(Color.ORANGE);
        view.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.3));

        RandomGenerator rnd = RandomGenerator.getDefault();
        PVector location = new PVector(rnd.nextDouble() * root.getWidth(), rnd.nextDouble() * root.getHeight());
        PVector velocity = new PVector(rnd.nextDouble() * SPRITE_MAX_SPEED, rnd.nextDouble() * SPRITE_MAX_SPEED);
        return new SpriteBouncing(view, location, velocity);
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
        bouncingSprites.forEach(SpriteBouncing::update);
    }

    protected void updateCurve(double x1, double y1){
        quadCurve.setControlX(x1);
        quadCurve.setControlY(y1);
    }

}
