package com.demotxt.myapp.recyclerview.shoppycartlist;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;

///import android.widget.ListView;
 import androidx.appcompat.widget.AppCompatTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.recyclerview.R;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {


    private ListView listview;

    Typeface fonts1,fonts2;

    private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo,
            R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo};

    private String[] TITLE = {"Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set"};


    private String[] PRICE = {"$ 220.00","$ 49.00","$ 320.00","$ 220.00","$ 49.00","$ 320.00","$ 220.00"};
    private ArrayList<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);


        listview = (ListView)findViewById(R.id.listview);


        Bean = new ArrayList<CartListBeanlist>();

        for (int i= 0; i< TITLE.length; i++){

            CartListBeanlist bean = new CartListBeanlist(IMAGE[i], TITLE[i],PRICE[i]);
            Bean.add(bean);

        }


        baseAdapter = new CartListBaseAdapter(CartListActivity.this, Bean) {
        };

        listview.setAdapter(baseAdapter);


    }
}
