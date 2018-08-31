package com.example.android.marblecalculator;

public class Point {
    protected float x;
    protected float y;


    public Point(){
        this.x = 0;
        this.y = 0;
    }

    public Point (float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public double getLength(){
        return Math.sqrt(x*x+y*y);
    }

    public float dotProduct(Point p){
        return (this.x * p.x + this.y * p.y);
    }

    public float crossProduct (Point p){
        return Math.abs(this.x * p.y - p.x * this.y);
    }


}
