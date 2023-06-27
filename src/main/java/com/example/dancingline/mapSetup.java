package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

public class mapSetup {
    public static List<SpriteBouncing> generateMap(AnchorPane root) throws FileNotFoundException {
        List<SpriteBouncing> wallList = new ArrayList<>();
        Random rand = new Random();
        Image img = new Image(new FileInputStream("C:\\Users\\ricca\\IdeaProjects\\dancingLine\\src\\main\\resources\\com\\example\\dancingline\\assets\\wall.png"));
        for(int j = 0; j<9; j++){
            for(int i = 0; i< 36; i++){
                if(rand.nextInt(100)>=50){
                    ImageView item = new ImageView();
                    item.setImage(img);
                    item.setFitHeight(49.8);
                    item.setFitWidth(49.8);
                    //double x = i, y=0;
                    PVector location = new PVector(i*49.8, j*49.8);
                    PVector velocity = new PVector(0, 0);
                    SpriteBouncing wallPiece = new SpriteBouncing(item, location, velocity);
                    //System.out.println("++++"+ wallPiece.getLocation());
                    wallPiece.setTranslateX(i*49.8);
                    wallPiece.setTranslateY(j*49.8);
                    root.getChildren().add(wallPiece);
                    wallList.add(wallPiece);
                }

            }
        }


        return wallList;
    }
}
