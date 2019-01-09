package com.msluka.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    MediaPlayer mp;


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

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
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

                }
            }.start();

        } else {

            resetTimer();

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);


        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

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
