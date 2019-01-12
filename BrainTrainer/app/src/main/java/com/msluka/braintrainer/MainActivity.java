package com.msluka.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean gameActive = true;

    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    Integer locationOfCorrectAnswer;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    CountDownTimer timer;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    int score= 0;
    int numberOfQuestions = 0;

    public void generateQuestion(){

        answers.clear();

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a)+" + "+ Integer.toString(b)+" =");

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;

        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == a+b){
                    incorrectAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view) {

        if (gameActive) {

            if (view.getTag().equals(Integer.toString(locationOfCorrectAnswer))) {

                score++;
                resultTextView.setText("Correct!");

            } else {
                resultTextView.setText("Wrong!");

            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();
        }

    }

    public void playAgain(View view) {

        gameActive = true;
        score= 0;
        numberOfQuestions = 0;

        timerTextView.setText("60 s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        generateQuestion();

        if(timer != null){
            timer.cancel();
        }

        timer = new CountDownTimer(60100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000)+" s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0 s");
                resultTextView.setText("Done");
                gameActive = false;
            }
        }.start();

    }

    public void start(View view){

        view.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startBtn);
        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        timerTextView = findViewById(R.id.timerTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        generateQuestion();


    }
}
