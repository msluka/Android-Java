package com.msluka.multipleactivitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        final Intent intent = getIntent();

        if(intent.getStringExtra("helloFS") != null && intent.getStringExtra("helloFS").length() > 0){
            msgBox.setText(intent.getStringExtra("helloFS"));
        }
        else{
            msgBox.setText("There is no message from SeconActivity");
        }

        // Send data from list by clicking on it to SecondActivity;

        ListView listView = findViewById(R.id.listView);
        final ArrayList<String> cars = new ArrayList<String>();
        cars.add("Mercedes");
        cars.add("BMW");
        cars.add("Toyota");
        cars.add("Ferrari");
        cars.add("Lamborghini");
        cars.add("AUDI");
        cars.add("Mercedes-2");
        cars.add("BMW-2");
        cars.add("Toyota-2");
        cars.add("Ferrari-2");
        cars.add("Lamborghini-2");
        cars.add("AUDI-2");

        ArrayAdapter <String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, cars);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("carName", cars.get(position));
                startActivity(intent);

            }
        });



    }
}
