package com.jameselsey.demos.androidsms.test;


import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.jameselsey.demos.androidsms.receiver.SmsReceiver;

public class TestUtils {

    public static void resetAppState(Context targetContext) {
        ComponentName comp = new ComponentName(targetContext, SmsReceiver.class);
        targetContext.getPackageManager().setComponentEnabledSetting(comp, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
