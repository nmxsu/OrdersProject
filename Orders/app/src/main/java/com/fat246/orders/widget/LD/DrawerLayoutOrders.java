package com.fat246.orders.widget.LD;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/2/7.
 */
public class DrawerLayoutOrders extends DrawerLayout {

    public DrawerLayoutOrders(Context context) {
        super(context);
    }

    public DrawerLayoutOrders(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerLayoutOrders(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return false;
    }
}
