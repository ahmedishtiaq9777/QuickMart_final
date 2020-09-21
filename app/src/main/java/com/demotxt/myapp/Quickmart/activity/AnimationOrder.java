package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.Quickmart.R;

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
        }, 6000);
    }
}

