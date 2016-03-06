package com.fat246.orders.bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UserInfo {

    //记录登陆信息的Preferences
    public static String login_info_key="com.fat246.orders.login_info_key";

    //记录用户登录信息
    private String mUser;
    private String mPassword;
    private boolean isSavePassword;
    private boolean isAutoLogIn;

    //记录用户的权限
    public int operationValue;

    //特定的构造函数
    public UserInfo(String mUser,String mPassword,boolean isSavePassword,boolean isAutoLogIn){

        this.mUser=mUser;
        this.mPassword=mPassword;
        this.isSavePassword=isSavePassword;
        this.isAutoLogIn=isAutoLogIn;

        //初始权限
        this.operationValue=-1;
    }

    public UserInfo(String mUser,String mPassword,boolean isSavePassword,boolean isAutoLogIn,int operationValue){
        this( mUser, mPassword, isSavePassword, isAutoLogIn);

        this.operationValue=operationValue;
    }

    //只读
    public String getmUser(){return this.mUser;}
    public String getmPassword(){return this.mPassword;}
    public boolean getisSavePassword(){return this.isSavePassword;}
    public boolean getisAutoLogIn(){return this.isAutoLogIn;}

    //得到一个Bundle
    public Bundle getBundle(){

        Bundle mBundle=new Bundle();

        mBundle.putString("mUser", mUser);
        mBundle.putInt("operationValue", operationValue);

        return mBundle;
    }

    //得到一个Bundle
    public Bundle getBundle(Bundle outSatate){

        outSatate.putString("mUser", mUser);
        outSatate.putInt("operationValue", operationValue);

        return outSatate;
    }

    //得到

    //包装数据
    public static void setData(Intent mIntent,UserInfo mUserInfo){

        mIntent.putExtra("mUser",mUserInfo.getmUser());
        mIntent.putExtra("mPassword",mUserInfo.getmPassword());
        mIntent.putExtra("isSavePassword",mUserInfo.getisSavePassword());
        mIntent.putExtra("isAutoLogIn",mUserInfo.getisAutoLogIn());
        mIntent.putExtra("operationValue",mUserInfo.operationValue);
    }

    //包装空数据
    public static void setData(Intent mIntent){

        mIntent.putExtra("mUser","");
        mIntent.putExtra("mPassword","");
        mIntent.putExtra("isSavePassword",false);
        mIntent.putExtra("isAutoLogIn",false);
        mIntent.putExtra("operationValue",0);
}

    //取出数据
    public static UserInfo getData(Activity activity){
        //Intent
        Intent mIntent=activity.getIntent();

        //实例化 UserInfo
        UserInfo mUserInfo= new UserInfo(
                mIntent.getStringExtra("mUser"),
                mIntent.getStringExtra("mPassword"),
                mIntent.getBooleanExtra("isSavePassword",false),
                mIntent.getBooleanExtra("isAutoLogIn",false)
        );

        mUserInfo.operationValue=mIntent.getIntExtra("operationValue",0);

        return mUserInfo;
    }

    //从Bundle里面取出数据
    public static UserInfo getData(Bundle savesavedInstanceState){

        return new UserInfo(savesavedInstanceState.getString("mUser","null"),"null",false,false,savesavedInstanceState.getInt("operationValue",0));
    }
}
