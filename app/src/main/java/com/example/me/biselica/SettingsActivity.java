package com.example.me.biselica;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@SuppressWarnings("ALL")
public class SettingsActivity extends PreferenceActivity {
    private PendingIntent intent;
    ListPreference list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        addPreferencesFromResource(R.xml.pref_general);
        Preference restart = findPreference("restart");

        list = (ListPreference)findPreference("list");
        Preference btn = (Preference)findPreference("chooseListFile");
        btn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                return true;
            }
        });

        intent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getIntent()), 0);
        toolbar.setClickable(true);
        toolbar.setNavigationIcon(getResIdFromAttribute(this));
        toolbar.setTitle(R.string.action_settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private static int getResIdFromAttribute(final Activity activity) {
        if (R.attr.homeAsUpIndicator == 0) {
            return 0;
        }
        final TypedValue typedvalueattr = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.homeAsUpIndicator, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }
}