package com.example.dancingline.motionelements;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Sprite extends Region {
    public static boolean DEBUG_ENABLED = false;
    PVector location;
    PVector velocity;
    PVector acceleration;
    double mass = Balls.SPRITE_DEFAULT_MASS;
    Node view;

    public Sprite(Node view) {
        this.view = view;
        this.location = new PVector(0, 0);
        this.velocity = new PVector(0, 0);
        this.acceleration = new PVector(0, 0);
        getChildren().add(view);
    }

    public Sprite(Node view, PVector location) {
        this.view = view;
        this.location = location;
        this.velocity = new PVector(0, 0);
        this.acceleration = new PVector(0, 0);
        getChildren().add(view);
    }

    public Sprite(Node view, PVector location, PVector velocity) {
        this.view = view;
        this.location = location;
        this.velocity = velocity;
        this.acceleration = new PVector(0, 0);
        getChildren().add(view);
    }

    public Sprite(Node view, PVector location, PVector velocity, PVector acceleration) {
        this.view = view;
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        getChildren().add(view);
    }

    public Sprite(Node view, PVector location, PVector velocity, PVector acceleration, double mass) {
        this.view = view;
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        getChildren().add(view);
    }

    public PVector getLocation() {
        return location;
    }

    public void setLocation(PVector location) {
        this.location = location;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public PVector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(PVector acceleration) {
        this.acceleration = acceleration;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public void applyImpulseForce(PVector force) {
        velocity = velocity.add(force);
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "location=" + location +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                ", mass=" + mass +
                ", view=" + view +
                '}';
    }

    public void update() {
        // update velocity and location
        velocity = velocity.add(acceleration);
        location = location.add(velocity);
        // update position on parent component
        setTranslateX(location.x);
        setTranslateY(location.y);

        if (DEBUG_ENABLED) {
            setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    public void update(PVector velocity) {
        // update velocity and location
        location = location.add(velocity);
        // update position on parent component
        setTranslateX(location.x);
        setTranslateY(location.y);
    }

    public boolean intersects(Sprite other) {
        return getBoundsInParent().intersects(other.getBoundsInParent());
    }

    public boolean contains(Point2D point) {
        return getBoundsInParent().contains(point);
    }

    public void display() {
        setTranslateX(location.x);
        setTranslateY(location.y);
    }
}