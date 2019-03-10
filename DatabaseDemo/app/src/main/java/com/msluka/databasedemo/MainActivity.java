package com.msluka.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers2 (id INTEGER PRIMARY KEY, name VARCHAR, age INT(3))");

            //myDatabase.execSQL("INSERT INTO newUsers2 (name, age) VALUES('John', 25)");
            //myDatabase.execSQL("INSERT INTO newUsers2 (name, age) VALUES('David', 37)");

            //myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
            //myDatabase.execSQL("INSERT INTO users (name, age) VALUES('LUKA', 37)");

            //myDatabase.execSQL("DELETE FROM users WHERE name = 'LUKA'");

            myDatabase.execSQL("UPDATE newUsers2 SET name='Sara', age = 24 WHERE id=2 ");



            Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers2", null);
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            //count rows in query result: c.getCount()
            Log.i("Records", String.valueOf(c.getCount()));

            c.moveToFirst();

            while(c!=null){
              //Log.i("User", c.getString(nameIndex)+" "+ c.getString(ageIndex));
                Log.i("User", c.getString(idIndex)+" "+c.getString(nameIndex)+" "+ c.getString(ageIndex));
              c.moveToNext();

            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
