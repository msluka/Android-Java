package com.msluka.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int count = 1;
    public void printNumber(){
        if(count <= 10)
            Log.i("Timer TimerTask", String.valueOf(count));
            count++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Method 1

        final Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {

                Log.i("Timer Runnable", "Runnable is running");
                handler.postDelayed(this, 1000);

            }
        };

        handler.post(run);

        // Method 2

        new CountDownTimer(10000, 1000){
            public void onTick(long millisecondsUntilDone){

                Log.i("Timer CountDownTimer", "Seconds left"+ String.valueOf(millisecondsUntilDone / 1000));

            }

            public void onFinish(){
                Log.i("Timer CountDownTimer", "CountDownTimer finished");
            }
        }.start();


        // Method 3

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // code goes here
                printNumber();


            }
        }, 0, 1000);


    }
}
