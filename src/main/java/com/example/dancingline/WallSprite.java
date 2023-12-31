package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import javafx.scene.Node;

public class WallSprite extends SpriteBouncing{
    int bumpsNumber=3;
    public WallSprite(Node view) {
        super(view);
    }

    public WallSprite(Node view, PVector location) {
        super(view, location);
    }

    public WallSprite(Node view, PVector location, PVector velocity) {
        super(view, location, velocity);
    }

    public WallSprite(Node view, PVector location, PVector velocity, PVector acceleration) {
        super(view, location, velocity, acceleration);
    }

    public WallSprite(Node view, PVector location, PVector velocity, PVector acceleration, double mass) {
        super(view, location, velocity, acceleration, mass);
    }

    public int getBumpsNumber() {
        return bumpsNumber;
    }

    public void setBumpsNumber(int bumpsNumber) {
        this.bumpsNumber = bumpsNumber;
    }

    public void decreseBumpsNumber(){
        bumpsNumber--;
    }
}
