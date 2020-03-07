package com.demotxt.myapp.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.activity.Prod_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewProdAdapter extends RecyclerView.Adapter<RecyclerViewProdAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Prod> Data1 ;


    public RecyclerViewProdAdapter(Context mContext, List<Prod> data1) {
        this.mContext = mContext;
        this.Data1 = data1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_prod,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(Data1.get(position).getTitle());
       // holder.img_book_thumbnail.setImageResource(Data1.get(position).getThumbnail());
        Picasso.get().load(Data1.get(position).getThumbnail()).into(holder.img_book_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Prod_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",Data1.get(position).getTitle());
                intent.putExtra("Description",Data1.get(position).getDescription());
                intent.putExtra("Thumbnail",Data1.get(position).getThumbnail());
                intent.putExtra("price",Data1.get(position).getPrice());
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return Data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview2);


        }
    }

}
