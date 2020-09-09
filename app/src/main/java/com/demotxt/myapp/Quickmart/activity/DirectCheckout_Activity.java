package com.demotxt.myapp.Quickmart.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.UserModel;

public class DirectCheckout_Activity extends AppCompatActivity {

    EditText Name, Phone, Address;
    RadioButton COD;
    Button Confirm;
    CardView mCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_checkout_);

        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.address);
        Address = findViewById(R.id.phoneNumber);
        COD = findViewById(R.id.Cod_Rd);
        Confirm = findViewById(R.id.ConfirmBtn);
        mCardView = findViewById(R.id.include);


        UserModel model = new UserModel();

        Name.setText(model.getUserName());
        Phone.setText(model.getPhone());
        Address.setText(model.getAddress());


    }
}