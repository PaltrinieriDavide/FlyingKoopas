package com.example.dancingline;

import com.example.dancingline.motionelements.PVector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import java.util.List;

public class Koopa extends SpriteBouncing {

    private String type;
    private static final double RADIUS = 30;

    public Koopa(Node view, String type) {
        super(view);
        this.type = type;
    }

    public Koopa(Node view, PVector location, PVector velocity, PVector acceleration, String type) {
        super(view, location, velocity, acceleration);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static double getRADIUS() {
        return RADIUS;
    }

    public SpriteBouncing update(List<Koopa> koopasList, List<WallSprite> wallList, List<QuestionMarkSprite> questionMarkList, List<WallSprite> evilWallSprites) {
        super.update();
        //koopaBounceKoopas(koopasList);

        return koopaBounceWallsQuestionMark(wallList, questionMarkList, evilWallSprites);
    }

    public void koopaBounceKoopas(List<Koopa> koopasList){
        /*
        Point2D koopa1 = new Point2D(localToParent(getLocation().x, getLocation().y).getX(),
                localToParent(getLocation().x, getLocation().y).getY());
        double koopa1CenterX = koopa1.getX() + RADIUS;
        double koopa1CenterY = koopa1.getY() + RADIUS;

        for (Koopa k : koopasList) {
            if (k != this) {
                if (intersects(k)) {
                    Point2D koopa2 = new Point2D(localToParent(getLocation().x, getLocation().y).getX(),
                            localToParent(getLocation().x, getLocation().y).getY());
                    double koopa2CenterX = koopa2.getX() + RADIUS;
                    double koopa2CenterY = koopa2.getY() + RADIUS;

                    double deltaX = koopa1CenterX - koopa2CenterX;
                    double deltaY = koopa1CenterY - koopa2CenterY;
                    double widthOverlap = RADIUS - Math.abs(deltaX);
                    double heightOverlap = RADIUS - Math.abs(deltaY);

                    if (widthOverlap < heightOverlap){
                        this.setVelocity( new PVector(
                                - this.getVelocity().x,
                                this.getVelocity().y)
                        );
                    }else{
                        this.setVelocity( new PVector(
                                this.getVelocity().x,
                                - this.getVelocity().y)
                        );
                    }

                }
            }
        }
         */
        for (Koopa k : koopasList) {
            if (k != this) {
                if (intersects(k)) {
                    this.setVelocity( new PVector(
                            - this.getVelocity().x,
                            this.getVelocity().y)
                    );
                }
            }
        }
    }

    public SpriteBouncing koopaBounceWallsQuestionMark(List<WallSprite> wallList, List<QuestionMarkSprite> questionMarkList, List<WallSprite> evilWallSprites){

        Point2D koopa = new Point2D(localToParent(getLocation().x, getLocation().y).getX(),
                localToParent(getLocation().x, getLocation().y).getY());
        double koopaCenterX = koopa.getX() + RADIUS;
        double koopaCenterY = koopa.getY() + RADIUS;

        for (int j = 0; j < Math.max(wallList.size(), Math.max(evilWallSprites.size(), questionMarkList.size())); j++){

            if (j < wallList.size() && this.getBoundsInParent().intersects(wallList.get(j).getBoundsInParent())){
                Point2D rectangle = new Point2D(wallList.get(j).localToParent(wallList.get(j).getLocation().x, wallList.get(j).getLocation().y).getX(),
                        wallList.get(j).localToParent(wallList.get(j).getLocation().x, wallList.get(j).getLocation().y).getY());

                double rectCenterX = rectangle.getX() + wallList.get(j).getHeight() / 2;
                double rectCenterY = rectangle.getY() + wallList.get(j).getWidth() / 2;

                //System.out.println(wallList.get(j).getHeight() + " - " + wallList.get(j).getWidth());

                double deltaX = koopaCenterX - rectCenterX;
                double deltaY = koopaCenterY - rectCenterY;
                double widthOverlap = (RADIUS + wallList.get(j).getWidth() / 2) - Math.abs(deltaX);
                double heightOverlap = (RADIUS + wallList.get(j).getHeight() / 2) - Math.abs(deltaY);
                if (widthOverlap < heightOverlap){
                    this.setVelocity( new PVector(
                            - this.getVelocity().x,
                            this.getVelocity().y)
                    );
                }else{
                    this.setVelocity( new PVector(
                            this.getVelocity().x,
                            - this.getVelocity().y)
                    );
                }
                return wallList.remove(j);
            }
            else if(j < questionMarkList.size() && this.getBoundsInParent().intersects(questionMarkList.get(j).getBoundsInParent())){
                Point2D rectangle = new Point2D(questionMarkList.get(j).localToParent(questionMarkList.get(j).getLocation().x, questionMarkList.get(j).getLocation().y).getX(),
                        questionMarkList.get(j).localToParent(questionMarkList.get(j).getLocation().x, questionMarkList.get(j).getLocation().y).getY());

                double rectCenterX = rectangle.getX() + questionMarkList.get(j).getHeight() / 2;
                double rectCenterY = rectangle.getY() + questionMarkList.get(j).getWidth() / 2;

                //System.out.println(wallList.get(j).getHeight() + " - " + wallList.get(j).getWidth());

                double deltaX = koopaCenterX - rectCenterX;
                double deltaY = koopaCenterY - rectCenterY;
                double widthOverlap = (RADIUS + questionMarkList.get(j).getWidth() / 2) - Math.abs(deltaX);
                double heightOverlap = (RADIUS + questionMarkList.get(j).getHeight() / 2) - Math.abs(deltaY);

                if (widthOverlap < heightOverlap){
                    this.setVelocity( new PVector(
                            - this.getVelocity().x,
                            this.getVelocity().y)
                    );
                }else{
                    this.setVelocity( new PVector(
                            this.getVelocity().x,
                            - this.getVelocity().y)
                    );
                }

                return questionMarkList.remove(j);
            }
            else if(j < evilWallSprites.size() && getBoundsInParent().intersects(evilWallSprites.get(j).getBoundsInParent())){
                Point2D rectangle = new Point2D(evilWallSprites.get(j).localToParent(evilWallSprites.get(j).getLocation().x, evilWallSprites.get(j).getLocation().y).getX(),
                        evilWallSprites.get(j).localToParent(evilWallSprites.get(j).getLocation().x, evilWallSprites.get(j).getLocation().y).getY());

                double rectCenterX = rectangle.getX() + evilWallSprites.get(j).getHeight() / 2;
                double rectCenterY = rectangle.getY() + evilWallSprites.get(j).getWidth() / 2;

                //System.out.println(wallList.get(j).getHeight() + " - " + wallList.get(j).getWidth());

                double deltaX = koopaCenterX - rectCenterX;
                double deltaY = koopaCenterY - rectCenterY;
                double widthOverlap = (RADIUS + evilWallSprites.get(j).getWidth() / 2) - Math.abs(deltaX);
                double heightOverlap = (RADIUS + evilWallSprites.get(j).getHeight() / 2) - Math.abs(deltaY);

                if (widthOverlap < heightOverlap){
                    this.setVelocity( new PVector(
                            - this.getVelocity().x,
                            this.getVelocity().y)
                    );
                }else{
                    this.setVelocity( new PVector(
                            this.getVelocity().x,
                            - this.getVelocity().y)
                    );
                }
                return null;
            }
        }
        return null;
    }
}