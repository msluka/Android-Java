package com.msluka.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return result;

            }catch(Exception e){

                e.printStackTrace();
                return "Failed";

            }


        }
    }

    public void grabWebContent(View view){
         EditText urlView = findViewById(R.id.urlInput);
         String url = urlView.getText().toString();

         if(!url.matches("")){
             DownloadTask task = new DownloadTask();
             String result = null;

             try {
                 result = task.execute(url).get();
                 TextView grabedText = findViewById(R.id.resultTextView);
                 grabedText.setText(result);

             } catch (ExecutionException e) {
                 e.printStackTrace();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }else{
             Toast.makeText(getApplicationContext(), "Address field cannot be empty", Toast.LENGTH_SHORT).show();
         }
    }

    public void clearResult(View view){
        TextView grabedText = findViewById(R.id.resultTextView);
        grabedText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://www.google.com/").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        //Log.i("Result", result);

       // TextView grabedText = findViewById(R.id.resultTextView);
       // grabedText.setText(result);



    }
}
