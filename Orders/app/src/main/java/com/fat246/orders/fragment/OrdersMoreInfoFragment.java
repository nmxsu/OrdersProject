package com.fat246.orders.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fat246.orders.R;
import com.fat246.orders.bean.OrderMoreInfo;
import com.fat246.orders.bean.OrderMoreInfoListItem;

import java.util.ArrayList;
import java.util.List;

public class OrdersMoreInfoFragment extends Fragment {

    //id location
    private String ID;
    private int Location;

    //list
    private ListView mList;

    //datalist
    private List<OrderMoreInfoListItem> mDataList=new ArrayList<>();

    //适配器
    private OrdersMoreInfoAdapter mAdapter;


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

    //适配器
    class OrdersMoreInfoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //重新实例化View
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            convertView=layoutInflater.inflate(R.layout.orders_more_info_list_item,null);

            //设置  data
            setData(position,convertView);

            return convertView;
        }

        public void setData(int position,View convertView){

            //找到相应的View
            TextView MATE_Code=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MATE_Code);
            TextView MATE_Name=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MATE_Name);
            TextView MUNITU_NAME=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MUNITU_NAME);
            TextView MATE_Size=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MATE_Size);
            TextView MATE_Model=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MATE_Model);
            TextView PRHSOD_AMNT=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_PRHSOD_AMNT);
            TextView PRHSOD_ACCEIN=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_PRHSOD_ACCEIN);
            TextView PRHSOD_BILLIN=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_PRHSOD_BILLIN);
            TextView PRHSOD_AMNT_RTN=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_PRHSOD_AMNT_RTN);
            TextView MATE_PRICEP=(TextView)convertView.findViewById(R.id.orders_more_info_list_item_MATE_PRICEP);

            //设置详细信息
            OrderMoreInfoListItem mItem=mDataList.get(position);
            MATE_Code.append(mItem.getMATE_Code());
            MATE_Name.append(mItem.getMATE_Name());
            MUNITU_NAME.append(mItem.getMUNITU_NAME());
            MATE_Size.append(mItem.getMATE_Size());
            MATE_Model.append(mItem.getMATE_Model());
            PRHSOD_AMNT.append(mItem.getPRHSOD_AMNT());
            PRHSOD_ACCEIN.append(mItem.getPRHSOD_ACCEIN());
            PRHSOD_BILLIN.append(mItem.getPRHSOD_BILLIN());
            PRHSOD_AMNT_RTN.append(mItem.getPRHSOD_AMNT_RTN());
            MATE_PRICEP.append(mItem.getMATE_PRICEP());
        }
    }
}
