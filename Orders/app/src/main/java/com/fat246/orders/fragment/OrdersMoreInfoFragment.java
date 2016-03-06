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
import com.fat246.orders.bean.OrderMoreInfo;

/**
 * Created by sun on 2016/2/19.
 */
public class OrdersMoreInfoFragment extends Fragment {

    //id location
    private String ID;
    private int Location;

    public OrdersMoreInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders_more_info, container, false);

        //设置 信息
        setData();

        //
        new OrdersMoreInfoAsyncTask(rootView).execute(ID);

        return rootView;
    }

    //设置 ID 和 Location
    public void setData(){

        Bundle mBundle=getArguments();

        this.ID=mBundle.getString("ID","null");
        this.Location=mBundle.getInt("Location",0);
    }


    //异步加载数据
    private class OrdersMoreInfoAsyncTask extends AsyncTask<String, Void, OrderMoreInfo> {

        //rootView
        private View rootView;

        public OrdersMoreInfoAsyncTask(View rootView) {

            this.rootView = rootView;
        }

        @Override
        protected OrderMoreInfo doInBackground(String... params) {

            return getOrderMoreInfo(params[0]);
        }

        public OrderMoreInfo getOrderMoreInfo(String ID) {

            //虚拟数据
            return new OrderMoreInfo(ID, "null", "全部收货", "苏宇峰", "null", "null");
        }

        @Override
        protected void onPostExecute(OrderMoreInfo orderMoreInfo) {


            //设置UI
            TextView mNAMEE = (TextView) rootView.findViewById(R.id.orders_more_info_namee);
            TextView mPRAC_NAME = (TextView) rootView.findViewById(R.id.orders_more_info_prac_name);
            TextView mCREATE_NAME = (TextView) rootView.findViewById(R.id.orders_more_info_create_name);
            TextView mMODIFY_NAME = (TextView) rootView.findViewById(R.id.orders_more_info_modify_name);
            TextView mdry_auth_name = (TextView) rootView.findViewById(R.id.orders_more_info_dry_auth_name);

            mNAMEE.append(orderMoreInfo.getNAMEE());
            mPRAC_NAME.append(orderMoreInfo.getPRAC_NAME());
            mCREATE_NAME.append(orderMoreInfo.getCREATE_NAME());
            mMODIFY_NAME.append(orderMoreInfo.getMODIFY_NAME());
            mdry_auth_name.append(orderMoreInfo.getDry_auth_name());

            //关闭进度条
            ProgressBar mProgressBar=(ProgressBar)rootView.findViewById(R.id.orders_more_info_progress_bar);
            mProgressBar.setVisibility(View.GONE);

            //LinearLayout 可见
            LinearLayout mLinearLayout=(LinearLayout)rootView.findViewById(R.id.orders_more_info_linear_layout);
            mLinearLayout.setVisibility(View.VISIBLE);

        }
    }
}
