package com.demotxt.myapp.recyclerview.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListActivity;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBaseAdapter;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;

import java.util.ArrayList;


public class CartFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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
       View view= inflater.inflate(R.layout.fragment_cart, container, false);
        listview = (ListView) view.findViewById(R.id.listview);


        Bean = new ArrayList<CartListBeanlist>();

       /* for (int i = 0; i < TITLE.length; i++) {

            CartListBeanlist bean = new CartListBeanlist(IMAGE[i], TITLE[i], PRICE[i]);
            Bean.add(bean);

        }


        baseAdapter = new CartListBaseAdapter(getActivity(), Bean) {
        };

        listview.setAdapter(baseAdapter);
*/

        // Inflate the layout for this fragment
        return view;
    }


}
