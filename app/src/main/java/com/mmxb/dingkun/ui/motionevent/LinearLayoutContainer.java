package com.mmxb.dingkun.ui.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.mmxb.dingkun.R;

/**
 * Created by mmxb on 5/18/21.
 */
public class LinearLayoutContainer extends LinearLayout {
    private int y;

    public LinearLayoutContainer(Context context) {
        this(context, null);
    }

    public LinearLayoutContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.item_linear_layout, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MotionEventActivity.TAG, "onTouch: LinearLayoutContainer");
        performClick();
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                return Math.abs(y - ev.getY()) > 0;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
