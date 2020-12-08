package com.example.androidsig;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebContentsDebuggingEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadUrl(getString(R.string.urlnodejs));
    }
}