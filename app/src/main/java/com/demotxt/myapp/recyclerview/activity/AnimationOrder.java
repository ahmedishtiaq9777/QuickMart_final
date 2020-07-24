package com.demotxt.myapp.recyclerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.demotxt.myapp.recyclerview.R;

public class AnimationOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_order);

       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent start = new Intent(AnimationOrder.this, MainActivity2.class);
                startActivity(start);
                finish();
            }
        },6000);
    }
}

