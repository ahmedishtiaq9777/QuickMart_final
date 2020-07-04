package com.demotxt.myapp.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.Order.Order_Adapter;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.model.OrderViewImg;

import java.util.ArrayList;
import java.util.List;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder> {
    private List<OrderViewImg> mOrderList;

    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
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
        OrderViewImg currentItem = mOrderList.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.tit.setText(currentItem.getTitle());
        holder.qty.setText(String.valueOf(currentItem.getQuantity()));
        holder.pri.setText(String.valueOf(currentItem.getPrice()));



    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
