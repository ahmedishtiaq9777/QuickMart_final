package com.demotxt.myapp.recyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.adapter.RecyclerView3;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewAdapter;
import com.demotxt.myapp.recyclerview.adapter.RecyclerViewProdAdapter;
import com.demotxt.myapp.recyclerview.ownmodels.r3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    // List<Book> lstBook2;
    List<Book> list;
    List<Prod> Book22;
    List<r3> mTrends;
    View view;
    View v2;
    ViewFlipper viewFlipper;
    SwipeRefreshLayout RefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.homefragment, container, false);

        list = new ArrayList<>();
        Book22 = new ArrayList<>();
        mTrends = new ArrayList<>();

        RefreshLayout = view.findViewById(R.id.SwipeRefresh);
        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                list = new ArrayList<>();
                Book22 = new ArrayList<>();
                mTrends = new ArrayList<>();

                getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getsellers/", 1);

                getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getrecommendedpro/", 2);

                getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/gettrendingpro/", 3);

                RefreshLayout.setRefreshing(false);
            }
        });


        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getsellers/", 1);

        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getrecommendedpro/", 2);

        getconnection("http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/gettrendingpro/", 3);


        v2 = inflater.inflate(R.layout.cardveiw_item_prod, null);

        setrecycleone();

        ImageView prod = (ImageView) v2.findViewById(R.id.book_img_id);

        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetail = new Intent(getActivity(), Prod_Activity.class);
                startActivity(productdetail);
            }
        });

        //flipper
        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);


        int images[] = {R.drawable.ac_banner, R.drawable.cloth_banner, R.drawable.sale1, R.drawable.mobile_banner};

        for (int image : images) {
            flipperimages(image);
        }


        return view;
    }

    public void getconnection(String url, final int val) {

        RefreshLayout.setRefreshing(false);
        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest rRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (val != 0) {
                                    GsonBuilder builder = new GsonBuilder();
                                    Gson gson = builder.create();
                                    //For Seller Recycler View
                                    if (val == 1) {
                                        //Array for Book Class
                                        list = Arrays.asList(gson.fromJson(response, Book[].class));
                                        int n = 0;
                                        for (Book i : list) {
                                            i.setThumbnail("http://ahmedishtiaq9778-001-site1.ftempurl.com" + i.getThumbnail());
                                            // list.remove(n);
                                            list.set(n, i);
                                            n++;
                                        }
                                        //Setting Recycler View 1
                                        setrecycleone();
                                    }
                                    //For Recommended Recycler View
                                    else if (val == 2) {
                                        //Array for Prod Class
                                        Book22 = Arrays.asList(gson.fromJson(response, Prod[].class));
                                        int n = 0;
                                        for (Prod i : Book22) {
                                            i.setThumbnail("http://ahmedishtiaq9778-001-site1.ftempurl.com" + i.getThumbnail());
                                            // list.remove(n);
                                            Book22.set(n, i);
                                            n++;
                                        }
                                        //Setting Recycler View 2
                                        setrecycletwo();
                                    }
                                    //For Trending Recycler View
                                    else if (val == 3) {
                                        //Array for r3 class
                                        mTrends = Arrays.asList(gson.fromJson(response, r3[].class));
                                        int n = 0;
                                        for (r3 i : mTrends) {
                                            i.setThumbnail("http://ahmedishtiaq9778-001-site1.ftempurl.com" + i.getThumbnail());
                                            // list.remove(n);
                                            mTrends.set(n, i);
                                            n++;
                                        }
                                        //Setting Recycler View 3
                                        setrecyclethree();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                        }
                    });
            requestQueue.add(rRequest);
        } catch (
                Exception E) {
            RefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Error: " + E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void flipperimages(int image) {
        try {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        } catch (Exception e) {
            Toast.makeText(getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setrecycleone() {


        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //  myrv.setLayoutManager(new LinearLayoutManager(getActivity()));

        myrv.setLayoutManager(layoutManager);
        myrv.setAdapter(myAdapter);

    }

    public void setrecycletwo() {
        RecyclerView myrv2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        RecyclerViewProdAdapter myAdapter1 = new RecyclerViewProdAdapter(getActivity(), Book22);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv2.setLayoutManager(layoutManager1);
        myrv2.setAdapter(myAdapter1);
    }

    public void setrecyclethree() {
        RecyclerView myrv3 = (RecyclerView) view.findViewById(R.id.recyclerview3);
        RecyclerView3 myAdapter2 = new RecyclerView3(getActivity(), mTrends);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        myrv3.setLayoutManager(layoutManager2);
        myrv3.setAdapter(myAdapter2);
    }
}
