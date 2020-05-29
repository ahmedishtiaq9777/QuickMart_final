package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    EditText t1,t2,t3,t4,t5,t6;
    Button ship;
    AwesomeValidation awesomeValidation;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping);


        awesomeValidation = new AwesomeValidation(BASIC);



        awesomeValidation.addValidation(Shipping.this, R.id.nameShip, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Shipping.this, R.id.contactShip, "^[0-9]{10}", R.string.error_contact);
        awesomeValidation.addValidation(Shipping.this, R.id.emailShip, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);




       ship=(Button)findViewById(R.id.button1);
        t1=(EditText) findViewById(R.id.nameShip);
        t2=(EditText) findViewById(R.id.emailShip);
        t3=(EditText) findViewById(R.id.contactShip);
        t4=(EditText) findViewById(R.id.addShip);
        t5=(EditText) findViewById(R.id.zipShip);
        t6=(EditText) findViewById(R.id.stateShip);


        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(awesomeValidation.validate()) {


                    String name = t1.getText().toString();
                    String email = t2.getText().toString();
                    String contact = t3.getText().toString();
                    String address = t4.getText().toString();
                    String code = t5.getText().toString();
                    String state = t6.getText().toString();
                    Intent i = new Intent(getBaseContext(), Confirmation.class);
                    i.putExtra("getname", name);
                    i.putExtra("getaddress", address);
                    startActivity(i);

                }else {



                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }














          /*      if(isEmpty(t1)||isEmail(t2)||isEmpty(t3)||isEmpty(t4)||isEmpty(t5)||isEmpty(t6)) {

                       t1.setError("Name Required");
                       t2.setError("Email Required");
                       t3.setError("Contact Required");
                       t4.setError("Address Required");
                       t5.setError("Zip Required");
                       t6.setError("State Required");
                   }
                   else{




                   }*/


            }
        });
    }



   /* boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void CheckDataEntered() {
        if(isEmpty(t1)){
            t1.setError("Name Required");
        }
        if(isEmpty(t2)){
            t2.setError("Email Required");

        }
        if(isEmpty(t3)){
            t3.setError("Contact Required");

        }
        if(isEmpty(t4)){
            t4.setError("Address Required");

        }
        if(isEmpty(t5)){
            t5.setError("Zip Code Required");

        }
        if(isEmpty(t6)){
            t6.setError("State Required");

        }*/




}
