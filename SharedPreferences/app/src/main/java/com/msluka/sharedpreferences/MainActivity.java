package com.msluka.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.msluka.sharedpreferences", Context.MODE_PRIVATE);

        /*
        sharedPreferences.edit().putString("name","Luka").apply();

        String myName = sharedPreferences.getString("name", "22");
        Log.i("SSS", myName);
        Toast.makeText(this, myName, Toast.LENGTH_LONG).show();
        */


        ArrayList<String> cars = new ArrayList<>();
        cars.add("Mercedes");
        cars.add("Audi");
        cars.add("BMW");

        try {

            sharedPreferences.edit().putString("cars", ObjectSerializer.serialize(cars)).apply();

        } catch (IOException e) {

            e.printStackTrace();

        }

        ArrayList<String> newCars = new ArrayList<>();

        try {
            newCars = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("cars", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("NewCars", newCars.toString());
        Log.i("NewCars", newCars.get(1));

        for(String car: newCars){

            Log.i("NewCars", car);

        }


    }
}
