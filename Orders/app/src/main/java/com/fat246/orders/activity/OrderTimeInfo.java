package com.fat246.orders.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fat246.orders.R;
import com.fat246.orders.application.MyApplication;
import com.fat246.orders.bean.OrderStandInfo;
import com.fat246.orders.bean.TimeInfo;
import com.fat246.orders.parser.TimeInfoParser;

public class OrderTimeInfo extends AppCompatActivity {

    private String Time;

    //TextView
    private TextView tCreateTime;
    private TextView tServiceTime;
    private TextView tSubmitTime;
    private TextView tApproveTime;
    private TextView tExitTime;

    private LinearLayout linearLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_time_info);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        Intent tIntent = getIntent();
        Bundle tBundle = tIntent.getExtras();

        Time = tBundle.getString(TimeInfo.Time);

        this.setTitle("time");

        setSupportActionBar(toolBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replece all", Snackbar.LENGTH_LONG)
                        .setAction("Action1", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        showBar();


    }

    private void showBar() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
    }


    private void initView() {
        tCreateTime = (TextView) findViewById(R.id.create);
        tServiceTime = (TextView) findViewById(R.id.service);
        tSubmitTime = (TextView) findViewById(R.id.submit);
        tApproveTime = (TextView) findViewById(R.id.approve);
        tExitTime = (TextView) findViewById(R.id.exit);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

    }

    class MyAsyncTask extends AsyncTask<String, Void, OrderStandInfo> {

        @Override
        protected OrderStandInfo doInBackground(String... params) {
            return new TimeInfoParser(MyApplication.getTimeInfo(), params[0]).getTimeInfo();
        }

        @Override
        protected void onPostExecute(OrderStandInfo orderStandInfo) {

            tCreateTime.append(": " + orderStandInfo.getORDER_PROVIDER());
            tServiceTime.append(": " + orderStandInfo.getCOMPLETE_STATUS());
            tSubmitTime.append(": " + orderStandInfo.getORDER_OWNER());
            tApproveTime.append(": " + orderStandInfo.getORDER_MAINTAIN());
            tExitTime.append(": " + orderStandInfo.getORDER_JUDGE());
        }


        private void hideBar() {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}