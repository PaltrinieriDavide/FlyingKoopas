package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 15;

    @FXML
    private AnchorPane root;

    AnimationTimer timer;
    List<SpriteBouncing> bouncingSprites = new ArrayList<>();
    List<WallSprite> wallSprites = new ArrayList<>();
    List<QuestionMarkSprite> markSprites = new ArrayList<>();

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
            generateMap();
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

    private void generateMap() throws FileNotFoundException {
        int value;
        List<SpriteBouncing> wallList = new ArrayList<>();
        Random rand = new Random();
        Image imgWall = new Image(new FileInputStream("C:\\Users\\ricca\\IdeaProjects\\dancingLine\\src\\main\\resources\\com\\example\\dancingline\\assets\\wall.png"));
        Image imgItem = new Image(new FileInputStream("C:\\Users\\ricca\\IdeaProjects\\dancingLine\\src\\main\\resources\\com\\example\\dancingline\\assets\\pngegg.png"));
        for(int j = 0; j<9; j++){
            for(int i = 0; i< 36; i++){
                value=rand.nextInt(100);
                if(value>=80){
                    ImageView item = new ImageView();
                    item.setImage(imgWall);
                    item.setFitHeight(49.8);
                    item.setFitWidth(49.8);
                    //double x = i, y=0;
                    PVector location = new PVector(i*49.8, j*49.8);
                    PVector velocity = new PVector(0, 0);
                    WallSprite wallPiece = new WallSprite(item, location, velocity);
                    //System.out.println("++++"+ wallPiece.getLocation());
                    wallPiece.setTranslateX(i*49.8);
                    wallPiece.setTranslateY(j*49.8);
                    root.getChildren().add(wallPiece);
                    wallList.add(wallPiece);
                }
                else if(value<=10){
                    ImageView item2 = new ImageView();
                    item2.setImage(imgItem);
                    item2.setFitHeight(49.8);
                    item2.setFitWidth(49.8);
                    //double x = i, y=0;
                    PVector location = new PVector(i*49.8, j*49.8);
                    PVector velocity = new PVector(0, 0);

                    QuestionMarkSprite questionMark = new QuestionMarkSprite(rand.nextInt(3)+1, item2, location, velocity);

                    System.out.println("222  " + questionMark.getType());

                    questionMark.setTranslateX(i*49.8);
                    questionMark.setTranslateY(j*49.8);
                    root.getChildren().add(questionMark);
                    markSprites.add(questionMark);
                }

            }
        }

    }

  /*  private Sprite generateItem(){
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
    }*/

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
        //call.updateItems(time, wallSprites);
        bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update(bouncingSprites));
    }
}

