package com.msluka.imagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

// https://www.gannett-cdn.com/-mm-/3b8b0abcb585d9841e5193c3d072eed1e5ce62bc/c=0-30-580-356/local/-/media/2018/02/24/USATODAY/usatsports/large-pile-of-hundred-dollar-bills-cash-money-savings-rich_large.jpg


public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public void downloadImage (View view){

        String imageAdress = "https://www.gannett-cdn.com/-mm-/3b8b0abcb585d9841e5193c3d072eed1e5ce62bc/c=0-30-580-356/local/-/media/2018/02/24/USATODAY/usatsports/large-pile-of-hundred-dollar-bills-cash-money-savings-rich_large.jpg\n" +
                "\n";
        Bitmap myImage;

        ImageDownloader task = new ImageDownloader();
        try {

            myImage  = task.execute(imageAdress).get();
            imageView.setImageBitmap(myImage);

        } catch (Exception e) {

            e.printStackTrace();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);




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


}
