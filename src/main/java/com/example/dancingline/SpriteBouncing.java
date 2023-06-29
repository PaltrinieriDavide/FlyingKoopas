package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.scene.Node;

import java.util.List;


public class SpriteBouncing extends Sprite {

    public SpriteBouncing(Node view) {
        super(view);
    }

    public SpriteBouncing(Node view, PVector location) {
        super(view, location);
    }

    public SpriteBouncing(Node view, PVector location, PVector velocity) {
        super(view, location, velocity);
    }

    public SpriteBouncing(Node view, PVector location, PVector velocity, PVector acceleration) {
        super(view, location, velocity, acceleration);
    }

    public SpriteBouncing(Node view, PVector location, PVector velocity, PVector acceleration, double mass) {
        super(view, location, velocity, acceleration, mass);
    }

    public void update() {
        super.update();
        reboundWalls();
    }

    public void reboundWalls() {
        double offset;

        // right wall
        offset = getBoundsInParent().getMaxX() - getParent().getLayoutBounds().getMaxX();
        if (offset > 0) {
            getLocation().x -= offset;
            getVelocity().x *= -1;
        }

        // left wall
        offset = getBoundsInParent().getMinX() - getParent().getLayoutBounds().getMinX();
        if (offset < 0) {
            getLocation().x -= offset;
            getVelocity().x *= -1;
        }

        // lower wall
        offset = getBoundsInParent().getMaxY() - getParent().getLayoutBounds().getMaxY();
        if (offset > 0) {
            getLocation().y -= offset;
            getVelocity().y *= -1;
        }

        // upper wall
        offset = getBoundsInParent().getMinY() - getParent().getLayoutBounds().getMinY();
        if (offset < 0) {
            getLocation().y -= offset;
            getVelocity().y *= -1;
        }
    }

}
