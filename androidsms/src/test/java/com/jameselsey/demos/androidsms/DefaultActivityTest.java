package com.jameselsey.demos.androidsms;

import android.widget.Button;
import android.widget.TextView;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class DefaultActivityTest
{
    private DefaultActivity defaultActivity;
    private Button btnStartListening;
    private Button btnStopListening;
    private TextView state;

    @Before
    public void setUp() throws Exception {
        defaultActivity = new DefaultActivity();
        defaultActivity.onCreate(null);
        btnStartListening = (Button) defaultActivity.findViewById(R.id.btnStartListening);
        btnStopListening = (Button) defaultActivity.findViewById(R.id.btnStopListening);
        state = (TextView) defaultActivity.findViewById(R.id.state);
    }

    @Test
    public void shouldStartInDisabledState() throws Exception {
        // test initial state is DISABLED
        assertThat(state.getText().toString(), equalTo("DISABLED"));
    }

    @Test
    public void canChangeStateByClickingStartAndStop(){
        // Verify that you can start
        btnStartListening.performClick();
        assertThat(state.getText().toString(), equalTo("ENABLED"));

        // Stop it
        btnStopListening.performClick();
        assertThat(state.getText().toString(), equalTo("DISABLED"));
    }

    @Test
    public void clickingStartMultipleTimesHasNoEffect(){
        btnStartListening.performClick();
        assertThat(state.getText().toString(), equalTo("ENABLED"));

        btnStartListening.performClick();
        assertThat(state.getText().toString(), equalTo("ENABLED"));

        btnStartListening.performClick();
        assertThat(state.getText().toString(), equalTo("ENABLED"));
    }

    @Test
    public void clickingStopMultipleTimesHasNoEffect(){
        btnStopListening.performClick();
        assertThat(state.getText().toString(), equalTo("DISABLED"));

        btnStopListening.performClick();
        assertThat(state.getText().toString(), equalTo("DISABLED"));

        btnStopListening.performClick();
        assertThat(state.getText().toString(), equalTo("DISABLED"));
    }

}
