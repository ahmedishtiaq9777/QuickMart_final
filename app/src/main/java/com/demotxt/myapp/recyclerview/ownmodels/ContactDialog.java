package com.demotxt.myapp.recyclerview.ownmodels;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demotxt.myapp.recyclerview.R;

public class ContactDialog extends AppCompatActivity {
    private Context context;


    public ContactDialog(Context cntx) {
        context = cntx;
    }


    public void showContactDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.contactdialog);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
