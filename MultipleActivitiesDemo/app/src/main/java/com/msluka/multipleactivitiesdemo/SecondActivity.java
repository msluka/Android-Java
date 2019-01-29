package com.msluka.multipleactivitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public void jumpToMainActivity(View vie){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("helloFS","Hello from SecondActivity");
        startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView msgBox = findViewById(R.id.msgFromMain);
        Intent intent = getIntent();

        if(intent.getStringExtra("helloFM") != null && intent.getStringExtra("helloFM").length() > 0){
            msgBox.setText(intent.getStringExtra("helloFM"));
        }
        else{
            msgBox.setText("There is no message from MainActivity");
        }

        if(intent.getStringExtra("carName") !=null && intent.getStringExtra("carName").length() > 0){

            Toast.makeText(getApplicationContext(), intent.getStringExtra("carName"), Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(), "No message from list", Toast.LENGTH_LONG).show();
        }


    }
}
