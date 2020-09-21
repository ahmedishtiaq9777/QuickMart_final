package com.demotxt.myapp.Quickmart.ownmodels;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.demotxt.myapp.Quickmart.R;

import java.util.Objects;

public class CustomInternetDialog {
  private final Context context;
   public CustomInternetDialog(Context cntx)
    {
        context=cntx;
    }

    public void showCustomDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((Button) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckConnect obj=new CheckConnect(context);
                Boolean isconnected=obj.CheckConnection();
                if(isconnected){
                    dialog.dismiss();
                }
                //Toast.makeText(getApplicationContext(), ((Button) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
