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
        String[] expected = {"jsta-testA(com.xamarin.testcloud.appium.WatcherTest)-AU8V17-Af0g"};
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    @Ignore
    public void testB() throws Exception {
        String[] expected = {
                "jsta-testA(WatcherTest)-HdxmF7fk8fY",
                "jsuc-testA(WatcherTest)-aSlXROqBeGM",
                "jfin-testA(WatcherTest)-Li-1K3sXOdY",
                "jsta-testB(WatcherTest)-VT89h9ELOUQ"
        };
        assertArrayEquals(expected, reporter.getIds());
        throw new Exception("On purpose! This test must fail in order to test the Watcher handling failure.");
    }

    @Test
    @Ignore
    public void testC() throws Exception {
        String[] expected = {
                "jsta-testA(WatcherTest)-HdxmF7fk8fY",
                "jsuc-testA(WatcherTest)-aSlXROqBeGM",
                "jfin-testA(WatcherTest)-Li-1K3sXOdY",
                "jsta-testB(WatcherTest)-VT89h9ELOUQ",

                "jfai-testB(WatcherTest)-_AnTMlAWZIc",
                "jfin-testB(WatcherTest)-oXGRNfsQC28",
                "jsta-testC(WatcherTest)-_i4uhnmaxQU",
        };
        assertArrayEquals(expected, reporter.getIds());
    }

    @Test
    @Ignore
    public void testD() throws Exception {
        // On purpose -- should provoke no interception
    }

    @Test
    @Ignore
    public void testE() throws Exception {
        reporter.reportLabel("I'm here!", null, 0, false);
        String[] expected = {
                "jsta-testA(WatcherTest)-HdxmF7fk8fY",
                "jsuc-testA(WatcherTest)-aSlXROqBeGM",
                "jfin-testA(WatcherTest)-Li-1K3sXOdY",
                "jsta-testB(WatcherTest)-VT89h9ELOUQ",
                "jfai-testB(WatcherTest)-_AnTMlAWZIc",
                "jfin-testB(WatcherTest)-oXGRNfsQC28",
                "jsta-testC(WatcherTest)-_i4uhnmaxQU",

                "jsuc-testC(WatcherTest)-TYETH-6R7AQ",
                "jfin-testC(WatcherTest)-BpAxiyJhNBc",
                "jsta-testE(WatcherTest)-LtpbBfsvdIU",
                "jlab-testE(WatcherTest)-0-L-AyyU5SfLc",
        };
        assertArrayEquals(expected, reporter.getIds());
    }

}
