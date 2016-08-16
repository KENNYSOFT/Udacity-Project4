package kr.KENNYSOFT.Udacity.Project4;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    CountDownLatch signal;
    String mResult;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp() throws Exception
    {
        signal=new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception
    {
        signal.countDown();
    }

    @Test
    public void testEndpointsAsyncTask() throws InterruptedException
    {
        new EndpointsAsyncTask().setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener()
        {
            @Override
            public void onComplete(String result)
            {
                mResult=result;
                signal.countDown();
            }
        }).execute(getContext());
        signal.await(20,TimeUnit.SECONDS);
        assertFalse(mResult.isEmpty());
    }
}