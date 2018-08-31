package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Background {

    Paint paint = new Paint();

    public Background(){
        paint.setColor(Color.GRAY);
    }

    public void draw(Canvas canvas){
        canvas.drawColor(paint.getColor());
    }

}
