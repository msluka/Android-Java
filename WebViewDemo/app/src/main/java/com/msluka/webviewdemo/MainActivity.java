package com.msluka.webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to remove status bar programmatically this code has to be used before setContentView(layout)
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // to remove status bar from style just add <item name="android:windowFullscreen">true</item>

        setContentView(R.layout.activity_main);




        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
       // webView.loadUrl("https://rexxer.nazwa.pl/aukcje/export9102.html");
        //webView.loadUrl("https://s.codepen.io/shotas/debug/ZPpXXo/LDAmdPKNOgnr");
        webView.loadData("<html><h1>This is H1 tag </h1><p>This is P tag</p></html>", "text/html", "UTF-8");


    }
}
