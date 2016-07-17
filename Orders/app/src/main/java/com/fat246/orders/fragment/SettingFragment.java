package com.fat246.orders.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.fat246.orders.R;

/**
 * Created by ken on 16-7-17.
 */
public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}