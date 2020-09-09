package com.demotxt.myapp.Quickmart.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatKids_Adapter;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatMen_Adapter;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatWomen_Adapter;
import com.demotxt.myapp.Quickmart.MyLocation;
import com.demotxt.myapp.Quickmart.activity.Error_Screen_Activity;
import com.demotxt.myapp.Quickmart.ownmodels.Book;
import com.demotxt.myapp.Quickmart.ownmodels.Prod;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.adapter.RecyclerView3;
import com.demotxt.myapp.Quickmart.adapter.RecyclerViewAdapter;
import com.demotxt.myapp.Quickmart.adapter.RecyclerViewProdAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.collections.LongIterator;

import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class HomeFragment extends Fragment {

    List<Book> list;
    List<Prod> Book22;
    List<Prod> mTrends;
    View view;
    ViewFlipper viewFlipper;
    SwipeRefreshLayout RefreshLayout;
    private RecyclerViewAdapter myAdapter;
    private RecyclerViewProdAdapter myAdapter1;
    private RecyclerView3 myAdapter2;
    private CatKids_Adapter mCatKids_adapter;
    private CatMen_Adapter mCatMen_adapter;
    private CatWomen_Adapter mCatWomen_adapter;
    TextView shop, rec, trend;
    //loc
    public String Latitude,Longitude;
    public static Location loc;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileFragment.loadLocale(getContext());
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //
        view = inflater.inflate(R.layout.homefragment, container, false);

        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        shop = view.findViewById(R.id.textRecommend);
        rec = view.findViewById(R.id.textNew);
        trend = view.findViewById(R.id.textTrending);

        //Connection Check
        CheckConnection();

        list = new ArrayList<>();
        Book22 = new ArrayList<>();
        mTrends = new ArrayList<>();

        // to Find the Location
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(final Location location) {
                loc = location;
                System.out.println("Latitude: " + loc.getLatitude());
                System.out.println("Longitude: " + loc.getLongitude());
                Latitude = String.valueOf(loc.getLatitude());
                Longitude = String.valueOf(loc.getLongitude());
                getSeller(hostinglink + "/Home/getsellers/",Latitude,Longitude);
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);




        RefreshLayout = view.findViewById(R.id.SwipeRefresh);
        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshLayout.setRefreshing(true);

                list = new ArrayList<>();
                Book22 = new ArrayList<>();
                mTrends = new ArrayList<>();

                getSeller(hostinglink + "/Home/getsellers/",Latitude,Longitude);

                getconnection(hostinglink + "/Home/getrecommendedproduct/", 2);

                getconnection(hostinglink + "/Home/gettrendingpro/", 3);


            }
        });

        getconnection(hostinglink + "/Home/getrecommendedproduct/", 2);

        getconnection(hostinglink + "/Home/gettrendingpro/", 3);


        int images[] = {R.drawable.off1, R.drawable.off2, R.drawable.off3, R.drawable.off4, R.drawable.off5};

        for (int image : images) {
            flipperimages(image);
        }
        return view;
    }

    //For Trending and Recommended
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

                                    //For Recommended Recycler View
                                    if (val == 2) {
                                        //Array for Prod Class
                                        Book22 = Arrays.asList(gson.fromJson(response, Prod[].class));
                                        int n = 0;
                                        for (Prod i : Book22) {
                                            i.setThumbnail(hostinglink + i.getThumbnail());
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
                                        mTrends = Arrays.asList(gson.fromJson(response, Prod[].class));
                                        int n = 0;
                                        for (Prod i : mTrends) {
                                            i.setThumbnail(hostinglink + i.getThumbnail());
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

    //For Seller
    public void getSeller(String url, final String la, final String lo) {
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                //For Seller Recycler View
                                //Array for Book Class
                                list = Arrays.asList(gson.fromJson(response, Book[].class));
                                int n = 0;
                                for (Book i : list) {
                                    i.setThumbnail(hostinglink + i.getThumbnail());
                                    // list.remove(n);
                                    list.set(n, i);
                                    n++;
                                }
                                //Setting Recycler View 1
                                setrecycleone();
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
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("latitude", la);
                    params.put("longitude", lo);

                    //  params.p

                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
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
        myAdapter = new RecyclerViewAdapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        try {
            myrv.setLayoutManager(layoutManager);

            myrv.setAdapter(myAdapter);


        } catch (Exception e) {
            Log.e("Error", " " + e.getMessage());
        }
    }

    public void setrecycletwo() {
        RecyclerView myrv2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        myAdapter1 = new RecyclerViewProdAdapter(getActivity(), Book22);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv2.setLayoutManager(layoutManager1);
        myrv2.setAdapter(myAdapter1);
    }

    public void setrecyclethree() {
        RecyclerView myrv3 = (RecyclerView) view.findViewById(R.id.recyclerview3);
        myAdapter2 = new RecyclerView3(getActivity(), mTrends);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        myrv3.setLayoutManager(layoutManager2);
        myrv3.setAdapter(myAdapter2);
    }

    public void CheckConnection() {

        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(getContext(), "Wifi On", Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(getContext(), "Mobile Data On", Toast.LENGTH_SHORT).show();

            } else {
                Intent intent = new Intent(getContext(), Error_Screen_Activity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search_setting, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        try {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    shop.setVisibility(View.GONE);
                    rec.setVisibility(View.GONE);
                    trend.setVisibility(View.GONE);

                    myAdapter.getFilter().filter(newText);
                    myAdapter1.getFilter().filter(newText);
                    myAdapter2.getFilter().filter(newText);


                    return false;
                }

            });

        } catch (Exception E) {
            Toast.makeText(getContext(), E.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}