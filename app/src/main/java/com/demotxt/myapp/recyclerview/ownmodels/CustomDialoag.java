package com.demotxt.myapp.recyclerview.ownmodels;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demotxt.myapp.recyclerview.R;



public class CustomDialoag {
  private   Context context;
   public CustomDialoag(Context cntx)
    {
        context=cntx;
    }

    public void showCustomDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.customdialoag);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((Button) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckConnection obj=new CheckConnection(context);
                Boolean isconnected=obj.CheckConnection();
                if(isconnected){
                    dialog.dismiss();
                }
                //Toast.makeText(getApplicationContext(), ((Button) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();

            }
        });
       /* ((TextView) dialog.findViewById(R.id.title)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(),  " Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        */



        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
