package com.msluka.gridlayout;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;

    public void btnTapped(View view){

        // get tapped layout id (integer)
        int id = view.getId();
        // get tapped layout id (string)
        String ourId = view.getResources().getResourceEntryName(id);

        //get audio by file name
        int audioId = getResources().getIdentifier(ourId,"raw","com.msluka.gridlayout");


        cleanUpMediaPlayer();

        mp = MediaPlayer.create(this, audioId);

        mp.start();

        view.animate().rotationYBy(180f).setDuration(300);



        Log.i("LayOut Id", ourId);

    }

    public void cleanUpMediaPlayer(){
        if(mp != null) {
            if(mp.isPlaying()) {
                try {
                    mp.reset();
                    mp.prepare();
                    mp.stop();
                    mp.release();
                    mp = null;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
