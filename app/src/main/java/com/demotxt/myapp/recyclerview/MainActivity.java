package com.demotxt.myapp.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.adapter.RecyclerViewAdapter;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewProdAdapter;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
