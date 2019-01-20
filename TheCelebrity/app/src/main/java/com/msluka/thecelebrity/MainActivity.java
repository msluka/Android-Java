package com.msluka.thecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Bitmap celebImage;

    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    ArrayList<String> celebUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int chosenCeleb = 0;

    int locationOfCorrectAnswer = 0;
    String [] answers = new String[4];

    public void generateQuestion(){

        Random random = new Random();
        chosenCeleb = random.nextInt(celebUrls.size());

        ImageDownloader imageTask = new ImageDownloader();


        try {
            celebImage = imageTask.execute(celebUrls.get(chosenCeleb)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(celebImage);

        locationOfCorrectAnswer = random.nextInt(4);
        int incorrectAnswerLocation;

        for(int i =0; i< 4; i++){

            if(i == locationOfCorrectAnswer){

                answers[i] = celebNames.get(chosenCeleb);

            }else{

                incorrectAnswerLocation = random.nextInt(celebUrls.size());

                while (incorrectAnswerLocation == chosenCeleb){

                    incorrectAnswerLocation = random.nextInt(celebUrls.size());

                }

                answers[i] = celebNames.get(incorrectAnswerLocation);

            }
        }

        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
    }

    public void celebChosen(View view){

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
            generateQuestion();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong! it was "+ celebNames.get(chosenCeleb), Toast.LENGTH_LONG).show();
            generateQuestion();
        };
    }



    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap myBitMap = BitmapFactory.decodeStream(inputStream);

                return myBitMap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;


            String html = "";

            try{
                /*
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){

                    char current = (char)data;
                    result = result + current;
                    data = reader.read();

                }
                */

                // String html = "";

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder str = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    str.append(line);
                }
                in.close();
                html = str.toString();
                return html;




                //return result;




            }catch(Exception e){
                e.printStackTrace();
            }



            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.celebImageView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {

            result = task.execute("https://www.imdb.com/list/ls052283250").get();
            //Log.i("ddd", result);
            //mt.setText(result);

            String [] splitResult2 = result.split(  "<div class=\"article listo\">");
            String [] splitResult = splitResult2[1].split(  "<div class=\"footer filmosearch\">");

            Pattern p = Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while(m.find()){

                //System.out.println(m.group(1));
                celebUrls.add(m.group(1));

            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while(m.find()){

                //System.out.println(m.group(1));
                celebNames.add(m.group(1));

            }

            generateQuestion();
            /*
            Random random = new Random();
            chosenCeleb = random.nextInt(celebUrls.size());

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap celebImage;


            celebImage = imageTask.execute(celebUrls.get(chosenCeleb)).get();
            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = random.nextInt(4);
            int incorrectAnswerLocation;

            for(int i =0; i< 4; i++){

                if(i == locationOfCorrectAnswer){

                    answers[i] = celebNames.get(chosenCeleb);

                }else{

                    incorrectAnswerLocation = random.nextInt(celebUrls.size());

                    while (incorrectAnswerLocation == chosenCeleb){

                        incorrectAnswerLocation = random.nextInt(celebUrls.size());

                    }

                    answers[i] = celebNames.get(incorrectAnswerLocation);

                }
            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

            */


        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
