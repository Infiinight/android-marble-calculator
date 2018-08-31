package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Plank {


    private Point point1;
    private Point point2;
    private int thick;
    private Paint paint= new Paint();
    private Point dVector;
    private Point dUnitVector;

    //placement vector
    private Point vector = new Point();


    /*
    note to always draw left to right!
    so that the vector is set properly
    point 1 for left, point 2 for right

    Plank is defined by its top left and right points, width extends it downwards
    Collision is detected from the top line.
    */

    public Plank(float x1, float y1, float x2, float y2, int Thick){
        paint.setColor(Color.GREEN);
        point1 = new Point(x1,y1);
        point2 = new Point(x2,y2);
        this.thick = Thick;
        dVector = new Point(x2-x1,y2-y1);
        dUnitVector = new Point(dVector.x/(float)dVector.getLength(),dVector.y/(float)dVector.getLength());

    }

    public float[] toFloatArray(){
        float[] ret = new float[thick*4];
        for (int i = 0; i < thick; i++){
            ret[4*i] = point1.x;
            ret[4*i+1] = point1.y+i;
            ret[4*i+2] = point2.x;
            ret[4*i+3] = point2.y+i;

        }
        return ret;
    }
    public void draw(Canvas canvas){
        canvas.drawLines(toFloatArray(),paint);
    }

    public Point getPoint1(){
        return point1;
    }

    public Point getPoint2(){
        return point2;
    }
    public Point getdVector(){
        return dVector;
    }
    public Point getdUnitVector(){
        return dUnitVector;
    }
    public boolean lieRange(Marble m){
        return (m.x > point1.x && m.x < point2.x);
    }
    public float getDistance(Marble m){
        vector.setX(m.x - point1.x);
        vector.setY(m.y - point1.y);
        float distance = Math.abs(vector.crossProduct(dUnitVector));
        return distance;

    }

}
