package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

public class ItemsHandle {
    private AnchorPane root;
    Deque<Sprite> priorityDeque = new ArrayDeque<>();
    int itemNumber = 5;

    public ItemsHandle(AnchorPane root) {
        this.root = root;
    }

    public Sprite generateItem(){
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/pngegg.png")));
        ImageView item = new ImageView();
        item.setImage(img);
        item.setFitHeight(50);
        item.setFitWidth(50);
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

    public void updateItems(int time){
        Random rand = new Random();
        double value = rand.nextDouble(1);
        // System.out.println("++++" + value);
        System.out.println("**** " + priorityDeque.size());
        if (value >0.995){
            Sprite a = generateItem();
            priorityDeque.addFirst(a);
            root.getChildren().add(a);
        }
        if(time == 500 && priorityDeque.size()>0) {
            root.getChildren().remove(priorityDeque.peekLast());
            Sprite es = priorityDeque.removeLast();

        }

        else if(priorityDeque.size() >= 6){
            root.getChildren().remove(priorityDeque.peekLast());
            priorityDeque.removeLast();
        }
        System.out.println("time: " + time);
    }


}
