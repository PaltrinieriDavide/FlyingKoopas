package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.UtilsColor;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.FileNotFoundException;
import java.util.*;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 15;
    @FXML
    private Line line;
    @FXML
    private Pane root;
    Line force;
    AnimationTimer timer;
    List<Koopa> koopasDisplayed = new ArrayList<>();
    List<WallSprite> wallSprites = new ArrayList<>();
    List<QuestionMarkSprite> markSprites = new ArrayList<>();
    Deque<Koopa> koopasNotDisplayed = new ArrayDeque<>();

    public void initialize() {
        onReset();
    }

    void onReset() {

        /*
        root.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double width = newWidth.doubleValue();
            System.out.println("Larghezza pannello root: " + rootWidth);
            rootWidth = width;
        });

        root.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double height = newHeight.doubleValue();
            System.out.println("Altezza pannello root: " + rootHeight);
            rootHeight = height;
        });
         */

        initializeTimer();
        initializeObjects();
    }

    private void initializeObjects() {
        try {
            generateMap();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        koopasDisplayed.clear();

        System.out.println("ROOT: H " + root.getPrefHeight());

        line.setStartX(line.parentToLocal(0, root.getPrefHeight() * 0.95).getX());
        line.setStartY(line.parentToLocal(0, root.getPrefHeight() * 0.95).getY());
        line.setEndX(line.parentToLocal(root.getPrefWidth(), root.getPrefHeight() * 0.95).getX());
        line.setEndY(line.parentToLocal(root.getPrefWidth(), root.getPrefHeight() * 0.95).getY());


        for (int i = 0; i < 1; i++) {
            Koopa k = generateKoopa(root.getPrefWidth() / 2, line.localToParent(line.getStartX(), line.getStartY()).getY() - 60, "green");
            //koopasDisplayed.add(k);
            koopasNotDisplayed.add(k);
        }
        //root.getChildren().addAll(koopasDisplayed);
    }

    private Koopa generateKoopa(double x, double y, String type) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopaverde.png")));
        ImageView item = generateItemKoopa(type);

        Koopa koopa = new Koopa(item, "green");

        //sb.setLocation(new PVector(sb.parentToLocal(x, y).getX(), sb.parentToLocal(x, y).getY()));
        System.out.println(koopa.parentToLocal(x, y).getX() + " " + koopa.parentToLocal(x, y).getY());
        System.out.println(x + " " + y);

        koopa.setLocation(new PVector(x,y));
        koopa.setVelocity(new PVector(0,0));
        koopa.setAcceleration(new PVector(0,0));

        PVector location = new PVector(x, y);
        PVector velocity = new PVector(0,0);
        PVector acceleration = new PVector(0, 0);

        return koopa;

    }

    private void initializeTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            long delta;
            int time = 0;
            long lastFrameTime;
            @Override
            public void handle(long now) {
                time++;
                if(time == 501){
                    time = 0;
                }
                mainLoop();
            }
        };
        timer.start();
    }
    private void mainLoop() {
        for (int i = 0; i < koopasDisplayed.size(); i++){

            koopasDisplayed.get(i).update(koopasDisplayed);

            for (int j = 0; j < Math.max(wallSprites.size(), markSprites.size()); j++){
                if (j < wallSprites.size() && koopasDisplayed.get(i).getBoundsInParent().intersects(wallSprites.get(j).getBoundsInParent())){
                    koopasDisplayed.get(i).setVelocity( new PVector(
                            koopasDisplayed.get(i).getVelocity().x,
                            - koopasDisplayed.get(i).getVelocity().y)
                    );
                    root.getChildren().remove(wallSprites.get(j));
                    wallSprites.remove(j);
                    break;
                }
                else if(j < markSprites.size() && koopasDisplayed.get(i).getBoundsInParent().intersects(markSprites.get(j).getBoundsInParent())){
                    PVector vel = new PVector(koopasDisplayed.get(i).getVelocity().x, koopasDisplayed.get(i).getVelocity().y);
                    koopasDisplayed.get(i).setVelocity( new PVector(
                            koopasDisplayed.get(i).getVelocity().x,
                            - koopasDisplayed.get(i).getVelocity().y)
                    );
                    Koopa n = new Koopa(generateItemKoopa("green"), koopasDisplayed.get(i).getLocation(), vel, koopasDisplayed.get(i).getAcceleration(), koopasDisplayed.get(i).getType());
                    //Koopa nuovo = bouncingSprites.get(i);
                    //nuovo.setVelocity(new PVector(- nuovo.getVelocity().x, nuovo.getVelocity().y));
                    koopasDisplayed.add(n);
                    root.getChildren().add(n);
                    root.getChildren().remove(markSprites.get(j));
                    markSprites.remove(j);
                }
            }

            if (koopasDisplayed.get(i).getBoundsInParent().intersects(line.getBoundsInParent())){
                koopasNotDisplayed.addFirst(koopasDisplayed.get(i));
                root.getChildren().remove(koopasDisplayed.get(i));
                koopasDisplayed.remove(i);
            }

        }
        //bouncingSprites.forEach(spriteBouncing -> spriteBouncing.update(bouncingSprites));
    }
    @FXML
    void onMouseDragged(MouseEvent event) {
        force.setEndX(event.getX());
        force.setEndY(event.getY());
        double magnitude = Math.hypot(force.getStartX() - force.getEndX(), force.getStartY() - force.getEndY());
        force.setStroke(UtilsColor.getColorScale(0, 300, Color.GREEN.getHue(), Color.RED.getHue(), magnitude).deriveColor(1, 1, 1, 0.3));
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = event.getX();
        double endY = event.getY();

        //double length = (new Point2D(startX, startY)).distance(endX, endY);
        System.out.println("Lista " + koopasDisplayed.size());
        System.out.println("prima rimozione " + koopasNotDisplayed.size());

        if (!koopasNotDisplayed.isEmpty()){
            Koopa k = koopasNotDisplayed.removeLast();
            k.setLocation(new PVector(root.getPrefWidth() / 2, line.localToParent(line.getStartX(), line.getStartY()).getY() - 60));
            k.setVelocity(new PVector(0,0));
            koopasDisplayed.add(k);
            root.getChildren().add(k);
            System.out.println("dopo rimozione " + koopasNotDisplayed.size());
            force = new Line(root.getPrefWidth() / 2, line.localToParent(line.getStartX(), line.getStartY()).getY() - 60, event.getX(), event.getY());
            force.setStrokeWidth(5);
            root.getChildren().add(force);
        }
    }

    @FXML
    void onMouseReleased(MouseEvent event) {

        //bs.getBoundsInParent().intersects(line.getBoundsInParent())

        Optional<Koopa> sprite = koopasDisplayed.stream()
                .filter(bs -> bs.getBoundsInParent().contains(line.localToParent((line.getStartX() + line.getEndX()) / 2, line.getStartY() - 60)))
                .findFirst();
        if (sprite.isPresent()) {
            System.out.println("sprite trovata in onMouseReleased");
            PVector impulse = new PVector(
                    force.getEndX() - force.getStartX(),
                    force.getEndY() - force.getStartY());
            sprite.get().applyImpulseForce(impulse.multiply(0.009));
        }
        root.getChildren().removeAll(force);
    }

    private void generateMap() throws FileNotFoundException {
        int value;
        Random rand = new Random();
        //Image imgWall = new Image(new FileInputStream("C:\\Users\\ricca\\IdeaProjects\\dancingLine\\src\\main\\resources\\com\\example\\dancingline\\assets\\wall.png"));
        //Image imgItem = new Image(new FileInputStream("C:\\Users\\ricca\\IdeaProjects\\dancingLine\\src\\main\\resources\\com\\example\\dancingline\\assets\\questionMark.png"));
        Image imgWall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/wall.png")));
        Image imgItem = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/questionMark.png")));

        for(int j = 0; j<9; j++){
            for(int i = 0; i< 36; i++){
                value=rand.nextInt(100);
                if(value>=60){
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
                    wallSprites.add(wallPiece);
                }
                else if(value<=3){
                    ImageView item2 = new ImageView();
                    item2.setImage(imgItem);
                    item2.setFitHeight(49.8);
                    item2.setFitWidth(49.8);
                    //double x = i, y=0;
                    PVector location = new PVector(i*49.8, j*49.8);
                    PVector velocity = new PVector(0, 0);

                    QuestionMarkSprite questionMark = new QuestionMarkSprite(rand.nextInt(3)+1, item2, location, velocity);

                    //System.out.println("222  " + questionMark.getType());

                    questionMark.setTranslateX(i*49.8);
                    questionMark.setTranslateY(j*49.8);
                    root.getChildren().add(questionMark);
                    markSprites.add(questionMark);
                }

            }
        }
    }

    public ImageView generateItemKoopa(String type){
        Image img;
        switch(type){
            case "green":
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopaverde.png")));
                break;
            case "red":
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/kooparosso.png")));
                break;
            case "blue":
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopablue.png")));
            default:
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopaverde.png")));
        }

        ImageView item = new ImageView();
        item.setImage(img);
        item.setFitHeight(30);
        item.setFitWidth(30);

        item.setTranslateY(- 15);
        item.setTranslateX(- 15);
        return item;
    }
}

