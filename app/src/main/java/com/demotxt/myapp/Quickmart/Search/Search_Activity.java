package com.demotxt.myapp.Quickmart.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.ColorSpace;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.Cart_Fav.CartListBeanlist;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatMen;
import com.demotxt.myapp.Quickmart.CategoryFragments.CatMen_Adapter;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.activity.MainActivity2;
import com.demotxt.myapp.Quickmart.fragment.HomeFragment;
import com.demotxt.myapp.Quickmart.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Search_Activity extends AppCompatActivity {

    private static final String TAG = "Search_Activity";
    private RecyclerView mRecyclerView;
    private Search_Adapter mSearch_adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Search_Model> ModelProd;
    private final String link = hostinglink + "/Home/SearchNearByProducts";
    private final List<String> Sellers_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        //
        initToolbar();
        mRecyclerView = findViewById(R.id.recyclerView);
        //
        if (Sellers_list.isEmpty()){
            
            Sellers_list.addAll(HomeFragment.SellerIds);
        }
        else {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show();
        }
        
        
        //

        for (int x = 0; x < Sellers_list.size(); x++) {
            Log.i(TAG, "val " + Sellers_list.get(x));
        }


        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listOfTestObject = new TypeToken<List<String>>() {
        }.getType();

        final String test = gson.toJson(Sellers_list, listOfTestObject);

        Log.i(TAG,test);

        //
        getProductsData(link, test);



    }

    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Tools.setSystemBarColor(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_setting, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        try {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearch_adapter.getFilter().filter(query);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mRecyclerView.setVisibility(View.GONE);
                    return false;
                }
            });
        } catch (Exception E) {
            Toast.makeText(this, "Data is loading", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    //For Getting All Products
    private void getProductsData(String url, final String j) {

        try {
            final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                ModelProd = Arrays.asList(gson.fromJson(response, Search_Model[].class));
                                setimageurl();
                                setRecyclerView();
                                mRecyclerView.setVisibility(View.GONE);

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Map<String, String> params = new HashMap<String, String>();
                    //
                    params.put("idsarray", j);

                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            request.add(rRequest);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    //Setting RecyclerView
    private void setRecyclerView() {
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mSearch_adapter = new Search_Adapter(getApplicationContext(), ModelProd);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSearch_adapter);
    }

    //Setting Images
    private void setimageurl() {
        int n = 0;
        for (Search_Model i : ModelProd) {
            i.setThumbnail(hostinglink + i.getThumbnail());
            // list.remove(n);
            ModelProd.set(n, i);
            n++;
        }
    }

}