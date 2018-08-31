package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Wall {

    /*
    Wall is defined by its top left and bottom right points, denote Point 1 and Point 2 respectively

    Collision detected from all 4 sides .
     */
    private Point point1;
    private Point point2;
    private Paint paint = new Paint();
    private double vertiLength;
    private double horiLength;

    //variables for collision
    public Wall(){
        paint.setColor(Color.rgb(23, 132, 81));
        this.point1 = new Point(10,10);
        this.point2 = new Point(20,20);
        vertiLength = point1.getY() - point2.getY();
        horiLength = point2.getX() - point1.getX();
    }


    public Wall(Point point1, Point point2){
        paint.setColor(Color.rgb(23, 132, 81));
        this.point1 = point1;
        this.point2 = point2;
        vertiLength = point1.getY() - point2.getY();
        horiLength = point2.getX() - point1.getX();

    }

    public Point getPoint1(){
        return point1;
    }
    public Point getPoint2(){
        return point2;
    }
    public void draw(Canvas canvas){
        canvas.drawRect(point1.getX(),point1.getY(),point2.getX(),point2.getY(),paint);
    }
    public boolean lieRange(Marble m){ //used by movable walls
        return ((m.y > point1.y && m.y < point2.y) || (m.x > point1.x && m.x < point2.x));
    }
    public float getDistance(Marble m){
        if (m.y > point1.y && m.y < point2.y){ // marble lies on vertical portion of wall
            if (m.getX() < point1.getX()){ // marble on left of wall
                return (point1.getX() - m.getX());
            }
            else if (m.getX() > point2.getX()){ // marble on right of wall
                return (m.getX() - point2.getX());
            }
            else {
                System.out.println("Not left or right of wall");
            }

        }
        else if (m.x > point1.x && m.x < point2.x){ // marble lies on horizontal portion of wall
            if (m.y > point2.y){ //marble bottom of wall
                return (m.y - point2.y);
            }
            else if (m.y < point1.y){ // marble top of wall
                return (point1.y - m.y);
            }
            else {
                System.out.println("Not top or bottom of wall");
            }
        }
        return 0; //
    }
    public int getQuadrant(Marble m){ // find the quadrant marble approaching from.
        if (m.x > point2.x && m.y < point1.y){ // top right
            return 1;
        }
        else if (m.x < point1.x && m.y < point1.y){ // top left
            return 2;
        }
        else if (m.x < point1.x && m.y > point2.y){ // bottom left
            return 3;
        }
        else if (m.x > point2.x && m.y > point2.y){ // bottom right
            return 4;
        }
        else{
            return 0; //error
        }
    }
    public float getCornerDistance(Marble m){
        int quadrant = getQuadrant(m);
        double distance = 0;
        switch (quadrant) {
            case 1:
                distance = Math.sqrt(Math.pow(m.x - point2.x, 2) + Math.pow(point1.y - m.y, 2));
                break;
            case 2:
                distance = Math.sqrt(Math.pow(m.x - point1.x, 2) + Math.pow(point1.y - m.y, 2));
                break;
            case 3:
                distance = Math.sqrt(Math.pow(m.x - point1.x, 2) + Math.pow(point2.y - m.y, 2));
                break;
            case 4:
                distance = Math.sqrt(Math.pow(m.x - point2.x, 2) + Math.pow(point2.y - m.y, 2));
                break;
            default:
                distance = 999;
                break;
        }
        return (float)distance;
    }
    public int getCollisionDirection(Marble m){ // - 0 is horizontal collision, 1 is vertical collision, 2 is corner collision
        if (m.y > (point1.y - m.getRadius()) && m.y < (point2.y + m.getRadius())){ //horizontal collision
            return 0;
        }
        else if(m.x > (point1.x - m.getRadius()) && m.x < (point2.x + m.getRadius())){ //vertical collision
            return 1;
        }
        else {
            return 2;
        }
    }

    public boolean isMovable(){
        return false;
    }




}
