package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.CartFragment;

public class Confirmation extends AppCompatActivity {
    Button confirm;
    TextView t1,t2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);
        confirm=(Button)findViewById(R.id.button3);
        t1 = (TextView)findViewById(R.id.getname);
        t2 = (TextView)findViewById(R.id.getaddress);


        Bundle bn = getIntent().getExtras();
        String name = bn.getString("getname");
        String address = bn.getString("getaddress");
        t1.setText(String.valueOf(name));
        t2.setText(String.valueOf(address));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity2.class);
                startActivity(i);
            }
        });
    }
}
