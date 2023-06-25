package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import com.example.dancingline.motionelements.Sprite;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.QuadCurve;
import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

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

    public void update(QuadCurve quadCurve, List<SpriteBouncing> spriteBouncingList) {
        super.update();
        reboundWalls();
        //bounce with other balls
        for (SpriteBouncing spriteBouncing : spriteBouncingList) {
            if (spriteBouncing != this) {
                if (intersects(spriteBouncing)){
                    getVelocity().x *= -1;
                    getVelocity().y *= -1;

                }
            }
        }


        /*
        RIMBALZO SUL RETTANGOLO CHE CIRCONDA LA CURVA
        if (getBoundsInParent().intersects(quadCurve.getBoundsInParent())){
            getVelocity().y *= -1;
        }
         */



        if (containsPoint(quadCurve, getLocation().x - 10, getLocation().y - 10)){
            //getVelocity().x *= -1;
            getVelocity().y *= -1;
            System.out.println("RIMBALZO su linea  " + (getLocation().x - 10) + "  " +  (getLocation().y - 10));
        }




        /*
        ALTRE PROVE DEI RIMBALZI SULLA CURVA UTILIZZANDO IL METODO CONTAINS
        double ballX = getLocation().x - 10;
        double ballY = getLocation().y - 10;

        Point2D start = quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY());
        Point2D end = quadCurve.localToParent(quadCurve.getEndX(), quadCurve.getEndY());
        Point2D control = quadCurve.localToParent(quadCurve.getControlX(), quadCurve.getControlY());
        double t = (ballX - start.getX()) / (end.getX() - start.getX());
        double y = (1 - t) * (1 - t) * start.getY() + 2 * (1 - t) * t * control.getY() + t * t * end.getY();



            double t = (ballX - quadCurve.getStartX()) / (quadCurve.getEndX() - quadCurve.getStartX());
            double y = (1 - t) * (1 - t) * quadCurve.getStartY() + 2 * (1 - t) * t * quadCurve.getControlY() + t * t * quadCurve.getEndY();


            Transform transform = quadCurve.getLocalToSceneTransform();
            double yInGameWindow = transform.transform(new Point2D(0, y)).getY();


        System.out.println(getVelocity().y + "  " + ballY + "  " + y);
        if (ballY >= y){
            //la pallina è sotto la curva
            System.out.println("velocita' in Y: " + getVelocity().y);
            getVelocity().y *= -1;

        }
         */
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

    public boolean containsPoint(QuadCurve quadCurve, double pointX, double pointY) {
        double tolerance = 15; // Adjust the tolerance level as needed
        double toleranceX = 10;

        Point2D startCoords = quadCurve.localToParent(quadCurve.getStartX(), quadCurve.getStartY());
        Point2D controlCoords = quadCurve.localToParent(quadCurve.getControlX(), quadCurve.getControlY());
        Point2D endCoords = quadCurve.localToParent(quadCurve.getEndX(), quadCurve.getEndY());

        for (double t = 0.0; t <= 1.0; t += 0.0001) {
            double x = Math.pow(1 - t, 2) * startCoords.getX() +
                    2 * (1 - t) * t * controlCoords.getX() +
                    Math.pow(t, 2) * endCoords.getX();

            double y = Math.pow(1 - t, 2) * startCoords.getY() +
                    2 * (1 - t) * t * controlCoords.getY() +
                    Math.pow(t, 2) * endCoords.getY();

            //System.out.println("curva -> X " + x + " Y " + y);


            if (Math.abs(x - pointX) <= tolerance && Math.abs(y - pointY) <= tolerance){
                return true;
            }
            if (pointY > y && Math.abs(pointX + 10 - x) <= toleranceX) {
                getLocation().y = y - 20;
                return true;
            }
        }
        return false;
    }

}
