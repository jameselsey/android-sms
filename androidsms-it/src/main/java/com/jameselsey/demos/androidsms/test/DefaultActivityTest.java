package com.jameselsey.demos.androidsms.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jameselsey.demos.androidsms.*;

public class DefaultActivityTest extends ActivityInstrumentationTestCase2<DefaultActivity> {

    public DefaultActivityTest() {
        super(DefaultActivity.class);
    }

    public void testActivity() {
        DefaultActivity activity = getActivity();
        assertNotNull(activity);
    }
}

