package com.demotxt.myapp.Quickmart.CategoryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.activity.TabsBasic;
import com.demotxt.myapp.Quickmart.ownmodels.CheckConnect;
import com.demotxt.myapp.Quickmart.ownmodels.CustomInternetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class CatMen_Fragment extends Fragment{
    private RecyclerView mRecyclerView;
    private CatMen_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayAdapter<CharSequence> adapter;
    String Cattext, cat;
    List<CatMen> ProdMen;
    CheckConnect connection;
    CustomInternetDialog dialog;
    Spinner mSpinner;
    ConstraintLayout lyt_Main, lyt_second;
    String userid, url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cat_men_, container, false);
        mRecyclerView = rootview.findViewById(R.id.Rv_CatMen);
        mSpinner = rootview.findViewById(R.id.menCategory);
        lyt_Main = rootview.findViewById(R.id.lyt_mainFrag);
        lyt_second = rootview.findViewById(R.id.lyt_SecondFrag);
        connection = new CheckConnect(getActivity());
        dialog = new CustomInternetDialog(getActivity());

        boolean is_connected = connection.CheckConnection();
        if (!is_connected) {
            dialog.showCustomDialog();
        }
        //
        /*
        adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.MenCategory,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);*/

        mSpinner.setVisibility(View.GONE);
        ProdMen = new ArrayList<>();

        TabsBasic activity = (TabsBasic) getActivity();  // get activity data
        int sid = Objects.requireNonNull(activity).getuserid();
        Log.i("Seller id", String.valueOf(sid));
        userid = String.valueOf(sid);
        //
        url = hostinglink + "/Home/getprowithsellerid";

        //On UI Thread To reduce the load on main Thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("UI THREAD MEN-FRAG","IN UI THREAD");
                getconnection(url, userid,"Men");
            }
        });



        return rootview;
    }

    public void getconnection(String url, final String seller_id,final String category) {


        final RequestQueue request = Volley.newRequestQueue(Objects.requireNonNull(getContext()));

        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProdMen = Arrays.asList(gson.fromJson(response, CatMen[].class));

                            if (ProdMen.isEmpty()) {
                                lyt_Main.setVisibility(View.GONE);
                                lyt_second.setVisibility(View.VISIBLE);
                            } else {
                                setimageurl();
                                setadapterRecyclerView();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(getContext(),  Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sellerid", seller_id);
                params.put("category", category);

                return params;
            }

            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        request.add(rRequest);


    }

    private void setadapterRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mAdapter = new CatMen_Adapter(getActivity(), ProdMen);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setimageurl() {
        int n = 0;
        for (CatMen i : ProdMen) {
            i.setThumbnail(hostinglink + i.getThumbnail());
            // list.remove(n);
            ProdMen.set(n, i);
            n++;


        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater = Objects.requireNonNull(getActivity()).getMenuInflater();
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
                    mAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        } catch (Exception E) {
            Toast.makeText(getContext(), "Loading Data", Toast.LENGTH_SHORT).show();
        }
    }

    //Check and return value
    public String CheckCategory() {

        if (Cattext.equals("Shirts")) {
            cat = "MenShirts";
        } else if (Cattext.equals("T-Shirt")) {
            cat = "MenTshirts";
        } else if (Cattext.equals("Jeans")) {
            cat = "MenDenim";
        } else if (Cattext.equals("Shoes")) {
            cat = "MenShoes";
        } else if (Cattext.equals("Accessories")) {
            cat = "AccesariesMen";
        } else if (Cattext.equals("All")) {
            cat = "Men";
        }

        return cat;
    }



}
