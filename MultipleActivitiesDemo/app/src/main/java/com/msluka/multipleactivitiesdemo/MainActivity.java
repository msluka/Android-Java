package com.msluka.multipleactivitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void jumpToSecondActivity(View vie){

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("helloFM","Hello from MainActivity");
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView msgBox = findViewById(R.id.msgFromSecond);
        Intent intent = getIntent();

        if(intent.getStringExtra("helloFS") != null && intent.getStringExtra("helloFS").length() > 0){
            msgBox.setText(intent.getStringExtra("helloFS"));
        }
        else{
            msgBox.setText("There is no message from SeconActivity");
        }
    }
}
