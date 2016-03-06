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
import com.fat246.orders.bean.ApplyInfo;
import com.fat246.orders.widget.Ptr.PtrClassicFrameLayout;
import com.fat246.orders.widget.Ptr.PtrDefaultHandler;
import com.fat246.orders.widget.Ptr.PtrFrameLayout;
import com.fat246.orders.widget.Ptr.PtrHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */
public class MineApplysFragment extends Fragment {

    //首先还是  下拉刷新
    private PtrClassicFrameLayout mPtrFrame;

    //ListView
    private ListView mListView;

    //List
    private List<ApplyInfo> mList = new ArrayList<>();

    //用户信息
    private String mUser;
    private boolean isLogInSucceed = false;


    public MineApplysFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;

        //设置用户信息
        setUserInfo();

        //判断是否登陆
        if (isLogInSucceed) {

            rootView = inflater.inflate(R.layout.fragment_mine_applys, container, false);

            //List
            setList(rootView);

            //设置  下拉刷新
            setPtr(rootView);

        } else {

            rootView = new TextView(getActivity());
            ((TextView) rootView).setText("未登录");
        }

        return rootView;
    }

    //拿到 一些用户信息
    public void setUserInfo() {

        Bundle mBundle = getArguments();

        mUser = mBundle.getString("mUser", "未登录");
        isLogInSucceed = mBundle.getBoolean("isLogInSucceed", false);

    }

    //set List
    public void setList(View rootView){

        mListView=(ListView)rootView.findViewById(R.id.ptr_list_mine_applys);
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
                    convertView = mInflater.inflate(R.layout.fragment_mine_applys_item, null);

                    TextView mPRHS_ID = (TextView) convertView.findViewById(R.id.mine_applys_prhs_id);
                    TextView mDEP_NAME = (TextView) convertView.findViewById(R.id.mine_applys_dep_name);
                    TextView mPSD_NAME = (TextView) convertView.findViewById(R.id.mine_applys_psd_name);
                    TextView mPSR_NAME = (TextView) convertView.findViewById(R.id.mine_applys_psr_name);

                    ApplyInfo mAF = mList.get(position);
                    mPRHS_ID.append(mAF.getPRHS_ID());
                    mDEP_NAME.append(mAF.getDEP_NAME());
                    mPSD_NAME.append(mAF.getPSD_NAME());
                    mPSR_NAME.append(mAF.getPSR_NAME());

                }

                return convertView;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MoreInfo.class);

                //得到用户PO
                String PRHS_ID = mList.get(position).getPRHS_ID();

                intent.putExtra("PRHS_ID", PRHS_ID);
                intent.putExtra("Location", 3);

                startActivity(intent);
            }
        });
    }

    //包装的下拉刷新
    public void setPtr(View rootView) {
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_mine_applys);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //异步刷新加载数据
                new MineApplysAsyncTask(frame).execute(mUser);
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

    //下载  并解析数据
    private class MineApplysAsyncTask extends AsyncTask<String,Void,List<ApplyInfo>>{

        //ptr
        private PtrFrameLayout frame;

        public MineApplysAsyncTask(PtrFrameLayout frame) {
            this.frame=frame;
        }

        @Override
        protected List<ApplyInfo> doInBackground(String... params) {

            //下载解析
            return paserXml(params[0]);
        }

        //以后才写。。。
        public List<ApplyInfo> paserXml(String mUser){

            //虚拟数据
            List<ApplyInfo> list = new ArrayList<>();
            for (int i = 0; i <60; i++) {

                ApplyInfo af = new ApplyInfo("PY2015100500" + i, "生产办", "全部采购", "维修");

                list.add(af);
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<ApplyInfo> applyInfos) {

            mList=applyInfos;

            this.frame.refreshComplete();
        }
    }
}
