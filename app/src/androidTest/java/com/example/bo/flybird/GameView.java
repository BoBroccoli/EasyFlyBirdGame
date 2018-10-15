package com.example.bo.flybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameView extends View {

    private int canvasWidth, canvasHeight;
    private Bitmap bird[] = new Bitmap[2];
    private int Bird_X = 10;
    private int Bird_Y;
    private int birdSpeed;
    private int score;
    private int level = 1;
    private Button endGame;
    //ball
    private int blueX;
    private  int blueY;

    private int blueSpeed = 10;
    private Paint bluePaint = new Paint();

    private int life = 3;
    private Bitmap bg;

    private Paint scorePaint = new Paint();
    private Paint levelPaint = new Paint();
    private Bitmap lifes[] = new Bitmap[2];

    private Bitmap GameOver;
    public static Boolean Dead = false;

    private boolean touch_flg = false;
    public GameView(final Context context){
        super(context);
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(true);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);

        levelPaint.setColor(Color.BLUE);
        levelPaint.setTextSize(32);
        lifes[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        lifes[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);


        GameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
        Bird_Y = 200;
        score = 0;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //For the end game
        if (Dead == true){
            canvas.drawBitmap(GameOver, 0,0,null);
        }
        else {
            score++;
            if (score % 500 == 0)
                level++;


            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            canvas.drawBitmap(bg, 0, 0, null);

            //Draw score and level
            canvas.drawText("Score: " + score, 20, 60, scorePaint);
            canvas.drawText("Level: " + level, canvasWidth / 2, 60, levelPaint);
            blueX -= blueSpeed * level;

            int minBirdHeight = 0;
            int maxBirdHeight = bg.getHeight() - bird[0].getHeight();

            Bird_Y += birdSpeed - 10;
            if (Bird_Y < minBirdHeight) {
                Bird_Y = minBirdHeight;
            }
            if (Bird_Y > maxBirdHeight) {
                Bird_Y = maxBirdHeight;
            }
            birdSpeed += 1;

            if (touch_flg) {
                canvas.drawBitmap(bird[1], Bird_X, Bird_Y, null);
                touch_flg = false;
            } else {
                canvas.drawBitmap(bird[0], Bird_X, Bird_Y, null);
            }


            canvas.drawCircle(blueX, blueY, 20, bluePaint);
            if (hitCheck(blueX, blueY)) {

                blueX = canvasWidth + 30;
                blueY = (int) Math.floor(Math.random() * (bg.getHeight() - 10)) + 20;
            }
            if (blueX < 0) {
                blueX = canvasWidth + 30;
                blueY = (int) Math.floor(Math.random() * (bg.getHeight() - 10)) + 20;
            }
            //life
            for (int i = 0; i < 3; i++) {
                int x = 870 + i * lifes[0].getWidth();
                if (life == 0) {
                    Dead = true;
                }
                if (i < life) {
                    canvas.drawBitmap(lifes[0], x, 30, null);
                    x += lifes[0].getWidth();
                } else {
                    canvas.drawBitmap(lifes[1], x, 30, null);
                }
            }
        }

    }
    public  boolean hitCheck(int X, int Y){
        if(Bird_X < X && X < Bird_X+bird[0].getWidth()  &&  Bird_Y < Y && Y < Bird_Y + bird[0].getHeight() ){
            life--;
            return  true;
        }
        return  false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            birdSpeed -= 20;
            if(birdSpeed < -20)
                birdSpeed = -20;
            if(birdSpeed > 30)
                birdSpeed = 30;
            touch_flg = true;
        }
        return  true;
    }
}
