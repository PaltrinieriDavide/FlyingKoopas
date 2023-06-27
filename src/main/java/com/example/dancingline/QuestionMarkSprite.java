package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import javafx.scene.Node;

public class QuestionMarkSprite extends SpriteBouncing{
    public int type;
    public QuestionMarkSprite(int type, Node view) {
        super(view);
        this.type=type;
    }

    public QuestionMarkSprite(int type, Node view, PVector location) {
        super(view, location);
        this.type=type;
    }

    public QuestionMarkSprite(int type, Node view, PVector location, PVector velocity) {
        super(view, location, velocity);
        this.type=type;
    }

    public QuestionMarkSprite(int type, Node view, PVector location, PVector velocity, PVector acceleration) {
        super(view, location, velocity, acceleration);
        this.type=type;
    }

    public QuestionMarkSprite(int type, Node view, PVector location, PVector velocity, PVector acceleration, double mass) {
        super(view, location, velocity, acceleration, mass);
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
