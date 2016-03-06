package com.fat246.orders.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fat246.orders.R;
import com.fat246.orders.bean.ApplyMoreInfo;

/**
 * Created by sun on 2016/2/19.
 */
public class ApplysMoreInfoFragment extends Fragment {

    //ID 和 Location
    private String ID;
    private int Location;

    public ApplysMoreInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_applys_more_info, container, false);

        //设置 ID Location
        setData();

        //异步
        new ApplysMoreInfoAsyncTask(rootView).execute(ID);

        return rootView;
    }

    public void setData(){

        Bundle mBundle=getArguments();

        this.ID=mBundle.getString("ID","null");
        this.Location=mBundle.getInt("Location",0);
    }

    //异步加载数据
    private class ApplysMoreInfoAsyncTask extends AsyncTask<String, Void, ApplyMoreInfo> {

        //View
        private View rootView;

        public ApplysMoreInfoAsyncTask(View rootView) {

            this.rootView = rootView;
        }

        @Override
        protected ApplyMoreInfo doInBackground(String... params) {

            return getApplyMoreInfo(params[0]);
        }

        //拿到ApplyMoreInfo
        public ApplyMoreInfo getApplyMoreInfo(String ID) {

            return new ApplyMoreInfo(ID, "维修车间", "全部采购", "维修", "苏宇峰", "null ", "null ");
        }

        @Override
        protected void onPostExecute(ApplyMoreInfo applyMoreInfo) {

            //设置UI
            TextView mDEP_NAME = (TextView) rootView.findViewById(R.id.applys_more_info_dep_name);
            TextView mPSD_NAME = (TextView) rootView.findViewById(R.id.applys_more_info_psd_name);
            TextView mPSR_NAME = (TextView) rootView.findViewById(R.id.applys_more_info_psr_name);
            TextView mCREATE_NAME = (TextView) rootView.findViewById(R.id.applys_more_info_create_name);
            TextView mMODIFY_NAME = (TextView) rootView.findViewById(R.id.applys_more_info_modify_name);
            TextView mdry_auth_name = (TextView) rootView.findViewById(R.id.applys_more_info_dry_auth_name);

            mDEP_NAME.append(applyMoreInfo.getDEP_NAME());
            mPSD_NAME.append(applyMoreInfo.getPSD_NAME());
            mPSR_NAME.append(applyMoreInfo.getPSR_NAME());
            mCREATE_NAME.append(applyMoreInfo.getCREATE_NAME());
            mMODIFY_NAME.append(applyMoreInfo.getMODIFY_NAME());
            mdry_auth_name.append(applyMoreInfo.getDry_auth_name());

            //关闭进度条的显示
            ProgressBar mProgressBar = (ProgressBar) rootView.findViewById(R.id.applys_more_info_progress_bar);
            mProgressBar.setVisibility(View.GONE);

            //LinearLayout 可见
            LinearLayout mLinearLayout=(LinearLayout)rootView.findViewById(R.id.applys_more_info_linear_layout);
            mLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
