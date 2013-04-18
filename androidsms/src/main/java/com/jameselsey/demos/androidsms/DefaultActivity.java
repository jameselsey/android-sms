package com.jameselsey.demos.androidsms;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DefaultActivity extends Activity {

    private TextView tv;
    private PackageManager packageManager;
    private ComponentName compName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        packageManager = getPackageManager();
        compName = new ComponentName(getApplicationContext(), SmsReceiver.class);
        int componentEnabledState = packageManager.getComponentEnabledSetting(compName);

        tv = (TextView) findViewById(R.id.state);

        if (componentEnabledState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            tv.setText("ENABLED");
            tv.setTextColor(Color.GREEN);
        } else {    // assume disabled
            tv.setText("DISABLED");
            tv.setTextColor(Color.RED);
        }
    }

    public void stopListening(View v) {
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        tv.setText("DISABLED");
        tv.setTextColor(Color.RED);
    }

    public void startListening(View v) {
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        tv.setText("ENABLED");
        tv.setTextColor(Color.GREEN);
    }

    private void setListenerState(int state) {
        packageManager.setComponentEnabledSetting(compName, state, PackageManager.DONT_KILL_APP);
    }
}

