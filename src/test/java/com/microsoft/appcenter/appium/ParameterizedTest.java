package com.microsoft.appcenter.appium;


import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ParameterizedTest {
    @Parameterized.Parameters(name="{0}")
    public static Object[] data() {
        return new Object[] { "firsttest", "secondtest" };
    }

    private static MemoryEventReporter reporter = new MemoryEventReporter();

    @Rule
    public Watcher watcher = new Watcher(reporter);

    @Parameterized.Parameter
    public String dataValue;


    @Test
    public void testA() throws Exception {
        String prefix = String.format("jsta-testA[%s]", dataValue);
        assertThat(Arrays.asList(reporter.getIds()), hasItem(startsWith(prefix)));
    }

}

