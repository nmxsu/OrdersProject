package com.fat246.orders.application;

import android.app.Application;

public class MyApplication extends Application {

    //表示服务器地址的一些全局变量
    private static final String SERVER_IP = "192.168.56.1";
    private static final String SERVER_PORT = "8080";
    private static final String SERVER_NAME = "Service1.asmx";
    private static final String PRE_URL = "http://" + SERVER_IP + ":" + SERVER_PORT + "//" + SERVER_NAME;

    //订单地址
    private static final String ALLORDERSLIST_SERVER = "getAllOrderList";
    private static final String ALLORDERSLIST_URL = PRE_URL + "//" + ALLORDERSLIST_SERVER;

    //申请单地址
    private static final String ALLAPPLYSLIST_SERVER = "getAllApplyList";
    private static final String ALLAPPLYSLIST_URL = PRE_URL + "//" + ALLAPPLYSLIST_SERVER;


    //登陆要用到的URL 这个要配合到  URL前缀一起使用
    private static final String LOGIN_SERVER = "isLogin";
    private static final String LOGIN_URL = PRE_URL + "//" + LOGIN_SERVER;

    //官方网站
    private static final String OFFICIAL_WEBSITE = "http://www.fat246.com";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //获得订单地址
    public static String getAllorderslistUrl() {
        return ALLORDERSLIST_URL;
    }

    //返回申请单
    public static String getAllapplyslistUrl() {
        return ALLAPPLYSLIST_URL;
    }

    //获得登陆的地址
    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    //返回官方网址
    public static String getOfficialWebsite(){return OFFICIAL_WEBSITE;}
}
