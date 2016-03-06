package com.fat246.orders.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fat246.orders.R;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.fragment.FirstLoadingPageFragment;
import com.fat246.orders.fragment.LoadingPageFragment;

public class LoadingPage extends AppCompatActivity {

    //是否是第一次登陆
    private boolean isFirstLoading;

    //官方网站
    public static String official_website="http://www.fat246.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        if (findViewById(R.id.loading_page_frame_layout)!=null){
            if(savedInstanceState!=null){
                return;
            }
        }

        //判断是否是第一次登陆
        isFirstLoading=isFirstLoad();

        toAddFragment();
    }

    //添加Fragment
    private void toAddFragment(){

        Fragment mFragment;
        if (isFirstLoading){

            mFragment=new FirstLoadingPageFragment();
        }else {

            mFragment=new LoadingPageFragment();
        }

        //添加到 Fragment Container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.loading_page_frame_layout, mFragment).commit();
    }

    //判断是不是第一次启动程序
    public boolean isFirstLoad(){

        //首先得到SharedPreferences
        SharedPreferences mSP=getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

        boolean isFirst=mSP.getBoolean("isFirstLoad",true);

        if(isFirst){
            SharedPreferences.Editor mEditor=mSP.edit();
            mEditor.putBoolean("isFirstLoad", false);

            //提交
            mEditor.commit();

            isFirstLoading=true;
            return true;
        }else {

            isFirstLoading=false;
            return false;
        }
    }

    @Override
    public void onBackPressed() {

        this.finish();
    }
}
