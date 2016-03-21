package com.fat246.orders.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {

    private Handler mHandler = new Handler();
    private Timer mTimer;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mContext = getApplicationContext();

        ManageFloatSmallView.getManageFloatSmallView(mContext).addView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setTimerTask() {

        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                if (isHome()) {

                    if (!ManageFloatBigView.isShowing && !ManageFloatSmallView.isShowing) {

                        ManageFloatSmallView.getManageFloatSmallView(mContext).addView();
//                        Log.e("HOME ------>", "IS SHOWING");
                    }

//                    Log.e("HOME ------>", "IS HOME");
                } else {

                    if (ManageFloatBigView.isShowing) {
                        ManageFloatBigView.getManageFloatBigView(mContext).removeView();
                    } else if (ManageFloatSmallView.isShowing) {
                        ManageFloatSmallView.getManageFloatSmallView(mContext).removeView();
                    }

//                    Log.e("NOT HOME ------>", "IS NOT HOME");
                }
            }
        };

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mRunnable);
            }
        }, 0, 10);
    }

    private boolean isHome() {

        boolean result = false;

        List<String> names = getHomes();

        ActivityManager mAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> mRTI = mAM.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo running : mRTI) {
            if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (int i = 0; i < names.size(); i++) {
                    if (names.get(i).equals(running.processName)) {
                        result = true;
                        break;
                    }
                }
            }
        }


        return result;
    }

    private List<String> getHomes() {

        List<String> mList = new ArrayList<>();

        PackageManager mPM = this.mContext.getPackageManager();

        //属性
        Intent mIntent = new Intent(Intent.ACTION_MAIN);

        mIntent.addCategory(Intent.CATEGORY_HOME);

        List<ResolveInfo> mResolveInfo = mPM.queryIntentActivities(mIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo i : mResolveInfo) {
            mList.add(i.activityInfo.packageName);

        }

        return mList;
    }
}
