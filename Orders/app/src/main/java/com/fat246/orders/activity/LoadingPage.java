package com.fat246.orders.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fat246.orders.R;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.fragment.FirstLoadingPageFragment;
import com.fat246.orders.fragment.LoadingPageFragment;
import com.fat246.orders.services.MyService;

public class LoadingPage extends AppCompatActivity {

    //是否是第一次登陆
    private boolean isFirstLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        if (findViewById(R.id.loading_page_frame_layout) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        //启动悬浮窗口的服务
        startService();

        //判断是否是第一次登陆
        isFirstLoading = isFirstLoad();

        toAddFragment();
    }

    //启动服务
    private void startService() {

        Intent mIntent = new Intent(LoadingPage.this, MyService.class);
        startService(mIntent);
    }

    //添加Fragment
    private void toAddFragment() {

        Fragment mFragment;
        if (isFirstLoading) {

            mFragment = new FirstLoadingPageFragment();
        } else {

            mFragment = new LoadingPageFragment();
        }

        //添加到 Fragment Container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.loading_page_frame_layout, mFragment).commit();
    }

    //判断是不是第一次启动程序
    public boolean isFirstLoad() {

        //首先得到SharedPreferences
        SharedPreferences mSP = getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

        boolean isFirst = mSP.getBoolean("isFirstLoad", true);

        if (isFirst) {

            SharedPreferences.Editor mEditor = mSP.edit();
            mEditor.putBoolean("isFirstLoad", false);

            //提交
            mEditor.apply();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {

        this.finish();
    }
}
