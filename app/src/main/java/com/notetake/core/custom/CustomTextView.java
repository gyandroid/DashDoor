package com.notetake.core.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createFont();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public CustomTextView(Context context) {
        super(context);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "robo.ttf");
        setTypeface(font);
    }
}