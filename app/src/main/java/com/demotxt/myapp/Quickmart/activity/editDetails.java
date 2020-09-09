package com.demotxt.myapp.Quickmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class editDetails extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    Button update;
    TextView t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDNAME, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDPHONE, "^0(?=3)[0-9]{10}$", R.string.error_contact);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDEMAIL, android.util.Patterns.EMAIL_ADDRESS.toString(), R.string.error_email);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDADDRESS, RegexTemplate.NOT_EMPTY, R.string.error_address);

        update = (Button) findViewById(R.id.UPDATE);
        t1 = (EditText) findViewById(R.id.UPDATEDNAME);
        t2 = (EditText) findViewById(R.id.UPDATEDEMAIL);
        t3 = (EditText) findViewById(R.id.UPDATEDPHONE);
        t4 = (EditText) findViewById(R.id.UPDATEDADDRESS);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    String name = t1.getText().toString();
                    String email = t2.getText().toString();
                    String contact = t3.getText().toString();
                    String address = t4.getText().toString();
                    DetailModel m = new DetailModel(name, email, contact, address);
                } else {
                    Toast.makeText(editDetails.this, "clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}