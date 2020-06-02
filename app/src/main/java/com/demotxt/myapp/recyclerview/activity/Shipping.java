package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.recyclerview.MainActivity;
import com.demotxt.myapp.recyclerview.R;
import com.google.android.material.textfield.TextInputLayout;


import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.text.Regex;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class Shipping extends AppCompatActivity {

    EditText t1,t2,t3,t4,t5;
    Button ship;
    AwesomeValidation awesomeValidation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping);


        awesomeValidation = new AwesomeValidation(BASIC);

        awesomeValidation.addValidation(Shipping.this, R.id.nameShip, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Shipping.this, R.id.emailShip, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
        awesomeValidation.addValidation(Shipping.this, R.id.contactShip, "^[0-9]{11}", R.string.error_contact);
        awesomeValidation.addValidation(Shipping.this, R.id.addShip, RegexTemplate.NOT_EMPTY, R.string.error_address);
        awesomeValidation.addValidation(Shipping.this, R.id.zipShip, "^[0-9]{5}", R.string.error_zip);



        ship=(Button)findViewById(R.id.button1);
        t1=(EditText) findViewById(R.id.nameShip);
        t2=(EditText) findViewById(R.id.emailShip);
        t3=(EditText) findViewById(R.id.contactShip);
        t4=(EditText) findViewById(R.id.addShip);
        t5=(EditText) findViewById(R.id.zipShip);



        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(awesomeValidation.validate()) {


                    String name = t1.getText().toString();
                    String email = t2.getText().toString();
                    String contact = t3.getText().toString();
                    String address = t4.getText().toString();
                    String code = t5.getText().toString();
                    Intent i = new Intent(getBaseContext(), Confirmation.class);
                    i.putExtra("getname", name);
                    i.putExtra("getaddress", address);
                    startActivity(i);

                }else {

                    //Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
