package com.demotxt.myapp.Quickmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.Quickmart.ownmodels.Notify;
import com.demotxt.myapp.Quickmart.R;

import java.util.List;

public class Notify_Adapter extends RecyclerView.Adapter<Notify_Adapter.OrderViewHolder>{

    private final Context mContext;
    private final List<Notify> mData;

    public Notify_Adapter(Context mContext, List<Notify> mdata){
        this.mContext=mContext;
        this.mData = mdata;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_order_notifications, parent, false);
        return new Notify_Adapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {

        holder.Detail.setText(mData.get(position).getText());


        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(mData.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder   {

        final TextView Detail;
        final CardView cardView;
        final ImageView cross;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.Notify_CardView);
            Detail = itemView.findViewById(R.id.Txt_NotifyDetail);
            cross = itemView.findViewById(R.id.cancelbtn);


        }
    }


}
