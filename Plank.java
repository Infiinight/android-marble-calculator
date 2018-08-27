package com.example.android.marblecalculator;

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


    /*
    note to always draw left to right!
    so that the vector is set properly
    point 1 for left, point 2 for right
    */

    public Plank(float x1, float y1, float x2, float y2, int Thick){
        paint.setColor(Color.GREEN);
        point1 = new Point(x1,y1);
        point2 = new Point(x2,y2);
        this.thick = Thick;
        dVector = new Point(x2-x1,y2-y1);
        dUnitVector = new Point(dVector.getX()/(float)dVector.getLength(),dVector.getY()/(float)dVector.getLength());

    }
    public Paint getPaint(){
        return paint;
    }

    public float[] toFloatArray(){
        float[] ret = new float[thick*4];
        for (int i = 0; i < thick; i++){
            ret[4*i] = point1.getX();
            ret[4*i+1] = point1.getY()+i;
            ret[4*i+2] = point2.getX();
            ret[4*i+3] = point2.getY()+i;

        }
        return ret;
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

}
