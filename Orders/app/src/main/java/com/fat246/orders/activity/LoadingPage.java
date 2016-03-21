package com.fat246.orders.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.fat246.orders.R;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.fragment.FirstLoadingPageFragment;
import com.fat246.orders.fragment.LoadingPageFragment;
import com.fat246.orders.services.MyService;

public class LoadingPage extends AppCompatActivity {

    //是否是第一次登陆
    private boolean isFirstLoading;

    //获得线程权限
    public static final int REQUEST_CODE_GET_TASK=14;

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

        startService();

        //判断是否是第一次登陆
        isFirstLoading=isFirstLoad();

        toAddFragment();
    }

    //启动服务
    private void startService(){

        Intent mIntent=new Intent(LoadingPage.this, MyService.class);

        startService(mIntent);
    }

    //运行时权限申请
    private void getPermission() {

        if (true||Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.GET_TASKS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (true||ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.GET_TASKS)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.GET_TASKS},
                            REQUEST_CODE_GET_TASK);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else {
                Log.i("has Permisson","YES");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

            switch (requestCode) {
                case LoadingPage.REQUEST_CODE_GET_TASK: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
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
