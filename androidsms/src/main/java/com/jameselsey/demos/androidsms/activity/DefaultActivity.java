package com.jameselsey.demos.androidsms.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.jameselsey.demos.androidsms.R;
import com.jameselsey.demos.androidsms.receiver.SmsReceiver;
import com.jameselsey.demos.androidsms.util.Constants;
import com.jameselsey.demos.androidsms.util.TtsWrapper;

import static java.lang.String.format;

public class DefaultActivity extends Activity {

    private static final int MY_DATA_CHECK_CODE = 1234;
    private final String TAG = format("%s - %s - ", Constants.APP_LOG_NAME, getClass().getSimpleName());
    private ImageView stateImage;
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

        stateImage = (ImageView) findViewById(R.id.toggleState);

        if (componentEnabledState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            updateUiAsEnabled();
        } else {    // assume disabled
            updateUiAsDisabled();
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

    public void toggleStateClicked(View v) {
        int componentEnabledState = packageManager.getComponentEnabledSetting(compName);
        if (componentEnabledState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            stopListening();
        } else {
            startListening();
        }
    }

    private void startListening() {
        TtsWrapper.startTts(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                Toast.makeText(DefaultActivity.this, "TTS started", Toast.LENGTH_SHORT).show();
            }
        });
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        updateUiAsEnabled();
    }

    private void stopListening() {
        TtsWrapper.finishUsingTts();
        setListenerState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        updateUiAsDisabled();
    }

    private void updateUiAsDisabled() {
        Drawable drawable = getResources().getDrawable(R.drawable.disabled_button);
        stateImage.setImageDrawable(drawable);
        stateImage.setTag(R.drawable.disabled_button);
    }

    private void updateUiAsEnabled() {
        Drawable drawable = getResources().getDrawable(R.drawable.enabled_button);
        stateImage.setImageDrawable(drawable);
        stateImage.setTag(R.drawable.enabled_button);
    }

    private void setListenerState(int state) {
        packageManager.setComponentEnabledSetting(compName, state, PackageManager.DONT_KILL_APP);
    }
}

