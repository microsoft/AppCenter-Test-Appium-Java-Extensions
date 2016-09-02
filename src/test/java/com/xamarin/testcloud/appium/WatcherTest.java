package com.xamarin.testcloud.appium;

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
        String[] expected = {"jsta-testA(com.xamarin.testcloud.appium.WatcherTest)-psozkedvebs"};
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    public void testC() throws Exception {
        String[] expected = {
                "jsta-testA(com.xamarin.testcloud.appium.WatcherTest)-psozkedvebs",
                "jsuc-testA(com.xamarin.testcloud.appium.WatcherTest)-1x5fvtfzrychq",
                "jfin-testA(com.xamarin.testcloud.appium.WatcherTest)-amqb890rk3pr",
                "jsta-testC(com.xamarin.testcloud.appium.WatcherTest)-q3koi3yjil3j",
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
                "jsta-testA(com.xamarin.testcloud.appium.WatcherTest)-psozkedvebs",
                "jsuc-testA(com.xamarin.testcloud.appium.WatcherTest)-1x5fvtfzrychq",
                "jfin-testA(com.xamarin.testcloud.appium.WatcherTest)-amqb890rk3pr",
                "jsta-testC(com.xamarin.testcloud.appium.WatcherTest)-q3koi3yjil3j",

                "jsuc-testC(com.xamarin.testcloud.appium.WatcherTest)-1sluduzx6p6mf",
                "jfin-testC(com.xamarin.testcloud.appium.WatcherTest)-g3598m95rhjh",
                "jsta-testD(com.xamarin.testcloud.appium.WatcherTest)-134micanw29y3",
                "jsuc-testD(com.xamarin.testcloud.appium.WatcherTest)-1bgsf5p34ck5f",
                "jfin-testD(com.xamarin.testcloud.appium.WatcherTest)-1dcdh8x7ced5m",
                "jsta-testE(com.xamarin.testcloud.appium.WatcherTest)-qhz04ggmqwm3",
                "jlab-testE(com.xamarin.testcloud.appium.WatcherTest)-0-1s9aqzas1ohzr",
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
