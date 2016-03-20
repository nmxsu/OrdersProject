package com.fat246.orders.services;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fat246.orders.R;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ManageFloatSmallView {

    private static ManageFloatSmallView mManageFloatSmallView;

    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    private int screenWidth;
    private int screenHeight;


    private View rootView;

    public static ManageFloatSmallView getManageFloatSmallView(Context context) {

        if (mManageFloatSmallView == null) {

            mManageFloatSmallView = new ManageFloatSmallView(context);
        }else {

            mManageFloatSmallView.mWindowManager.addView(mManageFloatSmallView.rootView,
                    mManageFloatSmallView.mParams);
        }

        return mManageFloatSmallView;
    }

    private ManageFloatSmallView(Context context) {

        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        createView();
    }

    //
    private void createView() {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        rootView = layoutInflater.inflate(R.layout.service_float_layout, null);

        ImageView mIcon = (ImageView) rootView.findViewById(R.id.service_icon);

        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.width = mIcon.getLayoutParams().width;
        mParams.height = mIcon.getLayoutParams().height;

        //获得屏幕宽高
        getScreen();

        mParams.x = screenWidth;
        mParams.y = screenHeight/3;

        setListener(rootView);

        mWindowManager.addView(rootView, mParams);
    }

    private void getScreen(){

        DisplayMetrics dm=new DisplayMetrics();

        mWindowManager.getDefaultDisplay().getMetrics(dm);

        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels;
    }

    //设置监听事件
    private void setListener(final View rootView) {

        //设置 touch 监听事件
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mParams.x = (int) event.getRawX() - rootView.getMeasuredWidth() / 2;

                mParams.y = (int) event.getRawY() - rootView.getMeasuredHeight() / 2;


                //刷新
                mWindowManager.updateViewLayout(rootView, mParams);

                return false;
            }
        });

        //设置 click 监听事件
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWindowManager.removeViewImmediate(rootView);

                ManageFloatBigView.getManageFloatBigView(mContext);
            }
        });
    }
}
