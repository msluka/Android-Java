package com.msluka.actionbarmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  return super.onOptionsItemSelected(item);

        switch(item.getItemId()){

            case R.id.settings: Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.about: Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;

            default:return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
