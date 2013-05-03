package com.jameselsey.demos.androidsms.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.jameselsey.demos.androidsms.util.TtsWrapper;
import com.jameselsey.demos.androidsms.domain.Sms;
import com.jameselsey.demos.androidsms.util.Constants;

import java.util.ArrayList;

import static java.lang.String.format;

public class SpeakerService extends Service {

    private final String TAG = format("%s - %s - ", Constants.APP_LOG_NAME, getClass().getSimpleName());

    @Override
    public void onCreate() {
        Log.d(TAG, "In onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "speaking something from intent " + intent.getAction());
        Bundle extras = intent.getExtras();

        if (extras != null) {
            ArrayList<Sms> smsMessages = extras.getParcelableArrayList(Constants.EXTRA_SMS_MESSAGES);
            Log.d(TAG, format("There were %d messages", smsMessages.size()));
            for (Sms sms : smsMessages){
                String message = format("SMS received from %s, message is %s", sms.getSender(), sms.getBody());
                Log.d(TAG, "Attempting to speak " + message);

                TextToSpeech mTts = TtsWrapper.getInstance();
                if(mTts != null){
                    mTts.speak(message, TextToSpeech.QUEUE_ADD, null);
                }
            }
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in onDestroy()");
        TtsWrapper.finishUsingTts();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
