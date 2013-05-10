package com.jameselsey.demos.androidsms.activity;

import android.widget.ImageView;
import android.widget.TextView;
import com.jameselsey.demos.androidsms.R;
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
    private ImageView toggleState;
    private TextView state;

    @Before
    public void setUp() throws Exception {
        defaultActivity = new DefaultActivity();
        defaultActivity.onCreate(null);
        toggleState = (ImageView) defaultActivity.findViewById(R.id.toggleState);
        state = (TextView) defaultActivity.findViewById(R.id.state);
    }

    @Test
    public void shouldStartInDisabledState() throws Exception {
        // test initial state is DISABLED
        assertThat(state.getText().toString(), equalTo("DISABLED"));
    }

    @Test
    public void canChangeStateByClickingStartAndStop(){
        // check it starts in disabled
        assertThat(state.getText().toString(), equalTo("DISABLED"));

        toggleState.performClick();
        assertThat(state.getText().toString(), equalTo("ENABLED"));

    }
}
