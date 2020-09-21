package com.demotxt.myapp.Quickmart.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class editDetails extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    Button update,confirm,NameUpdate,AddUpdate;
    ImageView EditName_Btn,EditAdd_Btn;
    TextView t1,t3, t4,Pass,Pass_Again;
    EditText UpAdd_txt,UpName_txt;
    String name,contact,address;
    ConstraintLayout lyt_detail,lyt_pass,lyt_ChangeName,lyt_ChangeAdd;
    private SharedPreferences loginpref,UserPref;
    SharedPreferences.Editor mEditor;
    String userid;
    StringResponceFromWeb result;
    String Add,Na;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        //
        result = new StringResponceFromWeb();
        //
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDNAME, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDPHONE, "^0(?=3)[0-9]{10}$", R.string.error_contact);
        awesomeValidation.addValidation(editDetails.this, R.id.UPDATEDADDRESS, RegexTemplate.NOT_EMPTY, R.string.error_address);
        //
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        UserPref = getSharedPreferences("UserPref",MODE_PRIVATE);
        mEditor = UserPref.edit();
        //
        confirm = (Button) findViewById(R.id.UPDATE);
        t1 = (EditText) findViewById(R.id.UPDATEDNAME);
        t3 = (EditText) findViewById(R.id.UPDATEDPHONE);
        t4 = (EditText) findViewById(R.id.UPDATEDADDRESS);
        Pass = findViewById(R.id.Pass_1);
        Pass_Again = findViewById(R.id.Pass_1_again);
        lyt_detail = findViewById(R.id.lyt_detail);
        lyt_pass = findViewById(R.id.lyt_update);
        update = findViewById(R.id.con_NewDetail_btn);
        EditName_Btn = findViewById(R.id.edit_Name_btn);
        EditAdd_Btn = findViewById(R.id.edit_Address_btn);
        lyt_ChangeAdd = findViewById(R.id.lyt_changeAddress);
        UpAdd_txt = findViewById(R.id.Address_TxT);
        AddUpdate = findViewById(R.id.UpdateAdd_Btn);
        lyt_ChangeName = findViewById(R.id.lyt_changeUserName);
        UpName_txt = findViewById(R.id.Name_TxT);
        NameUpdate = findViewById(R.id.UpdateName_Btn);
        //
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    name = t1.getText().toString();
                    address = t4.getText().toString();
                } else {
                    Toast.makeText(editDetails.this, "clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //Name Edit Button
        EditName_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeName();
                lyt_ChangeName.setVisibility(View.VISIBLE);
            }
        });

        //Address Edit Button
        EditAdd_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAdd();
                lyt_ChangeAdd.setVisibility(View.VISIBLE);
            }
        });

    }

    //Address
    private void ChangeAdd(){
        Add = UpAdd_txt.getText().toString();

        AddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t4.setText(Add);
                lyt_ChangeAdd.setVisibility(View.GONE);
            }
        });
    }

    //Name
    private void ChangeName(){
        Na = UpName_txt.getText().toString();

        NameUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setText(Na);
                lyt_ChangeName.setVisibility(View.GONE);
            }
        });
    }

/*

    private void UpdateProfile(String url){

        userid = String.valueOf(loginpref.getInt("userid", 0));

        try{
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // enjoy your error status
                Toast.makeText(getApplicationContext(), "Error in Volley", Toast.LENGTH_SHORT).show(); }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userId", userid);

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
        }catch (Exception e){
            Toast.makeText(this, "Exception Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
*/


}