package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.UtilsColor;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;


public class DancingLineController {

    public static double SPRITE_MAX_SPEED = 15;
    @FXML
    private Line line;
    @FXML
    private Pane root;
    @FXML
    private Text textGreen;
    @FXML
    private Text textBlue;
    @FXML
    private Text textRed;
    @FXML
    private ImageView koopaBlueImg;
    @FXML
    private ImageView koopaGreenImg;
    @FXML
    private ImageView koopaRedImg;
    private Button winButton;
    @FXML
    private Text textWin;
    Line force;
    AnimationTimer timer;
    List<Koopa> koopasDisplayed = new ArrayList<>();
    List<WallSprite> evilWallSprites = new ArrayList<>();
    List<WallSprite> wallSprites = new ArrayList<>();
    List<QuestionMarkSprite> markSprites = new ArrayList<>();
    Deque<Koopa> koopasNotDisplayed = new ArrayDeque<>();
    public void initialize() {
        onReset();
    }
    void onReset() {
        initializeTimer();
        initializeObjects();
    }
    private void initializeObjects() {

        InputStream is = getClass().getResourceAsStream("font/SuperMario256.ttf");
        Font youWinFont = Font.loadFont(is, 200);
        textWin = new Text("YOU WIN");
        textWin.setFont(youWinFont);
        textWin.setFill(Color.WHITE);
        textWin.setX(root.getPrefWidth()*0.16);
        textWin.setY(root.getPrefHeight()*0.40);
        root.getChildren().add(textWin);
        textWin.setVisible(false);

        winButton = new Button("RESTART GAME");
        winButton.setStyle("-fx-background-color: #FDF5E6;\n" +
                "    -fx-background-radius: 50;\n" +
                "    -fx-padding: 8px 16px;\n" +
                "    -fx-border-radius: 50px;\n" +
                "    -fx-border-color: #E4D8C5 ;\n" +
                "    -fx-border-width: 1px;" +
                "-fx-pref-width: 225px;" +
                " -fx-pref-height: 75px;" +
                "-fx-font-size: 24px;");


        winButton.setOnMouseEntered(event -> {
            winButton.setStyle("-fx-background-color: #e5cc9e;\n" +
                    "    -fx-background-radius: 50;\n" +
                    "    -fx-padding: 8px 16px;\n" +
                    "    -fx-border-radius: 50px;\n" +
                    "    -fx-border-color: #d0ad67 ;\n" +
                    "    -fx-border-width: 1px;" +
                    "-fx-pref-width: 225px;" +
                    " -fx-pref-height: 75px;" +
                    "-fx-font-size: 24px;");
        });
        winButton.setOnMouseExited(event -> {
            winButton.setStyle("-fx-background-color: #FDF5E6;\n" +
                    "    -fx-background-radius: 50;\n" +
                    "    -fx-padding: 8px 16px;\n" +
                    "    -fx-border-radius: 50px;\n" +
                    "    -fx-border-color: #E4D8C5 ;\n" +
                    "    -fx-border-width: 1px;" +
                    "-fx-pref-width: 225px;" +
                    " -fx-pref-height: 75px;" +
                    "-fx-font-size: 24px;");
        });

        winButton.setVisible(false);
        winButton.setTranslateX(root.getPrefWidth()*0.42);
        winButton.setTranslateY(root.getPrefHeight()*0.60);
        root.getChildren().add(winButton);
        winButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //root.getChildren().clear();
                root.getChildren().remove(winButton);
                root.getChildren().remove(textWin);
                root.getChildren().remove(koopaGreenImg);
                root.getChildren().remove(koopaRedImg);
                root.getChildren().remove(koopaBlueImg);
                root.getChildren().remove(textGreen);
                root.getChildren().remove(textRed);
                root.getChildren().remove(textBlue);

                wallSprites.clear();
                markSprites.clear();
                root.getChildren().removeAll(koopasDisplayed);
                koopasDisplayed.clear();
                koopasNotDisplayed.clear();
                onReset();
            }
        });

        koopaGreenImg = generateItemKoopa("green");
        koopaRedImg = generateItemKoopa("red");
        koopaBlueImg = generateItemKoopa("blue");

        koopaGreenImg.setX(30);
        koopaRedImg.setX(30);
        koopaBlueImg.setX(30);

        koopaGreenImg.setY(root.getPrefHeight() * 0.85);
        koopaRedImg.setY(root.getPrefHeight() * 0.90);
        koopaBlueImg.setY(root.getPrefHeight() * 0.95);

        koopaGreenImg.setTranslateY(-20);
        koopaRedImg.setTranslateY(-20);
        koopaBlueImg.setTranslateY(-20);

        root.getChildren().add(koopaGreenImg);
        root.getChildren().add(koopaRedImg);
        root.getChildren().add(koopaBlueImg);

        textGreen = new Text("");
        textRed = new Text("");
        textBlue = new Text("");

        textGreen.setFont(new Font(20));
        textGreen.setFill(Color.WHITE);

        textRed.setFont(new Font(20));
        textRed.setFill(Color.WHITE);

        textBlue.setFont(new Font(20));
        textBlue.setFill(Color.WHITE);

        textGreen.setX(60);
        textRed.setX(60);
        textBlue.setX(60);

        textGreen.setY(root.getPrefHeight() * 0.85);
        textRed.setY(root.getPrefHeight() * 0.90);
        textBlue.setY(root.getPrefHeight() * 0.95);

        textGreen.setText("0");
        textRed.setText("0");
        textBlue.setText("0");

        root.getChildren().add(textGreen);
        root.getChildren().add(textRed);
        root.getChildren().add(textBlue);

        try {
            generateMap();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        koopasDisplayed.clear();

        //System.out.println("ROOT: H " + root.getPrefHeight());

        line.setStartX(line.parentToLocal(0, root.getPrefHeight() * 0.95).getX());
        line.setStartY(line.parentToLocal(0, root.getPrefHeight() * 0.95).getY());
        line.setEndX(line.parentToLocal(root.getPrefWidth(), root.getPrefHeight() * 0.95).getX());
        line.setEndY(line.parentToLocal(root.getPrefWidth(), root.getPrefHeight() * 0.95).getY());

        for (int i = 0; i < 1; i++) {
            Koopa k = generateKoopa(0,0, "green");
            //koopasDisplayed.add(k);
            koopasNotDisplayed.add(k);
        }
        //root.getChildren().addAll(koopasDisplayed);
    }
    private Koopa generateKoopa(double x, double y, String type) {
        ImageView item = generateItemKoopa(type);

        Koopa koopa = new Koopa(item, type);

        SpriteBouncing.DEBUG_ENABLED = true;

        koopa.setLocation(new PVector(x,y));
        koopa.setVelocity(new PVector(0,0));
        koopa.setAcceleration(new PVector(0,0));

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
        for(WallSprite ws : evilWallSprites){
            ws.update();
        }

        for (int i = 0; i < koopasDisplayed.size(); i++){

            SpriteBouncing item = koopasDisplayed.get(i).update(koopasDisplayed, wallSprites, markSprites, evilWallSprites);
            if (item instanceof WallSprite){
                root.getChildren().remove(item);
            } else if (item instanceof QuestionMarkSprite){
                PVector vel = new PVector(koopasDisplayed.get(i).getVelocity().x, koopasDisplayed.get(i).getVelocity().y);
                Koopa n;
                Random rand = new Random();
                n = switch (rand.nextInt(3)) {
                    case 0 ->
                            new Koopa(generateItemKoopa("green"), koopasDisplayed.get(i).getLocation(), vel, koopasDisplayed.get(i).getAcceleration(), "green");
                        case 1 ->
                                new Koopa(generateItemKoopa("red"), koopasDisplayed.get(i).getLocation(), vel, koopasDisplayed.get(i).getAcceleration(), "red");
                    case 2 ->
                            new Koopa(generateItemKoopa("blue"), koopasDisplayed.get(i).getLocation(), vel.multiply(1.2), koopasDisplayed.get(i).getAcceleration(), "blue");
                    default ->
                            new Koopa(generateItemKoopa("green"), koopasDisplayed.get(i).getLocation(), vel, koopasDisplayed.get(i).getAcceleration(), "green");
                };

                koopasDisplayed.add(n);
                root.getChildren().add(n);
                root.getChildren().remove(item);
            }

            if (koopasDisplayed.get(i).getBoundsInParent().intersects(line.getBoundsInParent()) ||
                    line.localToParent(line.getStartX(), line.getStartY()).getY() < koopasDisplayed.get(i).getLocation().y){
                koopasNotDisplayed.addFirst(koopasDisplayed.get(i));
                root.getChildren().remove(koopasDisplayed.get(i));
                koopasDisplayed.remove(i);
            }
        }

        if (wallSprites.isEmpty() && markSprites.isEmpty()){
            textWin.setVisible(true);
            winButton.setVisible(true);
            root.getChildren().removeAll(evilWallSprites);
            evilWallSprites.clear();
        }
        updateKoopasCounter();
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
        if (!koopasNotDisplayed.isEmpty()){
            Koopa k = koopasNotDisplayed.removeLast();
            k.setLocation(new PVector((root.getPrefWidth() / 2) - Koopa.getRADIUS(), line.localToParent(line.getStartX(), line.getStartY()).getY() - 60 - (Koopa.getRADIUS() / 2)));
            k.setVelocity(new PVector(0,0));
            koopasDisplayed.add(k);
            root.getChildren().add(k);
            //System.out.println("dopo rimozione " + koopasNotDisplayed.size());
            force = new Line((root.getPrefWidth() / 2)  - Koopa.getRADIUS() / 2, line.localToParent(line.getStartX(), line.getStartY()).getY() - 60, event.getX(), event.getY());            force.setStrokeWidth(5);
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
            double distance = (new Point2D(force.getStartX(), force.getStartY())).distance(new Point2D(force.getEndX(), force.getEndY()));
            System.out.println("distance: " + distance);
            if (distance > 60){
                if(sprite.get().getType().equals("blue")){
                    sprite.get().applyImpulseForce(impulse.multiply(0.04));
                }else{
                    sprite.get().applyImpulseForce(impulse.multiply(0.015));
                }
            }else{
                koopasDisplayed.remove(sprite);
                //koopasNotDisplayed.addFirst(sprite);
                root.getChildren().remove(sprite);
            }
        }
        root.getChildren().removeAll(force);
    }

    private void generateMap() throws FileNotFoundException {
        int value;
        Random rand = new Random();
        int genRowOne = rand.nextInt(12), genRowTwo = rand.nextInt(12);

        Image imgWall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/wall.png")));
        Image imgItem = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/questionMark.png")));
        Image evilWall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/evilwall.png")));

        for(int j = 0; j<12; j++){
            int genColOne = rand.nextInt(30), genColTwo = rand.nextInt(28);
            for(int i = 0; i< 28; i++){
                value=rand.nextInt(100);
                if(j == genRowOne || j == genRowTwo){
                    if(i == genColOne || i == genColTwo) {
                        ImageView item = new ImageView();
                        item.setImage(evilWall);
                        item.setFitHeight(50);
                        item.setFitWidth(50);
                        //double x = i, y=0;
                        PVector location = new PVector(i * 49.8, j * 49.8);
                        PVector velocity = new PVector(1, 0);
                        WallSprite notDestructible = new WallSprite(item, location, velocity);
                        notDestructible.setTranslateX(i*49.8);
                        notDestructible.setTranslateY(j*49.8);
                        root.getChildren().add(notDestructible);
                        evilWallSprites.add(notDestructible);

                    }
                    continue;
                }
                if(value>=90){
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
        Image img = switch (type) {
            case "green" -> new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopaverde.png")));
            case "red" -> new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/kooparosso.png")));
            case "blue" -> new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopablu.png")));
            default -> new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/koopeverde.png")));
        };

        ImageView item = new ImageView();
        item.setImage(img);
        item.setFitHeight(Koopa.getRADIUS());
        item.setFitWidth(Koopa.getRADIUS());

        return item;
    }

    public void updateKoopasCounter(){
        textGreen.setText(String.format("%d", koopasNotDisplayed.stream().filter(k -> Objects.equals(k.getType(), "green")).count()));
        textRed.setText(String.format("%d", koopasNotDisplayed.stream().filter(k -> Objects.equals(k.getType(), "red")).count()));
        textBlue.setText(String.format("%d", koopasNotDisplayed.stream().filter(k -> Objects.equals(k.getType(), "blue")).count()));

            }
}

