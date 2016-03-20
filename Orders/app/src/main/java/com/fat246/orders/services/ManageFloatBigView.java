package com.fat246.orders.services;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fat246.orders.R;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ManageFloatBigView {

    private static ManageFloatBigView mManageFloatBigView;

    public static ManageFloatBigView getManageFloatBigView(Context context) {

        if (mManageFloatBigView == null) {

            mManageFloatBigView = new ManageFloatBigView(context);
        }else {

            mManageFloatBigView.mWindowManager.addView(mManageFloatBigView.rootView,
                    mManageFloatBigView.mParams);
        }

        return mManageFloatBigView;
    }

    private Context mContext;
    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mParams;

    private View rootView;

    private int screenWidth;
    private int screenHeight;


    private ManageFloatBigView(Context context) {

        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        createView();
    }

    //创建视图
    private void createView() {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        rootView = layoutInflater.inflate(R.layout.service_float_layout_larger, null);

        LinearLayout ll=(LinearLayout)rootView.findViewById(R.id.service_float_big_linearlayout);


        setSomething(rootView);

        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.width =ll.getLayoutParams().width ;
        mParams.height = ll.getLayoutParams().height;

        Log.e("width",mParams.width+"");
        Log.e("height",mParams.height+"");

        getScreen();

        mParams.x = screenWidth/2-mParams.width/2;
        mParams.y = screenHeight/2-mParams.height/2;

        mWindowManager.addView(rootView, mParams);
    }

    private void getScreen(){

        DisplayMetrics dm=new DisplayMetrics();

        mWindowManager.getDefaultDisplay().getMetrics(dm);

        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels;
    }

    private void setSomething(final View rootView){

        ImageButton ib=(ImageButton)rootView.findViewById(R.id.service_float_big_button_cancle);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWindowManager.removeViewImmediate(rootView);
                ManageFloatSmallView.getManageFloatSmallView(mContext);
            }
        });

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
    }
}
