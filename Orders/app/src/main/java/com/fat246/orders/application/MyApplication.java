package com.fat246.orders.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/3/5.
 */
public class MyApplication extends Application {

    /**
     * 表示  主机的地址的全局变量
     */
    private static final String SERVER_IP="192.168.56.1";
    private static final String SERVER_PORT="8080";
    private static final String SERVER_NAME="Service1.asmx";
    public static final String PRE_URL="http://"+SERVER_IP+":"+SERVER_PORT+"//"+SERVER_NAME;


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
