package com.demotxt.myapp.recyclerview.CategoryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class CatWomen_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<CatWomen> ProdWomen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_cat_women_, container, false);

        ProdWomen = new ArrayList<>();

        //TODO Add Data in The Recycler Views;

        mRecyclerView = rootview.findViewById(R.id.Rv_CatWomen);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CatWomen_Adapter(ProdWomen);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        return rootview;
    }
}
