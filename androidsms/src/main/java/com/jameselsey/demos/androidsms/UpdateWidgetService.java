package com.jameselsey.demos.androidsms;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

    public final String TAG = "WidgetExample";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.widgetlayout);

            ComponentName smsComp = new ComponentName(this.getApplicationContext(), SmsReceiver.class);
            PackageManager pm = this.getApplicationContext().getPackageManager();

            if(pm.getComponentEnabledSetting(smsComp) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
                Log.d(TAG, "Default status, lets turn it on");
                enable(remoteViews, smsComp, pm);
            }
            else if(pm.getComponentEnabledSetting(smsComp) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
                Log.d(TAG, "Is disabled, setting to enabled") ;
                enable(remoteViews, smsComp, pm);
            }
            else if (pm.getComponentEnabledSetting(smsComp) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
                Log.d(TAG, "is enabled setting to disabled");
                disable(remoteViews,smsComp, pm);
            }

            Intent clickIntent = new Intent(this.getApplicationContext(), MyWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            Log.d(TAG, "Setting the pending intents");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.backgroundImage, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();
        return 0;
    }

    private void enable(RemoteViews remoteViews, ComponentName smsComp, PackageManager pm) {
        remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.enabledshape);
        remoteViews.setTextViewText(R.id.txtStatus, "ENABLED");
        pm.setComponentEnabledSetting(smsComp, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void disable(RemoteViews remoteViews, ComponentName smsComp, PackageManager pm) {
        remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.disabledshape);
        remoteViews.setTextViewText(R.id.txtStatus, "DISABLED");
        pm.setComponentEnabledSetting(smsComp, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}