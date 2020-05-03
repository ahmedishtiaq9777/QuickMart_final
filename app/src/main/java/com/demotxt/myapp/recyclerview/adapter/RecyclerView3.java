package com.demotxt.myapp.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.r3;
import com.demotxt.myapp.recyclerview.activity.r3_Activity;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerView3 extends RecyclerView.Adapter<RecyclerView3.MyViewHolder> {

    private Context mContext;
    private List<r3> Data2;

    private SharedPreferences cartpreferrence;
    private SharedPreferences.Editor cartprefEditor;
    private boolean isblack;
    //  private List<Integer> Ids;
    public Set<String> ids;


    public RecyclerView3(Context mContext, List<r3> data) {
        this.mContext = mContext;
        this.Data2 = data;
        ids = new HashSet<String>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        cartpreferrence = mContext.getSharedPreferences("favpref", MODE_PRIVATE);
        cartprefEditor = cartpreferrence.edit();
        ids = cartpreferrence.getStringSet("ids", ids);

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_r3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        int ID = Data2.get(position).getId();
        String strID = String.valueOf(ID);
        isblack = cartpreferrence.getBoolean(strID, false);
        if (isblack == true) {
            holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        holder.tv_r3_title.setText(Data2.get(position).getTitle());
        holder.r3_price.setText(Data2.get(position).getPrice());
        // holder.img_book_thumbnail.setImageResource(Data2.get(position).getThumbnail());
        Picasso.get().load(Data2.get(position).getThumbnail()).into(holder.img_r3_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, r3_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title", Data2.get(position).getTitle());
                intent.putExtra("Description", Data2.get(position).getDescription());
                intent.putExtra("Thumbnail", Data2.get(position).getThumbnail());
                intent.putExtra("price", Data2.get(position).getPrice());
                intent.putExtra("proid", Data2.get(position).getId());
                // start the activity
                mContext.startActivity(intent);

            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    int ID = Data2.get(position).getId();// get selected product Id
                    String strID = String.valueOf(ID);// convert to String
                    isblack = cartpreferrence.getBoolean(strID, false);// Check whether selected product is black or not
                    if (isblack == true) {
                        holder.heart.setImageResource(R.drawable.ic_favorite_border_24dp);
                        ids.remove(strID);
                        cartprefEditor.putBoolean(strID, false);


                    } else {
                        holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp);
                        cartprefEditor.putBoolean(strID, true);
                        ids.add(strID);
                        //  Log.i("message", "length: " +ids.size());
                        //  Toast.makeText(, "error:", Toast.LENGTH_SHORT).show();

                    }
                    cartprefEditor.putStringSet("ids", ids);
                    cartprefEditor.commit();

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return Data2.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_r3_title, r3_price;
        ImageView img_r3_thumbnail, heart;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_r3_title = itemView.findViewById(R.id.r3_title_id);
            img_r3_thumbnail = itemView.findViewById(R.id.r3_img_id);
            r3_price = itemView.findViewById(R.id.r3_prod_price);
            cardView = itemView.findViewById(R.id.cardview_id_r3);
            heart = itemView.findViewById(R.id.heart);


        }
    }

}
