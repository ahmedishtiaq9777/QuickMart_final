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

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.r3;
import com.demotxt.myapp.recyclerview.activity.r3_Activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerView3 extends RecyclerView.Adapter<RecyclerView3.MyViewHolder> {

    private Context mContext ;
    private List<r3> Data2 ;


    public RecyclerView3(Context mContext, List<r3> data) {
        this.mContext = mContext;
        this.Data2 = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(Data2.get(position).getTitle());
       // holder.img_book_thumbnail.setImageResource(Data2.get(position).getThumbnail());
        Picasso.get().load(Data2.get(position).getThumbnail()).into(holder.img_book_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, r3_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",Data2.get(position).getTitle());
                intent.putExtra("Description",Data2.get(position).getDescription());
                intent.putExtra("Thumbnail",Data2.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return Data2.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview_id);


        }
    }

}
