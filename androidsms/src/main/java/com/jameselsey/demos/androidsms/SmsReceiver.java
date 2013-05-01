package com.jameselsey.demos.androidsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.String.format;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_EXTRA_NAME = "pdus";
    private final String TAG = format("%s - %s - ", Constants.APP_LOG_NAME, getClass().getSimpleName());

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            Object[] smsExtras = (Object[]) extras.get(SMS_EXTRA_NAME);

            ArrayList<Sms> myMessages = new ArrayList<Sms>();

            for (int i = 0; i < smsExtras.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtras[i]);

                Sms mySms = new Sms(sms.getOriginatingAddress(), sms.getMessageBody().toString());
                myMessages.add(mySms);
            }
            Intent smsIntent = new Intent(context, SpeakerService.class);
            intent.setAction(Constants.ACTION_SPEAK_SMS);
            smsIntent.putParcelableArrayListExtra(Constants.EXTRA_SMS_MESSAGES, myMessages);

            Log.d(TAG, "Starting service...");
            context.startService(smsIntent);
        }
    }
}
