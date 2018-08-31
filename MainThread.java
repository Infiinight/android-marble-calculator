package com.example.android.marblecalculator;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Game game;
    private int FPS = 60;
    private double averageFPS;
    public static Canvas canvas;
    private boolean running;


    public MainThread(SurfaceHolder surfaceHolder, Game game){
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;

    }

    @Override
    public void run(){
        long startTime;
        long timeMill;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.game.update();
                    this.game.draw(canvas);
                }
            } catch (Exception e) {e.printStackTrace();}
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){e.printStackTrace();}
                }
            }

            timeMill = System.nanoTime()/1000000;
            waitTime = targetTime - timeMill;
            try{
                this.sleep(waitTime);
            } catch (Exception e){}
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPS){
                averageFPS = frameCount/((float)totalTime/1000000000);
                System.out.println(averageFPS);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    public void setRunning(boolean b) {
        running = b;
    }


}
