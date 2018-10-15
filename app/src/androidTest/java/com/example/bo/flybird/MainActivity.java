package com.example.bo.flybird;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private  GameView gameView;
    private Handler handler;
    private  final static long TIME_INTERVAL = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        handler = new Handler(getApplicationContext().getMainLooper());

        final Timer timer = new Timer();
        //Use handler change the view content under other thread
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }

        }, 0, TIME_INTERVAL);
    }

}
