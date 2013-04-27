package com.jameselsey.demos.androidsms;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

    private static boolean STATE;
    public final String TAG = "WidgetExample";
    private final String ACTION = "ACTION_CLICKED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Log.w(TAG, "onUpdate");

        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
            Intent intent = new Intent(context, MyWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent.setAction(ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.txtStatus, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "OnReceive with action " + intent.getAction());

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
        ComponentName componentName = new ComponentName(context.getApplicationContext(), MyWidgetProvider.class);
        if (ACTION.equals(intent.getAction())) {
            if (STATE) {
                Log.d(TAG, "ENABLED -> DISABLED");
                STATE = false;
                remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.disabledshape);
                remoteViews.setTextViewText(R.id.txtStatus, "DISABLED");
            } else {
                Log.d(TAG, "DISABLED -> ENABLED");
                STATE = true;
                remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.enabledshape);
                remoteViews.setTextViewText(R.id.txtStatus, "ENABLED");
            }
        }

        Intent i = new Intent(context, MyWidgetProvider.class);
        i.setAction(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.txtStatus, pendingIntent);

        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(componentName);
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetIds, remoteViews);
    }
}

