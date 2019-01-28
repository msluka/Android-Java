package com.msluka.theweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultTextView;

    private void showToast(final String text) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void findWeather(View view){

        resultTextView.setText("");

       if(cityName.getText().toString().isEmpty()){

           Toast.makeText(getApplicationContext(), "City name cannot be empty", Toast.LENGTH_SHORT).show();

       }else {

           InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
           mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);


           try {
               String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
               DownloadTask task = new DownloadTask();
               task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&APPID=68b89124792e0192059e2da9c599a899");

           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
               Toast.makeText(getApplicationContext(), "Encode failed.", Toast.LENGTH_SHORT).show();
           }

       }

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder str = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    str.append(line);
                }
                in.close();
                result = str.toString();
                return result;

            } catch (Exception e) {

                e.printStackTrace();

                //Toast.makeText(getApplicationContext(), "Reader failed", Toast.LENGTH_SHORT).show();
                showToast("The City not found");

            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                String message = "";

                /*
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");

                JSONArray arr = new JSONArray(weatherInfo);

                for(int i = 0; i< arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if(main !="" && description != ""){


                        message = main+" \r\n" + description;

                    }

                }

                */

                String temp = "";
                String pressure = "";
                String humidity = "";
                String temp_min = "";
                String temp_max = "";



                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("main");

                JSONObject jsonObject2 = new JSONObject(weatherInfo);

                temp = jsonObject2.getString("temp");
                pressure = jsonObject2.getString("pressure");
                humidity = jsonObject2.getString("humidity");
                temp_min = jsonObject2.getString("temp_min");
                temp_max = jsonObject2.getString("temp_max");

                message = "Temperature: " + temp+" \r\n" + "Pressure: " +pressure +" \r\n"
                        +"Humidity: "+ humidity +" \r\n" + "Min.temp: "+ temp_min +" \r\n" + "Max.temp: "+temp_max;



                if(!message.isEmpty()){

                    resultTextView.setText(message);

                }else{
                    Toast.makeText(getApplicationContext(), "Could not find weather, please try again.(Empty.msg)", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather, please try again. JSON(CATCH)", Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = findViewById(R.id.cityNameEditText);
        resultTextView = findViewById(R.id.resultTextView);

    }
}
