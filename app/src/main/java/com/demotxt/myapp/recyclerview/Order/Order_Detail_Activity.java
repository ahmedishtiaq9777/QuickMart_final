package com.demotxt.myapp.recyclerview.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demotxt.myapp.recyclerview.R;

public class Order_Detail_Activity extends AppCompatActivity {

    TextView txt_price,txt_cart_items,txt_status,txt_id,txt_date;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__detail_);

        //INIT
        txt_id = findViewById(R.id.txt_id);
        txt_status = findViewById(R.id.txt_status);
        txt_price = findViewById(R.id.txt_price);
        txt_cart_items = findViewById(R.id.txt_cart_items);
        txt_date = findViewById(R.id.txt_Date);
        mProgressBar = findViewById(R.id.progressBar);

        //Receive Data
        Intent intent = getIntent();
        String Price = intent.getExtras().getString("Price");
        String status = intent.getExtras().getString("Status");
        String id = intent.getExtras().getString("ID");
        String date = intent.getExtras().getString("Date");

        //Setting Values on Text Views
        txt_id.setText(id);
        txt_date.setText(date);
        txt_price.setText(Price);
        txt_status.setText(status);

        //Progressbar conditions
        if (status.equals("completed")){
            mProgressBar.setProgress(100);
        }
        else if (status.equals("ongoing")){
            mProgressBar.setProgress(50);
        }
        else {
            mProgressBar.setProgress(25);
        }

    }
}
