package com.fat246.orders.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fat246.orders.R;
import com.fat246.orders.application.MyApplication;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.parser.LogInParser;
import com.fat246.orders.widget.CI.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomePage extends Activity {


    //官方网站
    public static String official_website="http://www.fat246.com";

    /**
     * 登陆要用到的URL
     */
    private static final String LOGIN_SERVER="isLogin";
    private String LOGIN_URL;

    //动画的持续时间
    private static long Anim_Duration=1000;

    //开始界面的图片
    private ImageView mImageView;

    //View First
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private Button mNowLogIn;
    private TextView mComeIndirectly;

    private List<View> mViewList;

    //是不是第一次登陆
    private boolean isFirstLoad;

    //判断是否有网络连接
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //判断是不是第一次  Load
        if (isFirstLoad()){

            //启动显示新特新
            setContentView(R.layout.activity_hom_page_first);

            setFirstView();

            setFirstData();

            setFirstListener();

            setSomeThing();


        }else {

            //正常启动
            setContentView(R.layout.activity_home_page);

            getSomeApplicationInfo();

            setView();

            isConnected=isOnline();

            if (!isConnected)hintUser("亲，没有网络哦。。。");

            setListenler();
        }
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

            isFirstLoad=true;
            return true;
        }else {

            isFirstLoad=false;
            return false;
        }
    }

    public void setFirstView(){

        mViewPager=(ViewPager)findViewById(R.id.mViewPager);
        mCircleIndicator=(CircleIndicator)findViewById(R.id.mCircleIndicator);
        mNowLogIn=(Button)findViewById(R.id.now_login);
        mComeIndirectly=(TextView)findViewById(R.id.come_in_directly);
    }

    public void setSomeThing(){

        //
        mViewPager.setAdapter(pagerAdapter);
        mCircleIndicator.setViewPager(mViewPager);

    }

    //给用户一些提示
    private void hintUser(String msg){

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //判断是否连接到网络
    public boolean isOnline(){

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null&&networkInfo.isConnected());
    }

    //得到  用户登录时候要访问的的URL
    public void getSomeApplicationInfo(){

        //用URL 前缀 加上  要访问的服务构成  URL
        MyApplication mApp=(MyApplication)getApplication();
        LOGIN_URL=mApp.PRE_URL+"//"+LOGIN_SERVER;
    }

    public void setFirstData(){

        mViewList=new ArrayList<>();
        Random random=new Random();

        for (int i=0; i<5; i++){

            View mView=new View(this);
            mView.setBackgroundColor(0xff000000|random.nextInt(0x00ffffff));
            mViewList.add(mView);
        }
    }

    public void setFirstListener(){

        mNowLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent=new Intent(HomePage.this,LoginPage.class);

                UserInfo.setData(mIntent);

                startActivity(mIntent);

                //动画

                HomePage.this.finish();
            }
        });

        mComeIndirectly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent=new Intent(HomePage.this,MainPage.class);

                UserInfo.setData(mIntent);

                startActivity(mIntent);

                //动画

                HomePage.this.finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!isFirstLoad){
            //停止动画结束后的事件
            mImageView.animate().setListener(null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isFirstLoad){
            //停止动画结束后的事件
            mImageView.animate().setListener(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isFirstLoad){
            //重新开启动画   只需在 onResume()中重启动画
            setAnimation(0.4f, 1f, Anim_Duration);
        }
    }

    //找到一些 控件
    private void setView(){
        mImageView=(ImageView)findViewById(R.id.home_img);
    }

    //设置监听器
    private void setListenler(){
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uri 跳转到官网的Uri
                Uri mUri = Uri.parse(HomePage.official_website);

                Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);

                //看是否有App接受这个意图
                PackageManager mPackageManager = getPackageManager();
                List<ResolveInfo> mActivities = mPackageManager.queryIntentActivities(mIntent, 0);
                boolean isIntentSafe = mActivities.size() > 0;

                //判断又没有App接受这个意图，如果没有就别执行了，不然会Crash
                if (isIntentSafe) {

                    //首先把动画的监听事件停止了
                    mImageView.animate()
                            .setListener(null);

                    startActivity(mIntent);
                } else {

                    //关闭点击监听
                    mImageView.setOnClickListener(null);
                }

            }
        });
    }

    //设置动画
    private void setAnimation(float start,float end,long duration){

        //首先将透明度设置成start
        mImageView.setAlpha(start);

        //然后透明度渐渐变化
        mImageView.animate()
                .alpha(end)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        //异步做一些登陆准备  包括跳转
                        new HomePageWordThread(LOGIN_URL).execute();

                    }
                });
    }

    //处理返回事件
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isFirstLoad){
            //停止动画
            mImageView.animate().setListener(null);
            this.finish();
        }
    }

    //异步读取
    public class HomePageWordThread extends AsyncTask<Void,Void,UserInfo>{

        private String URL_Str;

        public HomePageWordThread(String URL_Str){

            this.URL_Str=URL_Str;
        }

        @Override
        protected UserInfo doInBackground(Void... params) {

            //从Preferences中读取UserInfo的信息
            UserInfo mUserInfo=getUserInfo();

            //判断是否需要自动登陆
            if (mUserInfo.getisAutoLogIn()){

                new LogInParser(mUserInfo,URL_Str).checkLogIn();
            }else {

            }

            return mUserInfo;
        }

        //访问网络

        public UserInfo getUserInfo(){

            //首先得到SharedPreferences
            SharedPreferences mSP=getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

            //读取UserInfo信息
            return new UserInfo(
                    mSP.getString("mUser",""),
                    mSP.getString("mPassword",""),
                    mSP.getBoolean("isSavePassword",false),
                    mSP.getBoolean("isAutoLogIn",false)
            );
        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {

            Intent mIntent;

            //判断是否登陆成功
            if (userInfo.operationValue!=LogInParser.ERROR_VALUE_WRONG_PASSWORD&&
                    userInfo.operationValue!=LogInParser.ERROR_VALUE_NETWORK_INCOORRECT){

                //直接进入主界面
                mIntent=new Intent(HomePage.this,MainPage.class);

            }else {

                //进入登陆界面
                mIntent=new Intent(HomePage.this,LoginPage.class);
            }

            UserInfo.setData(mIntent, userInfo);

            startActivity(mIntent);

            //切换动画


            //结束HomePage
            HomePage.this.finish();
        }
    }



    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return mViewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(mViewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return "title";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));

            return mViewList.get(position);
        }

    };
}