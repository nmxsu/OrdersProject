package com.fat246.orders.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fat246.orders.R;
import com.fat246.orders.activity.LoginPage;
import com.fat246.orders.activity.MainPage;
import com.fat246.orders.bean.ApplyInfo;
import com.fat246.orders.bean.OrderInfo;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.parser.AllApplyListParser;
import com.fat246.orders.parser.AllOrdersListParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public class ManageFloatBigView {

    private String ALLORDERSLIST_URL;
    private String ALLAPPLYSLIST_URL;

    private static ManageFloatBigView mManageFloatBigView;

    public static boolean isShowing = false;

    //用户信息
    private UserInfo mUserInfo;

    public static ManageFloatBigView getManageFloatBigView(Context context) {

        if (mManageFloatBigView == null) {

            mManageFloatBigView = new ManageFloatBigView(context);
        }

        return mManageFloatBigView;
    }

    private Context mContext;
    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mParams;

    private View rootView;

    private int screenWidth;
    private int screenHeight;

    //List
    private ListView mList;

    //数据适配器
    private ListDataAdapter mAdapter;

    //是否是给 订单加载 数据
    private boolean isOrdersData=true;

    //数据
    private List mData=new ArrayList();


    private ManageFloatBigView(Context context) {

        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        createView();
    }

    //创建视图
    private void createView() {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        rootView = layoutInflater.inflate(R.layout.service_float_layout_larger, null);

        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.service_float_big_linearlayout);


        setSomething(rootView);

        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.width = ll.getLayoutParams().width;
        mParams.height = ll.getLayoutParams().height;

        Log.e("width", mParams.width + "");
        Log.e("height", mParams.height + "");

        getScreen();

        mParams.x = screenWidth / 2 - mParams.width / 2;
        mParams.y = screenHeight / 2 - mParams.height / 2;
    }

    private void getScreen() {

        DisplayMetrics dm = new DisplayMetrics();

        mWindowManager.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    private void setSomething(final View rootView) {

        ImageButton ib = (ImageButton) rootView.findViewById(R.id.service_float_big_button_cancle);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWindowManager.removeViewImmediate(rootView);

                isShowing = false;
                ManageFloatSmallView.getManageFloatSmallView(mContext).addView();

            }
        });

        ImageButton ibHome = (ImageButton) rootView.findViewById(R.id.service_float_big_button_home);
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent;

                if (isLogin()) {
                    mIntent = new Intent(mContext, MainPage.class);
                } else {
                    mIntent = new Intent(mContext, LoginPage.class);
                }

                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(mIntent);

                ManageFloatBigView.this.removeView();
                ManageFloatSmallView.getManageFloatSmallView(mContext).addView();
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


        //找到 list
        mList=(ListView) rootView.findViewById(R.id.service_float_big_button_list);

        mAdapter=new ListDataAdapter();

        //加载数据
        setListData();

        //设置适配器
        mList.setAdapter(mAdapter);
    }

    //给ListView 加载数据  默认是  加载 订单数据
    private void setListData(){

        if (isOrdersData){

            //通过AllOrdersListParser 对象  解析 xml 数据
            mData=new AllOrdersListParser(mUserInfo,ALLORDERSLIST_URL).getAllOrdersList();
        }else {

            //下载并解析
            mData= new AllApplyListParser(mUserInfo,ALLAPPLYSLIST_URL).getAllApplyList();
        }
    }

    private boolean isLogin() {
        //首先得到SharedPreferences
        SharedPreferences mSP = mContext
                .getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

        int code = mSP.getInt("operationValue", 99);

        if (code>=1&&code<=3) return true;
        else return false;
    }

    public void removeView() {

        mWindowManager.removeViewImmediate(rootView);
        isShowing = false;
    }

    public void addView() {
        mWindowManager.addView(rootView, mParams);
        isShowing = true;

        //重新获得用户信息
        getUserInfo();
    }

    //
    public void getUserInfo() {

        //首先得到SharedPreferences
        SharedPreferences mSP = mContext
                .getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

        //读取UserInfo信息
        mUserInfo= new UserInfo(
                mSP.getString("mUser", ""),
                mSP.getString("mPassword", ""),
                mSP.getBoolean("isSavePassword", false),
                mSP.getBoolean("isAutoLogIn", false),
                mSP.getInt("operationValue",99)
        );
    }


    //ListView 的  数据适配器
    class ListDataAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater=LayoutInflater.from(mContext);

            if (isOrdersData){

                convertView=layoutInflater.inflate(R.layout.service_float_layout_larger_orders_item,null);

                setOrdersConvertView(convertView,position);
            }else {

                convertView=layoutInflater.inflate(R.layout.service_float_layout_larger_applys_item,null);

                setApplysConvertView(convertView,position);
            }
            return convertView;
        }

        private void setOrdersConvertView(View convertView,int position){

            TextView mText=(TextView)convertView.findViewById(R.id.service_float_big_item_orders_ord);

            mText.setText(((OrderInfo)(mData.get(position))).getPRHSORD_ID());
        }

        private void setApplysConvertView(View convertView,int position){

            TextView mText=(TextView)convertView.findViewById(R.id.service_float_big_applys_prd);

            mText.setText(((ApplyInfo)(mData.get(position))).getPRHS_ID());
        }
    }
}
