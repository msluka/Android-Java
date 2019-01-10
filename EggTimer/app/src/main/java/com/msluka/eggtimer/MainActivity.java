package com.msluka.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer2;
    MediaPlayer mp;

    Boolean minimized = false;
    ImageView eggImageView;


    public void updateTimer(int secondLefts) {

        int minutes = secondLefts / 60;
        int seconds = secondLefts - minutes * 60;

        String min;
        String sec;

        if (minutes < 10) {
            min = "0" + Integer.toString(minutes);
        } else {
            min = Integer.toString(minutes);
        }

        if (seconds < 10) {
            sec = "0" + Integer.toString(seconds);
        } else {
            sec = Integer.toString(seconds);
        }

        timerTextView.setText(min + ":" + sec);
    }

    public void resetTimer(int seconds){

        updateTimer(seconds);

        timerSeekBar.setProgress(seconds);

        if(countDownTimer !=null){
            countDownTimer.cancel();
        }

        if(countDownTimer2 !=null){
            countDownTimer2.cancel();
            eggImageView.animate().scaleX(1f).scaleY(1f).setDuration(1000);
            minimized = false;
        }

        timerSeekBar.setEnabled(true);
        counterIsActive = false;

        ImageView startBtn = findViewById(R.id.startBtn);
        ImageView stopBtn = findViewById(R.id.stopBtn);

        stopBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);

        if(mp !=null){
            mp.stop();
            mp.reset();
        }

    }


    public void controlTimer(View view) {

        ImageView startBtn = findViewById(R.id.startBtn);
        ImageView stopBtn = findViewById(R.id.stopBtn);



        if (counterIsActive == false) {

            jumpingEgg();

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            startBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.VISIBLE);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    timerTextView.setText("00:00");
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mp.start();

                    if(countDownTimer2 !=null){
                        countDownTimer2.cancel();
                        eggImageView.animate().scaleX(1f).scaleY(1f).setDuration(1000);
                        minimized = false;
                    }

                }
            }.start();

        } else {

            resetTimer(120);

            LinearLayout llLeft = findViewById(R.id.readyTimersLayout);
            LinearLayout llRight = findViewById(R.id.readyTimersLayoutRight);

            ArrayList llLeftChildren = getViewsByTag(llLeft, "readyTimer");
            ArrayList llRightChildren = getViewsByTag(llRight, "readyTimer");

            for (int counter = 0; counter < llLeftChildren.size(); counter++) {
                ImageView v = (ImageView) llLeftChildren.get(counter);
                v.setAlpha(0.5f);

            }

            for (int counter = 0; counter < llRightChildren.size(); counter++) {
                ImageView v = (ImageView) llRightChildren.get(counter);
                v.setAlpha(0.5f);

            }

            findViewById(R.id.soft).setAlpha(0.9f);




        }



    }

    public static ArrayList<View> getViewsByTag(ViewGroup root, String tag) {
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

    public void setMode(View view){

        if (counterIsActive == false) {
            //get tapped layout id (integer)
            int id = view.getId();
            //get tapped layout id (string)
            String ourId = view.getResources().getResourceEntryName(id);

            switch (ourId) {
                case "soft":
                    resetTimer(120);
                    break;

                case "normal":
                    resetTimer(210);
                    break;

                case "hard":
                    resetTimer(240);
                    break;

                case "five":
                    resetTimer(300);
                    break;

                case "ten":
                    resetTimer(600);
                    break;

                case "fifteen":
                    resetTimer(900);
                    break;

                default:
                    resetTimer(120);
            }


            LinearLayout llLeft = findViewById(R.id.readyTimersLayout);
            LinearLayout llRight = findViewById(R.id.readyTimersLayoutRight);

            ArrayList llLeftChildren = getViewsByTag(llLeft, "readyTimer");
            ArrayList llRightChildren = getViewsByTag(llRight, "readyTimer");

            for (int counter = 0; counter < llLeftChildren.size(); counter++) {
                ImageView v = (ImageView) llLeftChildren.get(counter);
                v.setAlpha(0.5f);

            }

            for (int counter = 0; counter < llRightChildren.size(); counter++) {
                ImageView v = (ImageView) llRightChildren.get(counter);
                v.setAlpha(0.5f);

            }

            view.setAlpha(0.9f);
        }
    }

    public void jumpingEgg(){

        eggImageView = findViewById(R.id.eggImageView);

        countDownTimer2 = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1500) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(minimized == false){

                    eggImageView.animate().scaleX(0.6f).scaleY(0.6f).setDuration(1500);
                    minimized = true;
                } else{
                    eggImageView.animate().scaleX(1f).scaleY(1f).setDuration(1500);
                    minimized = false;
                }

            }

            @Override
            public void onFinish() {



            }
        }.start();



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);


        timerSeekBar.setMax(900);
        timerSeekBar.setProgress(120);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

        });
    }
}
