package com.fat246.orders.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fat246.orders.R;
import com.fat246.orders.activity.MoreInfo;
import com.fat246.orders.bean.OrderInfo;
import com.fat246.orders.widget.Ptr.PtrClassicFrameLayout;
import com.fat246.orders.widget.Ptr.PtrDefaultHandler;
import com.fat246.orders.widget.Ptr.PtrFrameLayout;
import com.fat246.orders.widget.Ptr.PtrHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/5.
 */
public class MineOrdersFragment extends Fragment {

    //下拉刷新
    private PtrClassicFrameLayout mPtrFrame;

    //ListView
    private ListView mListView;

    //List
    List<OrderInfo> mList = new ArrayList<>();

    //用户名
    private String mUser;
    private boolean isLogInSucceed;

    public MineOrdersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=null;

        //设置用户名和登陆信息
        setUserInfo();

        //是否登陆成功
        if (isLogInSucceed) {

            rootView = inflater.inflate(R.layout.fragment_mine_orders, container, false);

            //设置下拉刷新
            setPtr(rootView);

            //设置ListView
            setList(rootView);

        } else {

            rootView = new TextView(getActivity());
            ((TextView) rootView).setText("未登录");
        }

        return rootView;
    }

    public void setUserInfo() {

        Bundle mBundle = getArguments();

        mUser = mBundle.getString("mUser", "未登录");
        isLogInSucceed = mBundle.getBoolean("isLogInSucceed", false);

    }

    //list
    public void setList(View rootView) {

        //
        mListView = (ListView) rootView.findViewById(R.id.ptr_list_mine_orders);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {

                    LayoutInflater mInflater = LayoutInflater.from(getActivity());
                    convertView = mInflater.inflate(R.layout.fragment_mine_orders_item, null);

                    TextView mPRHSORD_ID = (TextView) convertView.findViewById(R.id.mine_orders_prhsord_id);
                    TextView mNAMEE = (TextView) convertView.findViewById(R.id.mine_orders_namee);
                    TextView mPRAC_NAME = (TextView) convertView.findViewById(R.id.mine_orders_prac_name);
//                    TextView mID=(TextView)convertView.findViewById(R.id.mine_orders_apply_psr);

                    OrderInfo mOI = mList.get(position);
                    mPRHSORD_ID.append(mOI.getPRHSORD_ID());
                    mNAMEE.append(mOI.getNAMEE());
                    mPRAC_NAME.append(mOI.getPRAC_NAME());
//                    mPSR.append(mAF.getPSR_NAME());

                }

                return convertView;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), MoreInfo.class);

                //得到用户PY
                String PRHSORD_ID = mList.get(position).getPRHSORD_ID();

                intent.putExtra("PRHSORD_ID", PRHSORD_ID);
                intent.putExtra("Location", 2);

                startActivity(intent);

            }
        });

    }

    //包装的下拉刷新
    public void setPtr(View rootView) {
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_mine_orders);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //异步刷新加载数据
                new MineOrdersAsyncTask(frame).execute(mUser);
            }
        });

        // 这些大部分都是默认设置
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        mPtrFrame.setPullToRefresh(false);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    //异步下载数据
    class MineOrdersAsyncTask extends AsyncTask<String, Void, List<OrderInfo>> {

        private PtrFrameLayout frame;

        public MineOrdersAsyncTask(PtrFrameLayout frame) {

            this.frame = frame;
        }

        @Override
        protected List<OrderInfo> doInBackground(String... params) {

            //根据用户名  解析xml数据
            List list = paserXml(params[0]);

            return list;
        }

        @Override
        protected void onPostExecute(List<OrderInfo> ordersInfo) {

            mList = ordersInfo;
            frame.refreshComplete();
        }

        //下载xml 并解析
        public List paserXml(String mUser) {

            List<OrderInfo> list=new ArrayList<>();

            //虚拟数据
            for (int i=0; i<30; i++){

                OrderInfo mOI=new OrderInfo("PO2015100500"+i," ","全部收货");

                list.add(mOI);
            }

            return list;
        }
    }
}
