package com.jameselsey.demos.androidsms.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import com.jameselsey.demos.androidsms.R;
import com.jameselsey.demos.androidsms.activity.DefaultActivity;
import com.jayway.android.robotium.solo.Solo;

import static org.fest.assertions.api.Assertions.assertThat;

public class DefaultActivityTest extends ActivityInstrumentationTestCase2<DefaultActivity> {

    private Solo solo;

    public DefaultActivityTest() {
        super(DefaultActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        TestUtils.resetAppState(getInstrumentation().getTargetContext());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

    }

    public void testGetActivity() {
        DefaultActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testAppStartsInDisabledMode() {
        assertThat(solo.getImage(0).getTag()).isEqualTo(R.drawable.disabled_button);
    }

    public void testEnableSmsMonitoring() {
        solo.clickOnImage(0);
        solo.waitForView(ImageView.class);
        assertThat(solo.getImage(0).getTag()).isEqualTo(R.drawable.enabled_button);
    }

    public void testDisableSmsMonitoring() {
        // enable it first
        solo.clickOnImage(0);
        solo.waitForView(ImageView.class);
        assertThat(solo.getImage(0).getTag()).isEqualTo(R.drawable.enabled_button);

        solo.clickOnImage(0);
        solo.waitForView(ImageView.class);
        assertThat(solo.getImage(0).getTag()).isEqualTo(R.drawable.disabled_button);
    }
}

