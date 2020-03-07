package com.demotxt.myapp.recyclerview.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private ListView listview;

    Typeface fonts1, fonts2;

    private int[] IMAGE = {R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo,
            R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo, R.drawable.shoppy_logo};

    private String[] TITLE = {"Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set"};


    private String[] PRICE = {"$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00"};
    private ArrayList<CartListBeanlist> Bean;
    private CartListBaseAdapter baseAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.favoritefragment, container, false);
        listview = (ListView) view.findViewById(R.id.listview);


        Bean = new ArrayList<CartListBeanlist>();

        for (int i = 0; i < TITLE.length; i++) {

            CartListBeanlist bean = new CartListBeanlist(IMAGE[i], TITLE[i], PRICE[i]);
            Bean.add(bean);

        }


        baseAdapter = new CartListBaseAdapter(getActivity(), Bean) {
        };

        listview.setAdapter(baseAdapter);


        // Inflate the layout for this fragment
        return view;
    }
}
