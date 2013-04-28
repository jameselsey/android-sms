package com.jameselsey.demos.androidsms;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.RemoteViews;


public class StateReceiver extends BroadcastReceiver {

    public final String TAG = "WidgetExample";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "StateReceiver got " + intent.getAction());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);

        ComponentName smsComp = new ComponentName(context, SmsReceiver.class);
        PackageManager pm = context.getPackageManager();

        if ("INIT_STATE".equals(intent.getAction())) {
            // Lets check state..
            int currentState = pm.getComponentEnabledSetting(smsComp);
            if(currentState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || currentState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
                Log.d(TAG, "State is " + currentState + ", matching views to be disabled");
                setViewsDisable(remoteViews);
            } else {
                Log.d(TAG, "State is " + currentState + ", matching views to be enabled");
                setViewsEnable(remoteViews);
            }
        } else if ("TOGGLE_STATE".equals(intent.getAction())) {
            int currentState = pm.getComponentEnabledSetting(smsComp);
            Log.d(TAG, "Current state is " + currentState);
            if (currentState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                disable(remoteViews, smsComp, pm);
            } else {
                enable(remoteViews, smsComp, pm);
            }
        }

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {
            Log.d(TAG, "Setting the pending intents");
            Intent clickIntent = new Intent("TOGGLE_STATE");
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.backgroundImage, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private void setViewsEnable(RemoteViews remoteViews) {
        remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.enabledshape);
        remoteViews.setTextViewText(R.id.txtStatus, "ENABLED");
    }

    private void setViewsDisable(RemoteViews remoteViews) {
        remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.disabledshape);
        remoteViews.setTextViewText(R.id.txtStatus, "DISABLED");
    }

    private void enable(RemoteViews remoteViews, ComponentName smsComp, PackageManager pm) {
        Log.d(TAG, "enabling");
         setViewsEnable(remoteViews);
        pm.setComponentEnabledSetting(smsComp, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void disable(RemoteViews remoteViews, ComponentName smsComp, PackageManager pm) {
        Log.d(TAG, "disabling");
        setViewsDisable(remoteViews);
        pm.setComponentEnabledSetting(smsComp, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
