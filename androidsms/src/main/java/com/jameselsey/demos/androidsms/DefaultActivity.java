package com.jameselsey.demos.androidsms;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.format;

public class DefaultActivity extends Activity {

    private static final int MY_DATA_CHECK_CODE = 1234;
    private final String TAG = format("%s - %s - ", Constants.APP_LOG_NAME, getClass().getSimpleName());

    private TextView tv;
    private PackageManager packageManager;
    private ComponentName compName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Fire off an intent to check if a TTS engine is installed
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

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

    /**
     * This is the callback from the TTS engine check, if a TTS is installed we
     * create a new TTS instance (which in turn calls onInit), if not then we will
     * create an intent to go off and install a TTS engine
     *
     * @param requestCode int Request code returned from the check for TTS engine.
     * @param resultCode  int Result code returned from the check for TTS engine.
     * @param data        Intent Intent returned from the TTS check.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance

                // enable the start button
            } else {
                // missing tts, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    public void startListening(View v) {
        TtsWrapper.startTts(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                Toast.makeText(DefaultActivity.this, "TTS started", Toast.LENGTH_SHORT).show();
            }
        });
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        tv.setText("ENABLED");
        tv.setTextColor(Color.GREEN);
    }

    public void stopListening(View v) {
        TtsWrapper.finishUsingTts();
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        tv.setText("DISABLED");
        tv.setTextColor(Color.RED);
    }

    private void setListenerState(int state) {
        packageManager.setComponentEnabledSetting(compName, state, PackageManager.DONT_KILL_APP);
    }
}

