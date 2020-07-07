package com.demotxt.myapp.recyclerview.Order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class Order_Detail_Adapter extends RecyclerView.Adapter<Order_Detail_Adapter.OrderViewHolder>{

    private Context mContext;
    private List<Order_Detail> mData;

    public Order_Detail_Adapter(Context mContext, List<Order_Detail> mdata){
        this.mContext=mContext;
        this.mData = mdata;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_order_detail_products, parent, false);
        return new Order_Detail_Adapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {

        holder.ProdName.setText(mData.get(position).getTitle());
        holder.ProdPrice.setText(mData.get(position).getPrice());
        holder.ProdQuantity.setText(mData.get(position).getQuantity());
        Picasso.get().load(mData.get(position).getImage()).into(holder.ProdImage);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder   {

        TextView ProdName,ProdQuantity,ProdPrice;
        ImageView ProdImage;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            ProdName = itemView.findViewById(R.id.prodName);
            ProdImage = itemView.findViewById(R.id.prodImage);
            ProdPrice = itemView.findViewById(R.id.prodPrice);
            ProdQuantity = itemView.findViewById(R.id.prodQuantity);
        }
    }


}
