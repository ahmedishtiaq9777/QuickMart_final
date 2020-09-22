package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.Quickmart.R;

public class Web_Activity extends AppCompatActivity {
    private WebView webViewurl;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);
        String url = "http://ahmedishtiaq165-001-site1.ftempurl.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}
