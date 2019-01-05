package com.msluka.tictac;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = true;

    // 0 - cross; 1 - circle
    int activePlayer = 0;

    // 2 means unplayed counter (cell)
    int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int crossWon = 0;
    int circleWon = 0;
    int drawPlay = 0;


    public void pressCell(View view){

        ImageView counter = (ImageView)view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {

            LinearLayout hiddenLayout = findViewById(R.id.hiddenLayout);
            LinearLayout loWinner = findViewById(R.id.winnersMesageLeyout);
            LinearLayout loDraw = findViewById(R.id.drawMesageLeyout);

            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.cross);
                activePlayer = 1;
                startImageBlinking((ImageView) findViewById(R.id.playerOlogo));
                stopImageBlinking((ImageView) findViewById(R.id.playerXlogo));
            } else {

                counter.setImageResource(R.drawable.circle);
                activePlayer = 0;

                startImageBlinking((ImageView) findViewById(R.id.playerXlogo));
                stopImageBlinking((ImageView) findViewById(R.id.playerOlogo));
            }

            counter.animate().rotationYBy(360f).setDuration(500);

            for(int [] winningPosition : winningPositions){

                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2)
                {
                    // someone has won
                    gameIsActive = false;

                    TextView crossWonField = findViewById(R.id.playerXwon);
                    TextView circleWonField = findViewById(R.id.playerOwon);

                    String name;

                    if(gameState[winningPosition[0]] == 0){
                        name = "Cross";
                        crossWon = crossWon + 1;
                        //String.valueOf(crossWon);

                        activePlayer = 0; // this player won and it has to be start a new game
                        startImageBlinking((ImageView) findViewById(R.id.playerXlogo));
                        stopImageBlinking((ImageView) findViewById(R.id.playerOlogo));

                        crossWonField.setText(String.valueOf(crossWon));





                    }else{
                        name = "Circle";
                        circleWon = circleWon + 1;

                        activePlayer = 1; // this player won and it has to be start a new game
                        startImageBlinking((ImageView) findViewById(R.id.playerOlogo));
                        stopImageBlinking((ImageView) findViewById(R.id.playerXlogo));

                        circleWonField.setText(String.valueOf(circleWon));


                    }

                    // Set winner's logo in message box
                    ImageView iv = findViewById(R.id.winnersLogo);
                    if(gameState[winningPosition[0]] == 0){
                        iv.setImageResource(R.drawable.cross);
                    }
                    if(gameState[winningPosition[0]] == 1){
                        iv.setImageResource(R.drawable.circle);
                    }



                    loWinner.setVisibility(View.VISIBLE);
                    loDraw.setVisibility(View.INVISIBLE);

                    startImageBlinking((ImageView)findViewById(R.id.winnerAvatar));

                    //startImageJumping((ImageView)findViewById(R.id.winnerAvatar));

                    showMessagebox(hiddenLayout);

                    //Toast.makeText(MainActivity.this, "Winner is: "+ name, Toast.LENGTH_LONG).show();

                }
            }

            if(gameIsActive){

                boolean gameIsOver = true;

                for(int counterState : gameState){
                    if(counterState == 2){
                        gameIsOver = false;
                    }else{

                    }
                };

                if(gameIsOver){
                    gameIsActive = false;

                    loWinner.setVisibility(View.INVISIBLE);
                    loDraw.setVisibility(View.VISIBLE);

                    startImageBlinking((ImageView)findViewById(R.id.drawHandshake));

                    showMessagebox(hiddenLayout);


                    //Toast.makeText(MainActivity.this, "It's a draw! ", Toast.LENGTH_LONG).show();
                    drawPlay = drawPlay + 1;
                    TextView drawField = findViewById(R.id.drawField);
                    drawField.setText(String.valueOf(drawPlay));
                }

            }









        }



    };

    public void playAgain(View view){

        gameIsActive = true;
        //activePlayer = 0;

        for(int i =0; i< gameState.length; i++){
            gameState[i] = 2;
        }

        android.support.v7.widget.GridLayout gl = findViewById(R.id.gridLayout);
        for(int i =0; i < gl.getChildCount(); i++){

            ((ImageView) gl.getChildAt(i)).setImageResource(0);

        }


    }

    public void playAgainHidden(View view){

        closeMessagebox(view);
        playAgain(view);
    }

    public void closeMessagebox(View view){
        LinearLayout hiddenLayout = findViewById(R.id.hiddenLayout);
        LinearLayout loWinner = findViewById(R.id.winnersMesageLeyout);
        LinearLayout loDraw = findViewById(R.id.drawMesageLeyout);

        hiddenLayout.setVisibility(view.GONE);
        loWinner.setVisibility(View.INVISIBLE);
        loDraw.setVisibility(View.INVISIBLE);
    }

    public void showMessagebox(View view){
        view.setVisibility(view.VISIBLE);
    }

    public void resetStatistics(View view){

        crossWon = 0;
        circleWon = 0;
        drawPlay = 0;

        TextView crossWonField = findViewById(R.id.playerXwon);
        TextView circleWonField = findViewById(R.id.playerOwon);
        TextView drawField = findViewById(R.id.drawField);

        crossWonField.setText("0");
        circleWonField.setText("0");
        drawField.setText("0");
    }


    public void startBlinking(View view){

        TextView myText = (TextView) findViewById(R.id.drawField );

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250); //You can manage the blinking time with this parameter

        anim.setStartOffset(125);

        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);

    }


    public void startImageBlinking(ImageView view){

        //ImageView iview = (ImageView) findViewById(R.id.playerXlogo );

        ImageView iview = view;

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250); //You can manage the blinking time with this parameter
        anim.setStartOffset(125);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        iview.startAnimation(anim);

    }

    public void stopImageBlinking(ImageView view){
        //ImageView myView = (ImageView) findViewById(R.id.draw );

        view.clearAnimation();
    }

    public void fadeOutAllImagesInCounters(View view){

        //TextView mainText = findViewById(R.id.mainText);
       // mainText.animate().alpha(0f).setDuration(1000);

        android.support.v7.widget.GridLayout gl = findViewById(R.id.gridLayout);

        for(int i =0; i < gl.getChildCount(); i++){

            ((ImageView) gl.getChildAt(i)).animate().alpha(0f).setDuration(1000);
            //((ImageView) gl.getChildAt(i)).setImageResource(0);

        }



    }

    /*public void setPositionForMessageBox(){

       View msgBox = findViewById(R.id.hiddenLayout);
       msgBox.bringToFront();

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startImageBlinking((ImageView) findViewById(R.id.playerXlogo ));


    }
}
