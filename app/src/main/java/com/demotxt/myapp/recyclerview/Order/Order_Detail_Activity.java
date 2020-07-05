package com.demotxt.myapp.recyclerview.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class Order_Detail_Activity extends AppCompatActivity {

    TextView txt_price,txt_cart_items,txt_status,txt_id,txt_date;
    ProgressBar mProgressBar;

    //For Cartlist
    private RecyclerView mRecyclerView;
    private Order_Detail_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Order_Detail> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__detail_);
        //Dummy Data
        mList = new ArrayList<>();

        mList.add(new Order_Detail("Shoes","15$","2",R.drawable.image_1));
        mList.add(new Order_Detail("Shirt","25$","1",R.drawable.image_2));
        mList.add(new Order_Detail("Tie","5$","1",R.drawable.image_3));
        mList.add(new Order_Detail("Pant","20$","1",R.drawable.image_4));

        //
        //INIT
        txt_id = findViewById(R.id.txt_id);
        txt_status = findViewById(R.id.txt_status);
        txt_price = findViewById(R.id.txt_price);
        txt_date = findViewById(R.id.txt_Date);
        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.order_detail_products);

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

        //
        setadapterRecyclerView();
    }

    //for Recycler View
    private void setadapterRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Order_Detail_Adapter(getApplicationContext(),mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
