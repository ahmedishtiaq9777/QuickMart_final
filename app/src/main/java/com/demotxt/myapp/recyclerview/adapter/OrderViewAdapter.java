package com.demotxt.myapp.recyclerview.adapter;

import android.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.model.Image;
import com.demotxt.myapp.recyclerview.ownmodels.OrderViewImg;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder> {
    private List<OrderViewImg> mOrderList;
    private StringResponceFromWeb result;

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageView plus,minus;
        public TextView tit;
        public TextView qty;
        public TextView pri;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);

            tit = itemView.findViewById(R.id.textTitle);
            qty = itemView.findViewById(R.id.textQty);
            pri = itemView.findViewById(R.id.textPrice);
        }
    }

    public OrderViewAdapter(List<OrderViewImg> orderViewImgs){
        mOrderList = orderViewImgs;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_confirmation,parent,false);
        OrderViewHolder ovh = new OrderViewHolder(v);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final OrderViewImg currentItem = mOrderList.get(position);
        Picasso.get().load(currentItem.getImage()).into(holder.imageView);
       // holder.imageView.setImageResource(currentItem.getImage());
        holder.tit.setText(currentItem.getTitle());
        holder.qty.setText(String.valueOf(currentItem.getQuantity()));
        holder.pri.setText(String.valueOf(currentItem.getPrice()));






    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
