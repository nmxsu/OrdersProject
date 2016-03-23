package com.fat246.orders.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/3/5.
 */
public class MyApplication extends Application {

    /**
     * 表示  服务器的地址的全局变量
     */
    private static final String SERVER_IP="192.168.56.1";
    private static final String SERVER_PORT="8080";
    private static final String SERVER_NAME="Service1.asmx";
    private static final String PRE_URL="http://"+SERVER_IP+":"+SERVER_PORT+"//"+SERVER_NAME;

    //订单地址
    private static String ALLORDERSLIST_SERVER="getAllOrderList";
    private static String ALLORDERSLIST_URL=PRE_URL+"//"+ALLORDERSLIST_SERVER;

    //申请单地址
    private static String ALLAPPLYSLIST_SERVER = "getAllApplyList";
    private static String ALLAPPLYSLIST_URL=PRE_URL+"//"+ALLAPPLYSLIST_SERVER;


    //登陆要用到的URL 这个要配合到  URL前缀一起使用
    private static final String LOGIN_SERVER="isLogin";
    private static String LOGIN_URL=PRE_URL+"//"+LOGIN_SERVER;;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    //获得订单地址
    public static String getAllorderslistUrl(){return ALLORDERSLIST_URL;}

    //返回申请单
    public static String getAllapplyslistUrl(){return ALLAPPLYSLIST_URL;}

    //获得登陆的地址
    public static String getLoginUrl(){return LOGIN_URL;}
}
