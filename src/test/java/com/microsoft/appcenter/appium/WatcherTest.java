package com.microsoft.appcenter.appium;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertArrayEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WatcherTest {

    private static MemoryEventReporter reporter = new MemoryEventReporter();

    @Rule
    public Watcher watcher = new Watcher(reporter);

    @Test
    public void testA() throws Exception {
        String[] expected = {"jsta-testA(com.microsoft.appcenter.appium.WatcherTest)-qpsy67axfeg4"};
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    public void testC() throws Exception {
        String[] expected = {
                "jsta-testA(com.microsoft.appcenter.appium.WatcherTest)-qpsy67axfeg4",
                "jsuc-testA(com.microsoft.appcenter.appium.WatcherTest)-20sjfp8pg5fn",
                "jfin-testA(com.microsoft.appcenter.appium.WatcherTest)-18iqpnt9exli1",
                "jsta-testC(com.microsoft.appcenter.appium.WatcherTest)-1ihiwm3ppmaa8",
        };
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    public void testD() throws Exception {
        // On purpose -- should provoke no interception
    }

    @Test
    public void testE() throws Exception {
        reporter.reportLabel("I'm here!", null, 0, false);
        String[] expected = {
                "jsta-testA(com.microsoft.appcenter.appium.WatcherTest)-qpsy67axfeg4",
                "jsuc-testA(com.microsoft.appcenter.appium.WatcherTest)-20sjfp8pg5fn",
                "jfin-testA(com.microsoft.appcenter.appium.WatcherTest)-18iqpnt9exli1",
                "jsta-testC(com.microsoft.appcenter.appium.WatcherTest)-1ihiwm3ppmaa8",

                "jsuc-testC(com.microsoft.appcenter.appium.WatcherTest)-1lqbg75t6qilm",
                "jfin-testC(com.microsoft.appcenter.appium.WatcherTest)-y345gojo5iuz",
                "jsta-testD(com.microsoft.appcenter.appium.WatcherTest)-1ld5ag9vx5dpv",
                "jsuc-testD(com.microsoft.appcenter.appium.WatcherTest)-wddyeyyj7an4",
                "jfin-testD(com.microsoft.appcenter.appium.WatcherTest)-1vp4jevqhyh8m",
                "jsta-testE(com.microsoft.appcenter.appium.WatcherTest)-u745ebgwyyxv",
                "jlab-testE(com.microsoft.appcenter.appium.WatcherTest)-0-1hwqfnryvj3j5",
        };
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    @Ignore("Will fail build")
    public void testX() throws Exception {
        String[] expected = {
                "jsta-testA(WatcherTest)-HdxmF7fk8fY",
                "jsuc-testA(WatcherTest)-aSlXROqBeGM",
                "jfin-testA(WatcherTest)-Li-1K3sXOdY",
                "jsta-testB(WatcherTest)-VT89h9ELOUQ"
        };
        assertArrayEquals(expected, reporter.getIds());
        throw new Exception("On purpose! This test must fail in order to test the Watcher handling failure.");
    }


}
