package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 15;

    @FXML
    private AnchorPane root;

    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();
    List<SpriteBouncing> wallSprites = new ArrayList<>();

    public void initialize() {
        onReset();
    }

    void onReset() {
        initializeTimer();
        initializeObjects();
    }

    private void initializeObjects() {
        root.setStyle("-fx-background-color: black;");
        bouncingSprites.clear();
        for (int i = 0; i < 50; i++) {
            bouncingSprites.add(generateBoncingSprite());
        }
        try {
            wallSprites=mapSetup.generateMap(root);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //root.getChildren().clear();
        root.getChildren().addAll(bouncingSprites);
        //root.getChildren().add(generateItem());
    }

    private SpriteBouncing generateBoncingSprite() {
        Circle view = new Circle(10);
        view.setStroke(Color.BLUEVIOLET);
        view.setFill(Color.BLUEVIOLET.deriveColor(1, 1, 1, 1));
        System.out.println(root.getHeight());
        view.setTranslateX(view.getRadius());
        view.setTranslateY(view.getRadius());

        System.out.println( root.getPrefHeight()+ " " + root.getPrefWidth());

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
        while(check == false){
            noise = rnd.nextDouble();
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            if(y<0.6){
                check = true;
            }
        }
        //System.out.println("--" + x + "." + noise + "--" + root.getWidth());
        //System.out.println("__" + y + "__" +root.getHeight());
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
            int time = 0;
            ItemsHandle call = new ItemsHandle(root);
            long lastFrameTime;
            @Override
            public void handle(long now) {
                mainLoop(time, call);
                time++;
                if(time == 501){
                    time = 0;
                }
            }
        };
        timer.start();
    }

    private void mainLoop(int time, ItemsHandle call) {
        call.updateItems(time);
        bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update(bouncingSprites));
    }
}

