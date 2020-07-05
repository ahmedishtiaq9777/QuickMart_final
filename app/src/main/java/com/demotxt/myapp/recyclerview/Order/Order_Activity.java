package com.demotxt.myapp.recyclerview.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.CategoryFragments.CatKids_Adapter;
import com.demotxt.myapp.recyclerview.CategoryFragments.Catkids;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.TabsBasic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_Activity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Order_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ConstraintLayout ExplandableView;
    private Button Arrow_Down;
    private CardView mCardView;
    View  v2;
    private List<Order> mOrderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);
        mOrderList = new ArrayList<>();
        //
        ExplandableView = findViewById(R.id.expandable_list);
        Arrow_Down = findViewById(R.id.dropdown);
        mCardView = findViewById(R.id.order_cardview);
        mRecyclerView = findViewById(R.id.Order_Recyclerview);


        mOrderList.add(new Order("1","23/6/20","Shoes,Shirt","50$"));
        mOrderList.add(new Order("2","23/6/20","Shirt","30$"));
        mOrderList.add(new Order("3","23/6/20","Pants,Shirt","20$"));
        mOrderList.add(new Order("4","23/6/20","Pants,Shirt","65$"));
        //
        ExplandableView = findViewById(R.id.expandable_list);
        mCardView = findViewById(R.id.order_cardview);
        setadapterRecyclerView();



       /* v2 = getLayoutInflater().inflate(R.layout.cardview_item_order,null);

        //Drop Down
        Arrow_Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExplandableView.getVisibility()==v2.GONE){
                    TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());
                    ExplandableView.setVisibility(v2.VISIBLE);
                    Arrow_Down.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                else{
                    TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());
                    ExplandableView.setVisibility(v2.GONE);
                    Arrow_Down.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });*/

/*
        //int   sid=getArguments().getInt("user_id");
        TabsBasic activity = (TabsBasic) getActivity();// get acticity data
        int sid = activity.getuserid();
        String userid = String.valueOf(sid);
        String url = "http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/getprowithsellerid";

        getconnection(url, userid);

    */
    }
/*
    public void getconnection(String url, final String User_Id) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            mOrderList = Arrays.asList(gson.fromJson(response, Order[].class));
                            setadapterRecyclerView();

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
                params.put("userid", User_Id);

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        request.add(rRequest);


    }*/

    private void setadapterRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Order_Adapter(getApplicationContext(),mOrderList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
