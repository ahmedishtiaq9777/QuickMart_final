package com.demotxt.myapp.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.myapp.recyclerview.activity.TabsBasic;
import com.demotxt.myapp.recyclerview.ownmodels.Book;
import com.demotxt.myapp.recyclerview.activity.Book_Activity;
import com.demotxt.myapp.recyclerview.R;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;
    //
    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    public Set<String> ids;

    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;

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
        //holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_book_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To send data to next frag
              //  Fragment fragment = new Fragment();
                //Bundle b = new Bundle();
int userid= mData.get(position).getUserId();
Toast.makeText(mContext.getApplicationContext(),"seller id:"+userid,Toast.LENGTH_LONG).show();
         // String T= mData.get(position).getTitle();
               /* if(ShopID == "Outfitter") {
                  //  b.putInt("", );
                    fragment.setArguments(b);
                }

*/

                //Starting Activity To show Category Activity
                Intent intent = new Intent(mContext, TabsBasic.class);
                intent.putExtra("sellerid",userid);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = itemView.findViewById(R.id.book_img_id);
            cardView = itemView.findViewById(R.id.cardview_id);


        }
    }

}
