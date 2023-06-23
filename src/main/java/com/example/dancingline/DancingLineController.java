package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Material;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.shape.QuadCurve;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 7;
    @FXML
    private QuadCurve quadCurve;

    @FXML
    private AnchorPane root;

    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();


    public void initialize() {
        root.setStyle("-fx-background-color: black;");
        //root.prefHeightProperty().bind()
        //quadCurve.setStartY(root.getLayoutBounds().getMaxY());
        //quadCurve.setEndY(root.getLayoutBounds().getMaxY());
        quadCurve.endXProperty().bind(root.widthProperty().multiply(1.0));
        quadCurve.startXProperty().bind(root.widthProperty().multiply(0.0));
        System.out.println(root.getPrefHeight());
        //quadCurve.setStartY(root.getHeight()-(root.getHeight())*0.5);
        //quadCurve.setEndY(root.getHeight()-(root.getHeight())*0.5);

        quadCurve.startYProperty().bind(root.heightProperty().multiply(0.3));
        quadCurve.endYProperty().bind(root.heightProperty().multiply(0.3));



        Point2D startCoords = quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY());
        Point2D controlCoords = quadCurve.localToParent(quadCurve.getControlX(), quadCurve.getControlY());
        Point2D endCoords = quadCurve.localToParent(quadCurve.getEndX(), quadCurve.getEndY());

        for (double t = 0.0; t <= 1.0; t += 0.0001) {
            double x = Math.pow(1 - t, 2) * startCoords.getX() +
                    2 * (1 - t) * t * controlCoords.getX() +
                    Math.pow(t, 2) * endCoords.getX();

            double y = Math.pow(1 - t, 2) * startCoords.getY() +
                    2 * (1 - t) * t * controlCoords.getY() +
                    Math.pow(t, 2) * endCoords.getY();

            System.out.println("curva -> X " + x + " Y " + y);

        }


        onReset();
    }


    void onReset() {
        initializeTimer();
        initializeObjects();
    }

    private void initializeObjects() {
        bouncingSprites.clear();
        for (int i = 0; i < 1; i++) {
            bouncingSprites.add(generateBoncingSprite());
        }
        //root.getChildren().clear();
        root.getChildren().addAll(bouncingSprites);
        root.getChildren().add(generateItem());
    }

    private SpriteBouncing generateBoncingSprite() {
        Circle view = new Circle(10);
        view.setStroke(Color.BLUEVIOLET);
        view.setFill(Color.BLUEVIOLET.deriveColor(1, 1, 1, 1));
        System.out.println(root.getHeight());
        view.setTranslateX(100);
        view.setTranslateY(100);

        RandomGenerator rnd = RandomGenerator.getDefault();
        PVector location = new PVector(100, 100);
        PVector velocity = new PVector(rnd.nextDouble() * SPRITE_MAX_SPEED, rnd.nextDouble() * SPRITE_MAX_SPEED);
        PVector acceleration = new PVector(0, 5);



        return new SpriteBouncing(view, location, velocity, acceleration);
    }

    private Sprite generateItem(){
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/item.png")));
        ImageView item = new ImageView();
        item.setImage(img);
        item.setFitHeight(60);
        item.setFitWidth(60);
        double x = 0, y=0, noise=0;
        boolean check = false;
        RandomGenerator rnd = RandomGenerator.getDefault();
        while(check == false){
            noise = rnd.nextDouble();
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            if(y<0.6){
                check = true;
            }
        }
        System.out.println("--" + x + "." + noise + "--" + root.getWidth());
        System.out.println("__" + y + "__" +root.getHeight());
        item.setX((x+noise)*1000);
        item.setY(y*1000);
        PVector location = new PVector(rnd.nextDouble() * root.getPrefWidth(), rnd.nextDouble() * root.getPrefHeight());
        PVector velocity = new PVector(0, 0);
        return new Sprite(item, location, velocity);
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
        if (quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY()).getY() > root.getHeight()) {
            //quadCurve.setStartY(quadCurve.getStartY()-(quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY()).getY()-root.getHeight()));
        }
        if (quadCurve.getEndY() > root.getHeight()) {
            System.out.println("yuppi");
        }

        //quadCurve.setStartY(1);
        //quadCurve.setEndY(root.getPrefHeight()-(root.getPrefHeight())*0.5);
        // update bouncing sprites
        //System.out.println("--" + root.getHeight() + "    " + quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY()));
        //System.out.println(root.getHeight()+ "    " + quadCurve.getEndY());

        bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update(quadCurve, bouncingSprites));

    }
    protected void updateCurve(double x1, double y1){
        quadCurve.setControlX(x1);
        quadCurve.setControlY(y1);
    }

}

