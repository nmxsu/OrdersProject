package com.fat246.orders.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fat246.orders.R;
import com.fat246.orders.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_setting_content, new SettingFragment())
                .commit();
    }
}
