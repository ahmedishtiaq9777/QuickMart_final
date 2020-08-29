package com.demotxt.myapp.recyclerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.adapter.Notify_Adapter;
import com.demotxt.myapp.recyclerview.ownmodels.Notify;
import com.demotxt.myapp.recyclerview.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class Notification_Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Notify_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);
        initToolbar();

        List<Notify> notifies = new ArrayList<>();
        notifies.add(new Notify("Order Completed"));
        notifies.add(new Notify("Congrats! Your Order is Confirmed"));
        notifies.add(new Notify("Your Order is waiting for confirmation"));


        mRecyclerView = findViewById(R.id.notify_RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Notify_Adapter(this,notifies);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }
}
