package com.demotxt.myapp.recyclerview.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.demotxt.myapp.recyclerview.R;


public class FragmentShipping extends Fragment {
    private EditText e1;
    private EditText e2;
    private String s1,s2;
    Button b1;

    public FragmentShipping() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shipping, container, false);
        e1= (EditText) root.findViewById(R.id.nameShip);
        e2=(EditText)root.findViewById(R.id.addShip);


        return root;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            s1=e1.getText().toString();
            s2=e2.getText().toString();

            FragmentTransaction transection=getFragmentManager().beginTransaction();
            FragmentConfirmation cFragment = new FragmentConfirmation();

            //using Bundle to send data
            Bundle bundle=new Bundle();
            bundle.putString("name",s1);
            bundle.putString("address",s2);
            cFragment.setArguments(bundle); //data being send to SecondFragment
            transection.replace(R.id.shipline,cFragment);
            transection.commit();


        }
    };


}
