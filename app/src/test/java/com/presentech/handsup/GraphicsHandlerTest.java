package com.presentech.handsup;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by Alex on 27/05/2016.
 */
public class GraphicsHandlerTest extends ActivityUnitTestCase<TestActivity> {

    TestActivity activity;

    public GraphicsHandlerTest() {
        super(TestActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), TestActivity.class);
        startActivity(intent,null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testSomething() {
        //activity.
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
