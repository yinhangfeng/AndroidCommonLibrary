package com.example.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckedLinearLayout extends LinearLayout implements Checkable {

    private Checkable mCheckable;

    public CheckedLinearLayout(Context context) {
        super(context);
    }

    public CheckedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initCheckBox();
    }

    private void initCheckBox() {
        if(mCheckable == null) {
            View v = findViewById(android.R.id.checkbox);
            if(v instanceof Checkable) {
                mCheckable = (Checkable) v;
                v.setClickable(false);
            } else {
//				throw new RuntimeException(
//	                    "must have a Checkable View whose id attribute is " +
//	                    "'android.R.id.checkbox'");
            }
        }
    }

    @Override
    public void toggle() {
        if(mCheckable != null) {
            mCheckable.toggle();
        }
    }

    @Override
    public boolean isChecked() {
        return mCheckable != null ? mCheckable.isChecked() : false;
    }

    @Override
    public void setChecked(boolean checked) {
        //Log.i("CheckedLinearLayout", "setChecked checked=" + checked);
        if(mCheckable != null) {
            mCheckable.setChecked(checked);
        }
    }

}
