package com.demotxt.myapp.recyclerview.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by one on 3/12/15.
 */
public class MyEditText extends  AppCompatTextView {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
            setTypeface(tf);
        }
    }

}