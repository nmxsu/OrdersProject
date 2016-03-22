package com.fat246.orders.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fat246.orders.R;
import com.fat246.orders.activity.LoadingPage;
import com.fat246.orders.activity.LoginPage;
import com.fat246.orders.activity.MainPage;
import com.fat246.orders.application.MyApplication;
import com.fat246.orders.bean.UserInfo;
import com.fat246.orders.parser.LogInParser;

import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public class LoadingPageFragment extends Fragment {

    /**
     * 登陆要用到的URL
     */
    private static String LOGIN_URL;

    //开始界面的图片
    private ImageView mImageView;

    //判断是否有网络连接
    private boolean isConnected;

    //动画的持续时间
    private static long Anim_Duration = 1000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_loading_page, container, false);

        //得到登陆需要访问的网络地址
        LOGIN_URL=MyApplication.getLoginUrl();

        setView(rootView);

        isConnected = isOnline();

        if (!isConnected) hintUser("亲，没有网络哦。。。");

        setListenler();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        //停止动画结束后的事件
        mImageView.animate().setListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();

        //重新开启动画   只需在 onResume()中重启动画
        setAnimation(0.4f, 1f, Anim_Duration);
    }

    //给用户一些提示
    private void hintUser(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    //找到一些 控件
    private void setView(View rootView) {
        mImageView = (ImageView) rootView.findViewById(R.id.home_img);
    }

    //判断是否连接到网络
    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    //设置监听器
    private void setListenler() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uri 跳转到官网的Uri
                Uri mUri = Uri.parse(LoadingPage.official_website);

                Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);

                //看是否有App接受这个意图
                PackageManager mPackageManager = getActivity().getPackageManager();
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
    private void setAnimation(float start, float end, long duration) {

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
                        new LoadingPageWordThread(LOGIN_URL).execute();

                    }
                });
    }

    //异步读取
    public class LoadingPageWordThread extends AsyncTask<Void, Void, UserInfo> {

        private String URL_Str;

        public LoadingPageWordThread(String URL_Str) {

            this.URL_Str = URL_Str;
        }

        @Override
        protected UserInfo doInBackground(Void... params) {

            //从Preferences中读取UserInfo的信息
            UserInfo mUserInfo = getUserInfo();

            //判断是否需要自动登陆
            if (mUserInfo.getisAutoLogIn()) {

                Log.e("hint",mUserInfo.getmUser()+" || "+mUserInfo.getmPassword()+ "++"+URL_Str);
                new LogInParser(mUserInfo, URL_Str).checkLogIn();
            }

            return mUserInfo;
        }

        //访问网络

        public UserInfo getUserInfo() {

            //首先得到SharedPreferences
            SharedPreferences mSP = getActivity()
                    .getSharedPreferences(UserInfo.login_info_key, Context.MODE_PRIVATE);

            //读取UserInfo信息
            return new UserInfo(
                    mSP.getString("mUser", ""),
                    mSP.getString("mPassword", ""),
                    mSP.getBoolean("isSavePassword", false),
                    mSP.getBoolean("isAutoLogIn", false)
            );
        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {

            Intent mIntent;

            Log.e("result_auto_login",userInfo.operationValue+"");

            //判断是否登陆成功
            if (userInfo.operationValue != LogInParser.ERROR_VALUE_WRONG_PASSWORD &&
                    userInfo.operationValue != LogInParser.ERROR_VALUE_NETWORK_INCOORRECT) {

                //直接进入主界面
                mIntent = new Intent(getActivity(), MainPage.class);

            } else {

                //进入登陆界面
                mIntent = new Intent(getActivity(), LoginPage.class);
            }

            //改从配置文件里面获取信息过后就不需要这个了
//            UserInfo.setData(mIntent, userInfo);

            startActivity(mIntent);

            //切换动画


            //结束HomePage
            getActivity().finish();
        }
    }

}
