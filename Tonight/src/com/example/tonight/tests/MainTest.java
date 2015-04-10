package com.example.tonight.tests;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tonight.R;

import com.example.tonight.MainActivity;

public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mMainActivity;

    public MainTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        mMainActivity = getActivity();
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
    }
}