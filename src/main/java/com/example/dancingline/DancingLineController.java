package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 15;
    @FXML
    private QuadCurve quadCurve;

    @FXML
    private AnchorPane root;

    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();
    private AudioAnalyzer audioAnalyzer;



    public void initialize() {
        root.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // The anchor panel has been closed
                audioAnalyzer.interrupt();
            }
        });
        onReset();
    }

    void onReset() {

        initializeTimer();
        initializeObjects();
    }

    private void initializeObjects() {
        root.setStyle("-fx-background-color: black;");

        quadCurve.endXProperty().bind(root.widthProperty().multiply(1.0));
        quadCurve.startXProperty().bind(root.widthProperty().multiply(0.0));

        quadCurve.startYProperty().bind(root.heightProperty().multiply(0.3));
        quadCurve.endYProperty().bind(root.heightProperty().multiply(0.3));

        bouncingSprites.clear();
        for (int i = 0; i < 10; i++) {
            bouncingSprites.add(generateBoncingSprite());
        }
        //root.getChildren().clear();
        root.getChildren().addAll(bouncingSprites);
        //root.getChildren().add(generateItem());

        ExecutorService executorService = Executors.newFixedThreadPool(4); //ricordiamoci di definire correttamente il numero di thread
        audioAnalyzer = new AudioAnalyzer();
        executorService.submit(audioAnalyzer);
    }

    private SpriteBouncing generateBoncingSprite() {
        Circle view = new Circle(10);
        view.setStroke(Color.BLUEVIOLET);
        view.setFill(Color.BLUEVIOLET.deriveColor(1, 1, 1, 1));

        view.setTranslateX(view.getRadius());
        view.setTranslateY(view.getRadius());

        //System.out.println( root.getPrefHeight()+ " " + root.getPrefWidth());

        Random rand = new Random();

        //RandomGenerator rnd = RandomGenerator.getDefault();
        PVector location = new PVector(rand.nextDouble(root.getPrefWidth()), rand.nextDouble(root.getPrefHeight()));
        PVector velocity = new PVector(rand.nextDouble() * SPRITE_MAX_SPEED / 2, rand.nextDouble() * SPRITE_MAX_SPEED);
        PVector acceleration = new PVector(0, 0.05);

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
        while(!check){
            noise = rnd.nextDouble();
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            if(y<0.6){
                check = true;
            }
        }
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
            long delta;
            long lastFrameTime;
            @Override
            public void handle(long now) {
                mainLoop();
            }
        };
        timer.start();
    }

    private void mainLoop() {

        /*
        if (quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY()).getY() > root.getHeight()) {
            //quadCurve.setStartY(quadCurve.getStartY()-(quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY()).getY()-root.getHeight()));
        }
        if (quadCurve.getEndY() > root.getHeight()) {
            System.out.println("yuppi");
        }
         */
        bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update(quadCurve, bouncingSprites));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("volume: " + audioAnalyzer.getVolume());
        if (audioAnalyzer.getVolume() > 80){
            if (quadCurve.getControlY() < 200){
                updateCurve(quadCurve.getControlX(), quadCurve.getControlY() - 20);
            }

        }

    }
    private void updateCurve(double x1, double y1){
        quadCurve.setControlX(x1);
        quadCurve.setControlY(y1);
    }

    private void displayCurve(){
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
    }
}

