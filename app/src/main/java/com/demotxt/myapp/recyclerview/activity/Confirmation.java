package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.adapter.OrderViewAdapter;
import com.demotxt.myapp.recyclerview.fragment.CartFragment;
import com.demotxt.myapp.recyclerview.model.OrderViewImg;

import java.util.ArrayList;
import java.util.List;

public class Confirmation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button confirm;
    TextView t1,t2;
    List<OrderViewImg> orderViewImgs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        List<OrderViewImg> orderViewImgs = new ArrayList<>();
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));


        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderViewAdapter(orderViewImgs);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





















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
                Intent i = new Intent(getBaseContext(), AnimationOrder.class);
                startActivity(i);
            }
        });
    }
}
