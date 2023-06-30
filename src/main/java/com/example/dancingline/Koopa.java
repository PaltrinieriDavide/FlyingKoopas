package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import javafx.scene.Node;

import java.util.List;

public class Koopa extends SpriteBouncing {

    private String type;
    private boolean launched;

    public Koopa(Node view, String type) {
        super(view);
        this.type = type;
        this.launched = false;
    }

    public Koopa(Node view, PVector location, PVector velocity, PVector acceleration, String type) {
        super(view, location, velocity, acceleration);
        this.type = type;
        this.launched = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public void update(List<Koopa> list) {
        super.update();

        //bounce with other
        /*

         */
        for (Koopa koopa : list) {
            if (koopa != this) {
                if (intersects(koopa)) {
                    getVelocity().x *= -1;
                }
            }
        }
    }
}