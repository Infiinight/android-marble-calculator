package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Marble extends Point {

    private float radius;
    private Paint paint = new Paint();

    private double angle;
    private double velocity;
    private double xspeed = 0;
    private double yspeed = 0;

    //physics quantities
    private double m; //mass
    private double a; //acceleration
    private final double gravity = 0.2; //g constant

    //placement for vector
    private Point marbleVector = new Point();
    private final Point gVector = new Point(0,1);

    //collision
    private boolean touch = false;


    public Marble(float x, float y, float radius){
        super.x = x;
        super.y = y;
        this.radius = radius;
        paint.setColor(Color.RED);

        //setting physics quantities
        angle = 0;
        velocity = 5;
        a = 0.2;

    }
    public void update(){
        updateSpeed();
        x += xspeed;
        y += yspeed;

    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, paint);
    }

    //update speed
    public void updateSpeed(){
        velocity += a;
        this.xspeed = velocity*Math.sin(angle);
        this.yspeed = velocity*Math.cos(angle);
    }

    //check for collision
    public boolean collidePlank (Plank plank){
        marbleVector.setX(this.x - plank.getPoint1().getX());
        marbleVector.setY(this.y - plank.getPoint1().getY());
        double distance = marbleVector.crossProduct(plank.getdUnitVector());
        if(x > plank.getPoint1().getX() && x < plank.getPoint2().getX()) {
            if (distance-7 <= radius) { //check the '-7' how make collision realistic
                touch = true;
                return true;
            }
        }
        else {touch = false;}
        return false;
    }
    public void updateCollision(Plank plank){
        if(touch == true) {
            angle = Math.acos(gVector.dotProduct(plank.getdUnitVector()));
            if (angle > Math.PI / 2) {
                angle = -angle;
            }
            //update acceleration
            a = a * Math.cos(angle);
        }
        else{
            angle = 0;
            a = gravity;
        }


    }

}
