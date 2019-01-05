package com.msluka.videodemo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;


    public void playVideo(View view){

        VideoView mv = findViewById(R.id.mainVideoView);

        String path = "android.resource://" + getPackageName() + "/" +R.raw.demo;
        mv.setVideoPath(path);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mv);
        mv.setMediaController(mediaController);

        mv.start();
    }

    public void playVideoYoutube (View view){

        VideoView mv = findViewById(R.id.mainVideoView);

        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=Bznxx12Ptl0")));


    }

    public void playAudio(View view){

       //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.albatraoz);
        mediaPlayer = MediaPlayer.create(this, R.raw.albatraoz);

       mediaPlayer.start();

    }

    public void pauseAudio(View view){

        //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.albatraoz);
        mediaPlayer.pause();

    }

    public void stopAudio(View view){

       // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.albatraoz);
        mediaPlayer.stop();

    }

    // This is completely declared in onCreate method
    public void setSeekBarOnStart(View view){

        SeekBar volumeControl = (SeekBar) view;

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("Value", Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }


        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        SeekBar volumeControl = findViewById(R.id.seekBar);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        // This code is a must to work with seekBar
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Log.i("Value", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }


        });

        mediaPlayer = MediaPlayer.create(this, R.raw.albatraoz);

        final SeekBar scrubber = findViewById(R.id.scrubber);
        scrubber.setMax(mediaPlayer.getDuration());

       //Working with timer
       new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubber.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if (fromUser ==true) //fromUser validation
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }


        });




    }
}
