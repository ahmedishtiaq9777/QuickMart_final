package com.demotxt.myapp.Quickmart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.demotxt.myapp.Quickmart.R;

public class Forgot_Pass_Activity extends AppCompatActivity {

    EditText PhoneNo;
    Button Forget_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass_);

        PhoneNo = findViewById(R.id.Txt_PhoneNo);
        Forget_Btn = findViewById(R.id.SendForget_Btn);




    }
}