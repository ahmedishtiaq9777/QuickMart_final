package com.demotxt.myapp.recyclerview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.demotxt.myapp.recyclerview.R;


public class FragmentPayment extends Fragment {
    Switch onOffSwitch ;
    TextView t1,t2,t3,t4;
    Spinner s1;

    public FragmentPayment() {
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onOffSwitch = (Switch) view.findViewById(R.id.switchBtn);
        s1 = (Spinner) view.findViewById(R.id.Spcard);
        t1 = (TextView) view.findViewById(R.id.numbertxt);
        t2 = (TextView) view.findViewById(R.id.ntxt);
        t3 = (TextView) view.findViewById(R.id.exptxt);
        t4 = (TextView) view.findViewById(R.id.cvvtxt);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    t1.setFocusable(false);
                    t2.setFocusable(false);
                    t3.setFocusable(false);
                    t4.setFocusable(false);
                    s1.setEnabled(false);
                }
                else {
                    t1.setFocusableInTouchMode(true);
                    t2.setFocusableInTouchMode(true);
                    t3.setFocusableInTouchMode(true);
                    t4.setFocusableInTouchMode(true);
                    s1.setEnabled(true);
                }
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        return root;

    }
}