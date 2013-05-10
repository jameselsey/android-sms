package com.jameselsey.demos.androidsms.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jameselsey.demos.androidsms.activity.DefaultActivity;
import com.jayway.android.robotium.solo.Solo;


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
        assertTrue(solo.searchText("DISABLED"));
    }

    public void testEnableSmsMonitoring() {
        solo.clickOnButton("Start Listening");
        assertTrue(solo.searchText("ENABLED"));
    }

    public void testDisableSmsMonitoring() {
        // enable it first
        solo.clickOnButton("Start Listening");
        assertTrue(solo.searchText("ENABLED"));

        solo.clickOnButton("Stop Listening");
        assertTrue(solo.searchText("DISABLED"));
    }
}

