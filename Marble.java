package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Marble extends Point {

    private float radius;
    private Paint paint = new Paint();

    private double angle = 0;
    private double velocity = 5;
    private double xspeed = 0;
    private double yspeed = 0;

    //physics quantities
    private double m; //mass
    private double a = 0.2; //acceleration
    private double dya; // y  component of a
    private double dxa; // x component of a
    private double damping = 0.8; //simulate loss of energy

    private final double gravity = 0.2; //g constant

    //placement for vector
    private Point marbleVector = new Point();
    private Point gVector = new Point(0,1);

    //collision
    private boolean updatePlankCollision = false;
    private boolean updateWallCollision = false;
    private Plank collidingPlank;
    private Wall collidingWall;
    private boolean isCollidingPlank = false;
    private boolean isCollidingWall = false;




    public Marble(float x, float y, float radius){
        super.x = x;
        super.y = y;
        this.radius = radius;
        paint.setColor(Color.RED);

        //setting physics quantities
        updateAcceleration(angle);

    }
    public void update(){

        updateSpeed();
        x += xspeed;
        y += yspeed;


    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, paint);
    }
    //----------------------------get/set methods-----------------------------------//
    public float getRadius(){
        return radius;
    }

    //----------------------------- Relating to motion -----------------------------//
    public void updateSpeed(){
        //update speed components
        this.xspeed +=dxa;
        this.yspeed +=dya;
        limitSpeed();
    }

    private void updateAcceleration(double angle){
        this.dxa = a * Math.sin(angle);
        this.dya = a * Math.cos(angle);
    }
    private double getVelocity(){
        velocity = Math.sqrt(xspeed*xspeed + yspeed*yspeed);
        return velocity;
    }
    private void limitSpeed(){
        if (!isCollidingPlank && getVelocity() >= 10) {
            if (xspeed > 0.5) {
                xspeed += -0.3;
            } else if (xspeed < -0.5) {
                xspeed += 0.3;
            }
            if (yspeed > 10) {
                yspeed = 10;
            }
        }
        if (isCollidingPlank && getVelocity() >=5){
            a = 0;
            dxa = 0;
            dya = 0;
        }

    }

    //-------------------------- Relating to Plank ----------------------------//
    public boolean collidePlank (Plank p){
        if(p.lieRange(this)) {
            if (p.getDistance(this)-7 <= radius) { //check the '-7' how make collision realistic
                collidingPlank = p;
                isCollidingPlank = true;
                return true;
            }
        }
        return false;

    }
    public void updatePlankCollision(Plank plank){
        if(!isUpdatePlankCollision()) {
            angle = Math.acos(gVector.dotProduct(plank.getdUnitVector()));
            if (angle > Math.PI / 2) {
                angle = angle - Math.PI;
            }
            //update acceleration and velocity
            updateAcceleration(angle);
            this.xspeed = 0;
            this.yspeed = 0;
            a = gravity * Math.cos(angle);
            updatePlankCollision = true;
        }
    }
    public void resetPlankFall(){
        angle = 0;
        a = gravity;
        dya = gravity;
        dxa = 0;
        updatePlankCollision = false;
        collidingPlank = null;
        isCollidingPlank = false;

    }
    public Plank getCollidingPlank(){
        return collidingPlank;
    }
    public boolean isUpdatePlankCollision(){
        return updatePlankCollision;
    }

    //--------------------- Relating to wall -------------------------//
    public boolean collideWall(Wall w){
        if (w.lieRange(this)){
            if (w.getDistance(this) -7< radius){ // check collision
                collidingWall = w;
                isCollidingWall = true;
                return true;
            }
        }
        else if(w.getCornerDistance(this) -7< radius){
            collidingWall = w;
            isCollidingWall = true;
            return true;
        }
        return false;
    }
    public void updateWallCollision(Wall w){ // - 0 is horizontal collision, 1 is vertical collision, 2 is corner collision

        if (w.isMovable()){

        }

        else if (!w.isMovable()) {
            if (!updateWallCollision) {

                if (w.getCollisionDirection(this) == 0) {
                    dxa = -dxa * damping;
                    xspeed = -xspeed * damping;
                } else if (w.getCollisionDirection(this) == 1) {
                    yspeed = -yspeed * damping;
                } else if (w.getCollisionDirection(this) == 2) {
                    yspeed = -yspeed;
                    xspeed = -xspeed;
                } else {
                    System.out.println("updateWallCollision Error");
                }
                updateWallCollision = true;
            }
            if (updateWallCollision) {
                dxa = 0;
                dya = 0;
            }
        }
    }
    public Wall getCollidingWall(){
        return collidingWall;
    }
    public void resetWallFall(){
        updateWallCollision = false;
        collidingWall = null;
        isCollidingWall = false;
        dya = gravity;
    }

    //-------------------------Marble-marble collision----------------------//



}
