package com.demotxt.myapp.recyclerview.CategoryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.TabsBasic;
//import com.demotxt.myapp.recyclerview.ownmodels.CheckConnection;
import com.demotxt.myapp.recyclerview.ownmodels.CustomInternetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;

public class CatKids_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CatKids_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Catkids> ProdKids;
    //CheckConnection connection;
    CustomInternetDialog dialog;

    // private int sid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cat_kids_, container, false);
        mRecyclerView = rootview.findViewById(R.id.Rv_CatKids);
      //  connection=new CheckConnection(getActivity());
        dialog=new CustomInternetDialog(getActivity());

    /*    boolean is_connected=connection.CheckConnection();
        if(!is_connected)
        {
            dialog.showCustomDialog();
        }
*/



        ProdKids = new ArrayList<>();

        //int   sid=getArguments().getInt("user_id");
        TabsBasic activity = (TabsBasic) getActivity();// get acticity data
        int sid = activity.getuserid();
        String userid = String.valueOf(sid);
        String url = hostinglink +"/Home/getprowithsellerid";
        getconnection(url, userid);
        //TODO Add Data in The Recycler Views;

        //


        return rootview;
    }

    public void getconnection(String url, final String seller_id) {
        final RequestQueue request = Volley.newRequestQueue(getContext());


        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProdKids = Arrays.asList(gson.fromJson(response, Catkids[].class));

                            setimageurl();
                            setadapterRecyclerView();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("sellerid", seller_id);
                params.put("category", "Kid");

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
            i.setThumbnail(hostinglink  + i.getThumbnail());
            // list.remove(n);
            ProdKids.set(n, i);
            n++;


        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search_setting, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
    }


}


