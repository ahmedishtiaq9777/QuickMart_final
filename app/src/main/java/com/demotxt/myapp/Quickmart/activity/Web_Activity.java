package com.demotxt.myapp.Quickmart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.Quickmart.R;

public class Web_Activity extends AppCompatActivity {
    private WebView webViewurl;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);
        webViewurl = (WebView) findViewById(R.id.webView);

        webViewurl.getSettings().setJavaScriptEnabled(true);

        webViewurl.getSettings().setBuiltInZoomControls(true);
        final Activity activity = this;

        webViewurl.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });
        webViewurl.loadUrl("http://ahmedishtiaq165-001-site1.ftempurl.com/Seller/signup");


    }


}
