package com.jameselsey.demos.androidsms.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.jameselsey.demos.androidsms.util.Constants;


public class TtsWrapper {

    private static final String TAG = Constants.APP_LOG_NAME + " - TtsWrapper ";
    private static TextToSpeech tts;

    private TtsWrapper(){
        // private constructor to prevent instantiation
    }

    public static TextToSpeech getInstance(){
        return tts;
    }

    public static void startTts(Context context, TextToSpeech.OnInitListener listener){
        if(tts == null){
            Log.d(TAG, "tts is null, creating a new instance");
            tts = new TextToSpeech(context, listener);
        } else{
            Log.d(TAG, "TtsWrapper - tts is already assigned, doing nothing");
        }
    }

    public static void finishUsingTts(){
        if(tts != null){
            Log.d(TAG, "Shutting down TTS...");
            tts.shutdown();
            tts = null;
        }
    }

}
