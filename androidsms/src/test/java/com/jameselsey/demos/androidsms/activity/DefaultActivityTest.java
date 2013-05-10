package com.jameselsey.demos.androidsms.activity;

import android.widget.ImageView;
import com.jameselsey.demos.androidsms.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class DefaultActivityTest {
    private DefaultActivity defaultActivity;
    private ImageView toggleState;

    @Before
    public void setUp() throws Exception {
        defaultActivity = new DefaultActivity();
        defaultActivity.onCreate(null);
        toggleState = (ImageView) defaultActivity.findViewById(R.id.toggleState);
    }

    @Test
    public void shouldStartInDisabledState() throws Exception {
        // test initial state is DISABLED
        assertThat(toggleState.getTag()).isEqualTo(R.drawable.disabled_button);
    }

    @Test
    public void canChangeStateByClickingStartAndStop() {
        // check it starts in disabled
        assertThat(toggleState.getTag()).isEqualTo(R.drawable.disabled_button);

        toggleState.performClick();
        assertThat(toggleState.getTag()).isEqualTo(R.drawable.enabled_button);

    }
}
