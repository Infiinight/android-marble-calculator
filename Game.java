package com.example.android.marblecalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Background background;
    private ArrayList<Marble> marbleList;
    private ArrayList<Plank> plankList;



    //Dimensions for screen
    private final float WIDTH = getWidth();
    private final float HEIGHT = getHeight();


    public Game(Context context){
        super(context);

        //add callback to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);

        //allow events to be handled
        setFocusable(true);


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder,int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        background = new Background();
        marbleList = new ArrayList<>();
        plankList = new ArrayList<>();
        plankList.add(new Plank(100,1000,700,1100,10));

        //safely start game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            marbleList.add(new Marble(event.getX(),event.getY(),10));
            return true;
        }
        return super.onTouchEvent(event);

    }

    public void update(){
        for(int i = 0; i < marbleList.size(); i++){
            for(int j = 0; j < plankList.size(); j++) {
                marbleList.get(i).collidePlank(plankList.get(j));
                marbleList.get(i).updateCollision(plankList.get(j));

            }
            marbleList.get(i).update();
            if (marbleList.get(i).getY() > getHeight()){
                marbleList.remove(i);
                System.out.println("REMOVED");
                i--;

            }
        }

    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        background.draw(canvas);
        for(int i = 0; i < marbleList.size(); i++){
            marbleList.get(i).draw(canvas);
        }
        for(int i = 0; i < plankList.size(); i++){
            canvas.drawLines(plankList.get(i).toFloatArray(),plankList.get(i).getPaint());
        }
    }


}
