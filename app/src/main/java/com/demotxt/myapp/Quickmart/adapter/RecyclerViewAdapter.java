package com.demotxt.myapp.Quickmart.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.myapp.Quickmart.activity.AboutSeller_Activity;
import com.demotxt.myapp.Quickmart.activity.TabsBasic;
import com.demotxt.myapp.Quickmart.ownmodels.Book;
import com.demotxt.myapp.Quickmart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.BIND_NOT_FOREGROUND;
import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Book> mData;
    private List<Book> mDataFull;
    int userid;
    String img;
    String name,add,contact;
    //
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    public Set<String> ids;

    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull = new ArrayList<>(mData);
        ids = new HashSet<String>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cartpreferrence = mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids = cartpreferrence.getStringSet("ids", ids);


        //

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getTitle());
        holder.tv_book_dist.setText(mData.get(position).getDistance());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_book_thumbnail);
        holder.mRatingBar.setRating(mData.get(position).getRating());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userid = mData.get(position).getUserId();
                Toast.makeText(mContext.getApplicationContext(),"seller id:"+userid,Toast.LENGTH_LONG).show();
                //Starting Activity To show Category Activity
                Intent intent = new Intent(mContext, TabsBasic.class);
                intent.putExtra("sellerid",userid);
                mContext.startActivity(intent);

            }
        });
        // To show Seller Information
        holder.Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent infoIntent = new Intent(mContext, AboutSeller_Activity.class);
                infoIntent.putExtra("sellerid",mData.get(position).getUserId());
                infoIntent.putExtra("ShopName",mData.get(position).getTitle());
                infoIntent.putExtra("ShopImg",mData.get(position).getThumbnail());
                infoIntent.putExtra("Contact",mData.get(position).getContact());
                infoIntent.putExtra("address",mData.get(position).getAddress());
                infoIntent.putExtra("rating",mData.get(position).getRating());

                mContext.startActivity(infoIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title,tv_book_dist;
        ImageView img_book_thumbnail,Info;
        CardView cardView;
        AppCompatRatingBar mRatingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            tv_book_dist = itemView.findViewById(R.id.book_distance);
            cardView = itemView.findViewById(R.id.cardview_id);
            mRatingBar = itemView.findViewById(R.id.shop_RatingBar);
            Info = itemView.findViewById(R.id.info_Btn);

        }
    }

    //For Search Purposes
    @Override
    public Filter getFilter() {
        return mDataFilter;
    }

    private Filter mDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Book> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Book item : mDataFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData = new ArrayList<>();
            mData.addAll( (List) results.values);
            notifyDataSetChanged();

        }
    };



}
