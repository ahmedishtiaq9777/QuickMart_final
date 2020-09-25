package com.demotxt.myapp.Quickmart.CategoryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;
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

public class CatKids_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private RecyclerView mRecyclerView;
    private CatKids_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Spinner mSpinner;
    String Cattext, cat;
    List<Catkids> ProdKids;
    //CheckConnection connection;
    CustomInternetDialog dialog;
    ConstraintLayout lyt_Main, lyt_second;

    // private int sid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cat_kids_, container, false);
        mRecyclerView = rootview.findViewById(R.id.Rv_CatKids);
        mSpinner = rootview.findViewById(R.id.kidCategory);
        lyt_Main = rootview.findViewById(R.id.lyt_mainFrag);
        lyt_second = rootview.findViewById(R.id.lyt_SecondFrag);

        //  connection=new CheckConnection(getActivity());
        dialog = new CustomInternetDialog(getActivity());
        //
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.KidsCategory,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);


        ProdKids = new ArrayList<>();

        //int   sid=getArguments().getInt("user_id");
        TabsBasic activity = (TabsBasic) getActivity();// get acticity data
        int sid = Objects.requireNonNull(activity).getuserid();
        String userid = String.valueOf(sid);
        String url = hostinglink + "/Home/getprowithsellerid";
        getconnection(url, userid);


        return rootview;
    }

    public void getconnection(String url, final String seller_id) {
        final RequestQueue request = Volley.newRequestQueue(Objects.requireNonNull(getContext()));


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProdKids = Arrays.asList(gson.fromJson(response, Catkids[].class));

                            if (ProdKids.isEmpty()) {
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
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sellerid", seller_id);
                params.put("category", "Kid");

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
        mAdapter = new CatKids_Adapter(getActivity(), ProdKids);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setimageurl() {
        int n = 0;
        for (Catkids i : ProdKids) {
            i.setThumbnail(hostinglink + i.getThumbnail());
            // list.remove(n);
            ProdKids.set(n, i);
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
                    query = CheckCategory();

                    mAdapter.getCatFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {


                    mAdapter.getFilter().filter(newText);

                    return false;
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "Loading Data", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Cattext = adapterView.getItemAtPosition(i).toString();
        CheckCategory();
        Toast.makeText(getContext(), Cattext, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public String CheckCategory() {

        if (Cattext.equals("Kids(1–5) Boy")) {
            cat = "boyKids(1-5)";
        } else if (Cattext.equals("Kids(6–12) Boy")) {
            cat = "boyKids(6-12)";
        } else if (Cattext.equals("Kids(1–5) Girl")) {
            cat = "GirlKids(1-5)";
        } else if (Cattext.equals("Kids(6–12) Girl")) {
            cat = "GirlKids(6-12)";
        } else if (Cattext.equals("All")) {
            cat = "";
        }

        return cat;
    }


}


