package com.demotxt.myapp.recyclerview.fragment;



import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.demotxt.myapp.recyclerview.R;

import static com.pchmn.materialchips.R2.id.container;


public class FragmentConfirmation extends Fragment {

    private TextView mname,maddress;

    public FragmentConfirmation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_confirmation, container, false);


        mname = (TextView) root.findViewById(R.id.getname);
        maddress = (TextView)root.findViewById(R.id.getaddress);

        //retrieving data using bundle
       // Bundle bundle=getArguments();
      //  mname.setText(String.valueOf(bundle.getString("name")));
       // maddress.setText(String.valueOf(bundle.getString("address")));

        return root;
    }
}