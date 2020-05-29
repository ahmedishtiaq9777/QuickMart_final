package com.demotxt.myapp.recyclerview.Order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.CategoryFragments.Catkids;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.OrderViewHolder>{

    private Context mContext;
    private List<Order> mData;

    //  private List<Integer> Ids;
    public Set<String> ids;

    public Order_Adapter(Context mContext, List<Order> mdata){
        this.mContext=mContext;
        this.mData = mdata;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_order, parent, false);
        return new Order_Adapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {

        holder.Order_Id.setText(mData.get(position).getOrderId());
        holder.Order_Price.setText(mData.get(position).getPrice());
        holder.Order_Detail.setText(mData.get(position).getOrderDetail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder   {

        TextView Order_Id,Order_Price,Order_Detail;
        CardView cardView;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            Order_Id = itemView.findViewById(R.id.Order_Id);
            Order_Price = itemView.findViewById(R.id.Order_Price);
            Order_Detail = itemView.findViewById(R.id.Order_Detail);
            cardView = itemView.findViewById(R.id.order_cardview);
        }
    }


}
