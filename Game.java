package com.example.android.marblecalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Background background;
    private ArrayList<Marble> marbleList;
    private ArrayList<Plank> plankList;
    private ArrayList<Wall> wallList;



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
        wallList = new ArrayList<>();
        plankList.add(new Plank(100,1000,700,1100,10));
        plankList.add(new Plank(600,1500,1000,1300,10));
        wallList.add(new Wall(new Point(900,500),new Point(920,1300)));
        System.out.println(plankList.size());

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
        for(Marble m : marbleList) {
            for (Plank p : plankList) {
                if (m.collidePlank(p)) {
                    m.updatePlankCollision(p);
                    break; //break the moment detect collision with one plank; ensures 1 - 1 collision
                }
                if (m.getCollidingPlank() == p) {
                    if (!p.lieRange(m)) { // if marble is not in range of plank
                        m.resetPlankFall();
                    }
                    break; //same as above; ensures 1 - 1 collision, also saves time
                }
            }
            for (Wall w: wallList){
                if (m.getCollidingWall() == w){
                    if(!m.collideWall(w)){
                        m.resetWallFall();
                    }
                    break;
                }
                if (m.collideWall(w)){
                    m.updateWallCollision(w);
                    break;
                }


            }

            m.update();
            if (m.getY() > getHeight()) {
                marbleList.remove(m);
                System.out.println("REMOVED");
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
            plankList.get(i).draw(canvas);
        }
        for(int i = 0; i < wallList.size(); i++){
            wallList.get(i).draw(canvas);
        }
    }






}
