package com.example.game.TapiocaLauncher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;
@SuppressWarnings("ClickableViewAccessibility")


public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying;
    private Background background;
    private BoardManager boardManager;
    private List<Ball> layout;
    private int screenX, screenY;
    public static Launcher launcher;
    private Arrow arrow;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private double startX =0, startY=0, endX=0, endY = 0;
    private boolean ballClicked;


    public GameView(Context context, int screenX, int screenY) {

        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f /screenY;

        background = new Background(screenX, screenY, getResources());
        launcher = new Launcher(screenX, screenY, getResources());
        arrow = new Arrow(launcher.x, launcher.y, getResources());
        boardManager = new BoardManager(screenX, screenY, context);
        layout = boardManager.fillBoard(1);
        paint = new Paint();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();

        }

    }

    void update (){
        launcher.update();
    }

    private void draw (){

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background.background, background.x, background.y, paint);

            for (Ball ball: layout) {
                canvas.drawBitmap(ball.getBall(), ball.x, ball.y, paint);
            }
            canvas.drawBitmap(launcher.getLauncher(), launcher.x, launcher.y, paint);
            // canvas.drawBitmap(arrow.getArrow(), arrow.x, arrow.y, paint);
            rotateArrow(arrow, 45, canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void rotateArrow(Arrow src, int degrees, Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.reset();
        Paint paint = new Paint();
        matrix.postTranslate(-src.arrow.getWidth() /2, -10);
        matrix.postRotate(degrees);
        matrix.postTranslate(src.x, src.y);
        canvas.drawBitmap(src.arrow, matrix, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (launcher.isReadyToLaunch()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (( event.getX() >= launcher.getX() && event.getX() <= (launcher.getX() + launcher.getHeight()) )&&
                        (event.getY() >= launcher.getY() && event.getY() <= (launcher.getY() + launcher.getWidth()))) {
                    startX = event.getX();
                    startY = event.getY();
                    ballClicked = true;
                    //Log.d("", "onTouchEvent: Motion Down  x-val: " + startX);
                    //Log.d("", "onTouchEvent: Motion Down  y-val: " + startY);
                }
            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                //Log.d("", "onTouchEvent: Move" + event.getX());
//            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (ballClicked) {
                    endX = event.getX();
                    endY = event.getY();
                    // Log.d("", "onTouchEvent: Motion Up  x-val: " + endX);
                    // Log.d("", "onTouchEvent: Motion Up  y-val: " + endY);
                    launcher.moveBall(startX, startY, endX, endY);
                    ballClicked = false;
                }
            }
        }
        return true;
    }
}